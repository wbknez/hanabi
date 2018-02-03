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

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import kotlin.math.max
import kotlin.math.min

/**
 * Represents a collection of system-level rendering resources..
 *
 * @property canvas
 *           The JavaFX canvas component.
 * @property clip
 *           The clipping bounds.
 * @property graphics
 *           The drawing API.
 */
class RenderContext(val width: Double, val height: Double) {

    val canvas = Canvas(this.width, this.height)

    val clip = ClipRect(0.0, 0.0, this.width, this.height)

    val graphics: GraphicsContext
        get() = this.canvas.graphicsContext2D

    /**
     * Constructor.
     *
     * @param width
     *        The width to use as an integer.
     * @param height
     *        The height to use as an integer.
     */
    constructor(width: Int, height: Int)
        : this(width.toDouble(), height.toDouble())

    init {
        require(height > 0.0) {
            "Height must be positive."
        }

        require(width > 0.0) {
            "Width must be positive."
        }
    }
}