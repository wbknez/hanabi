/*
 * Copyright 2018 Will Knez <wbknez.dev@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.solsticesquared.hanabi

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.Gen
import io.kotlintest.specs.ShouldSpec
import java.util.concurrent.ThreadLocalRandom

/**
 * Represents an implementation of [Gen] that generates [ParticlePool]
 * objects with randomized properties.
 *
 * @property maxBuffers
 *           The upper bound on the number of buffers a pool may have.
 * @property maxParticles
 *           The upper bound on the number of particles a buffer may have.
 * @property randomizeBufferData
 *           Whether or not to fill each buffer with randomized numeric data.
 * @property randomizeStride
 *           Whether or not to randomize the stride per buffer.
 */
class ParticlePoolGenerator(var maxBuffers: Int = 5,
                            var maxParticles: Int = 100,
                            var randomizeBufferData: Boolean = false,
                            var randomizeStride: Boolean = false)
    : Gen<ParticlePool> {

    override fun generate(): ParticlePool {
        val random = ThreadLocalRandom.current()
        val numBuffers = random.nextInt(1, this.maxBuffers)
        val numParticles = random.nextInt(1, this.maxParticles)
        val pool = ParticlePool(numParticles)

        for(i in 0..(numBuffers - 1)) {
            val stride = when(this.randomizeStride) {
                false -> 4
                true  -> random.nextInt(1, 4)
            }
            val buffer = ParticleBuffer(pool.maxParticles, stride)

            if(this.randomizeBufferData) {
                for(j in 0..(buffer.size - 1)) {
                    buffer[j] = random.nextFloat()
                }
            }

            pool.addBuffer(i.toString(), buffer)
        }

        return pool
    }
}

/**
 * Test suite for [ParticlePool].
 */
class ParticlePoolTest : ShouldSpec() {

    companion object {

        /**
         * The utility to generate randomized [ParticlePool] objects.
         */
        val PoolGen = ParticlePoolGenerator()
    }

    init {

        "waking up a single particle" {
            should("incrememnt the number of alive particles by one") {
                val pool = PoolGen.generate()

                pool.wake(1)
                pool.numAlive shouldBe 1
            }

            should("fail if at capacity") {
                val pool = PoolGen.generate()

                pool.wake(pool.maxParticles)
                shouldThrow<IllegalArgumentException> { pool.wake(1) }
            }
        }

        "waking up multiple particles" {
            should("incrememnt the number of alive particles by that amount") {
                val pool = PoolGen.generate()
                val amount =
                    ThreadLocalRandom.current().nextInt(1, pool.maxParticles)

                pool.wake(amount)
                pool.numAlive shouldBe amount
            }

            should("fail if at capacity") {
                val pool = PoolGen.generate()
                val amount =
                    ThreadLocalRandom.current().nextInt(1, 1000)

                pool.wake(pool.maxParticles)
                shouldThrow<IllegalArgumentException> { pool.wake(amount) }
            }
        }

        "putting a single particle to sleep" {
            should("reduce the number of active particles by one") {
                val pool = PoolGen.generate()

                pool.wake(2)
                pool.numAlive shouldBe 2

                pool.sleep(0)
                pool.numAlive shouldBe 1
            }
        }

        "putting multiple particles to sleep" {
            should("reduce the number of active particles by that amount") {
                val pool = PoolGen.generate()
                val amount =
                    ThreadLocalRandom.current().nextInt(1, pool.maxParticles)

                pool.wake(pool.maxParticles)
                pool.numAlive shouldBe pool.maxParticles

                pool.sleep(0, amount)
                pool.numAlive shouldBe (pool.maxParticles - amount)
            }
        }
    }
}