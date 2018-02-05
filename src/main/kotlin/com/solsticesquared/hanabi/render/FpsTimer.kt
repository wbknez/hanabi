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

import javafx.beans.property.SimpleDoubleProperty

/**
 * Represents a simple mechanism for measuring both the average and
 * instantaneous frames per second for a graphical application.
 *
 * @param frameCount
 *        The number of frames to store as history.
 *
 * @property avgFpsProperty
 *           The average frames per second, given as a JavaFX [Double] property.
 * @property elapsedTime
 *           The amount of time, in fractions of a second, that has elapsed.
 * @property fpsProperty
 *           The instantaneous frames per second, given as a JavaFX [Double]
 *           property.
 * @property frameDeltas
 *           The array of elapsed frame times to use for averaging.
 * @property frameIndex
 *           The current frame to update.
 * @property lastTime
 *           The time stamp denoting the last update call.
 */
class FpsTimer(frameCount: Int = 100) {

    val avgFpsProperty = SimpleDoubleProperty()

    var elapsedTime = 0.0
        private set

    val fpsProperty = SimpleDoubleProperty()

    private val frameDeltas = LongArray(frameCount)

    private var frameIndex = 0

    private var lastTime = 0L

    /**
     * Updates both the instantaneous and average frames per second based on
     * the specified current time stamp.
     *
     * @param now
     *        A current timestamp, given in nanoseconds.
     */
    fun update(now: Long) {
        this.frameDeltas[this.frameIndex] = now - this.lastTime
        this.frameIndex = (this.frameIndex + 1) % this.frameDeltas.size
        this.lastTime = now

        this.avgFpsProperty.value =
            1000000000.0 / (this.frameDeltas.sum() / this.frameDeltas.size)
        this.elapsedTime = this.frameDeltas[this.frameIndex] / 1.0e-6
        this.fpsProperty.value =
            1000000000.0 / this.frameDeltas[this.frameIndex]
    }
}