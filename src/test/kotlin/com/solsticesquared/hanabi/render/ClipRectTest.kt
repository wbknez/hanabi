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

package com.solsticesquared.hanabi.render

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec
import java.util.concurrent.ThreadLocalRandom

/**
 * Represents an implementation of [Gen] that generates random [Integer] values
 * between a specific range.
 *
 * @property lowerBound
 *           The minimum value to generate, inclusive.
 * @property upperBound
 *           The maximum value to generate, exclusive.
 */
class BoundedIntGenerator(var lowerBound: Int, var upperBound: Int) : Gen<Int> {

    override fun generate(): Int =
        ThreadLocalRandom.current().nextInt(this.lowerBound, this.upperBound)
}

/**
 * Test suite for [ClipRect].
 */
class ClipRectTest : ShouldSpec() {

    init {

        "the containment of a point in a clipping area" {
            val rect = ClipRect(0, 0, 1920, 1200)
            val xGen = BoundedIntGenerator(0, 1920)
            val yGen = BoundedIntGenerator(0, 1200)

            should("be true when the point is inside the area") {
                forAll(xGen, yGen, {x: Int, y: Int ->
                    rect.contains(x.toDouble(), y.toDouble())
                })
            }

            should("also be true along the boundary") {
                rect.contains(0.0, 0.0) shouldBe true
                rect.contains(1920.0, 0.0) shouldBe true
                rect.contains(1920.0, 1200.0) shouldBe true
                rect.contains(0.0, 1200.0) shouldBe true
            }

            should("be false otherwise") {
                forAll(xGen, yGen, {x: Int, y: Int ->
                    val s = when(x in 0..1920) {
                        false -> x
                        true  -> x + 1920
                    }

                    val t = when(y in 0..1200) {
                        false -> y
                        true  -> 1200 + y
                    }

                    !rect.contains(s.toDouble(), t.toDouble())
                })
            }
        }

        "the containment of a circle in a clipping area" {
            val rect = ClipRect(0, 0, 1920, 1200)
            val xGen = BoundedIntGenerator(0, 1920)
            val yGen = BoundedIntGenerator(0, 1200)
            val rGen = BoundedIntGenerator(1, 100)

            should("be true when the circle overlaps the area") {
                forAll(xGen, yGen, rGen, {x: Int, y: Int, r: Int ->
                    rect.containsCircle(x.toDouble(), y.toDouble(), r.toDouble())
                })
            }

            should("also be true along the boundary") {
                rect.containsCircle(1920 / 2.0, 1200 / 2.0, 1920 / 2.0)
                rect.containsCircle(1920 / 2.0, 1200 / 2.0, 1200 / 2.0)
            }

            should("be false otherwise") {
                forAll(xGen, yGen, rGen, {x: Int, y: Int, r: Int ->
                    val s = when(x in -r..(1920 + r)) {
                        false -> x
                        true  -> 1920 + x  + r + 1
                    }

                    val t = when(y in -y..(1200 + y)) {
                        false -> y
                        true  -> 1200 + y + r + 1
                    }

                    !rect.containsCircle(s.toDouble(), t.toDouble(),
                                         r.toDouble())
                })
            }
        }
    }
}