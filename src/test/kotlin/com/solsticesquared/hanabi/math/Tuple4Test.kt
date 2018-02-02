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

package com.solsticesquared.hanabi.math

import com.solsticesquared.hanabi.ParticleBuffer
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec
import java.util.concurrent.ThreadLocalRandom

/**
 * Represents an implementation of [Gen] that generates [ParticleBuffer]
 * objects with randomized size and data elements.
 */
class ParticleBufferGenerator(var maxParticles: Int = 50)
    : Gen<ParticleBuffer> {

    override fun generate(): ParticleBuffer {
        val random = ThreadLocalRandom.current()
        val buffer = ParticleBuffer(random.nextInt(1, this.maxParticles))

        for(i in 0..(buffer.size - 1)) {
            buffer[i] = random.nextFloat()
        }

        return buffer
    }
}

/**
 * Represents an implementation of [Gen] that generates [Tuple4] objects with
 * randomized components.
 */
class Tuple4Gen : Gen<Tuple4> {

    override fun generate(): Tuple4 =
        Tuple4(Gen.float().generate(), Gen.float().generate(),
               Gen.float().generate(), Gen.float().generate())
}

/**
 * Test suite [Tuple4].
 */
class Tuple4Test : ShouldSpec() {

    companion object {

        /**
         * The utility to generate randomized [ParticleBuffer] objects.
         */
        val BuffGen = ParticleBufferGenerator()

        /**
         * The utility to generate randomized [Tuple4] objects.
         */
        val TupGen = Tuple4Gen()
    }

    init {

        "marshalling a tuple from a particle buffer" {
            should("correctly copy data from the buffer to the tuple") {
                forAll(BuffGen, {buffer: ParticleBuffer ->
                    val random = ThreadLocalRandom.current()
                    val index = random.nextInt(0, buffer.particles)

                    val expected = Tuple4(buffer[index * buffer.stride],
                                          buffer[index * buffer.stride + 1],
                                          buffer[index * buffer.stride + 2],
                                          buffer[index * buffer.stride + 3])
                    val result = Tuple4()

                    result.marshal(index, buffer)
                    result == expected
                })
            }
        }

        "unmarshalling a tuple to a particle buffer" {
            should("correctly write data from the tuple to the buffer") {
                forAll(BuffGen, TupGen, {buffer: ParticleBuffer,
                                         tuple: Tuple4 ->
                    val random = ThreadLocalRandom.current()
                    val index = random.nextInt(0, buffer.particles)

                    tuple.unmarshal(index, buffer)

                    buffer[index * buffer.stride] == tuple.x &&
                    buffer[index * buffer.stride + 1] == tuple.y &&
                    buffer[index * buffer.stride + 2] == tuple.z &&
                    buffer[index * buffer.stride + 3] == tuple.w
                })
            }
        }
    }
}