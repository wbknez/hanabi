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
import io.kotlintest.properties.Gen
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [ParticleBuffer].
 */
class ParticleBufferTest : ShouldSpec() {

    init {

        "filling a particle buffer with a single value" {
            should("assign all data elements in the buffer to that " +
                   "value") {
                val randomSize = (Gen.float().generate() * 100f).toInt() + 1
                val buffer = ParticleBuffer(randomSize)
                val value = Gen.float().generate()

                buffer.fill(value)

                for(element in buffer) {
                    element shouldBe value
                }
            }
        }

        "swapping the data blocks of two distinct particles" {
            should("swap all elements inside those blocks correctly") {
                val buffer = ParticleBuffer(3)

                for(i in 0..(buffer.size - 1)) {
                    buffer[i] = i.toFloat()
                }

                buffer.swap(0, 2)

                buffer[0] shouldBe 8f
                buffer[1] shouldBe 9f
                buffer[2] shouldBe 10f
                buffer[3] shouldBe 11f
                buffer[4] shouldBe 4f
                buffer[5] shouldBe 5f
                buffer[6] shouldBe 6f
                buffer[7] shouldBe 7f
                buffer[8] shouldBe 0f
                buffer[9] shouldBe 1f
                buffer[10] shouldBe 2f
                buffer[11] shouldBe 3f
                buffer.size shouldBe 12
            }
        }
    }
}