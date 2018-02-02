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

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * Converts the specified floating point color component, bounded between
 * zero and one, to an integer representation on the interval zero to 255.
 *
 * @param f
 *        The color component to convert.
 * @return A floating point color component converted to an integer.
 */
private fun safeConvert(f: Float): Int {
    val safe_f = max(0f, min(1f, f))
    return when(safe_f == 1f) {
        false -> floor(256f * safe_f).toInt()
        true  -> 255
    }
}

/**
 * Represents a color in the floating point RGBA (red, green, blue, and alpha)
 * color space.
 *
 * @property red
 *           The red color component.
 * @property green
 *           The green color component.
 * @property blue
 *           The blue color component.
 * @property alpha
 *           The alpha color component.
 */
class Color4(red: Float = 0f, green: Float = 0f, blue: Float = 0f,
             alpha: Float = 0f) :
    Cloneable, Tuple4(red, green, blue, alpha) {

    var red: Float
        get() = this.x
        set(value) { this.x = value }

    var green: Float
        get() = this.y
        set(value) { this.y = value }

    var blue: Float
        get() = this.z
        set(value) { this.z = value }

    var alpha: Float
        get() = this.w
        set(value) { this.w = value }

    /**
     * Constructor.
     *
     * @param color
     *        The color to copy from.
     */
    constructor(color: Color4?)
        : this(color!!.red, color.green, color.blue, color.alpha)

    override fun clone(): Color4 = Color4(this)

    /**
     * Computes the linear interpolation of this color with the specified one
     * at the specified point in parametric time.
     *
     * @param t
     *        The parametric time to use.
     * @param color
     *        The "final" color to use.
     * @return A linearly interpolated color.
     */
    fun lerp(t: Float, color: Color4): Color4 =
        Color4(this.red + (color.red - this.red) * t,
               this.green + (color.green - this.green) * t,
               this.blue + (color.blue - this.blue) * t,
               this.alpha + (color.alpha - this.alpha) * t)

    operator fun minus(color: Color4): Color4 =
        Color4(this.red - color.red, this.green - color.green,
               this.blue - color.blue, this.alpha - color.alpha)

    /**
     * Subtracts this color from the specified one and also modifies this
     * color as a result.
     *
     * @param color
     *        The color to subtract with.
     * @return A reference to this color for easy chaining.
     */
    fun minusSelf(color: Color4): Color4 {
        this.red -= color.red
        this.green -= color.green
        this.blue -= color.blue
        this.alpha -= color.alpha

        return this
    }

    operator fun plus(color: Color4): Color4 =
        Color4(this.red + color.red, this.green + color.green,
               this.blue + color.blue, this.alpha + color.alpha)

    /**
     * Adds this color to the specified one and also modifies this color as a
     * result.
     *
     * @param color
     *        The color to add with.
     * @return A reference to this color for easy chaining.
     */
    fun plusSelf(color: Color4): Color4 {
        this.red += color.red
        this.green += color.green
        this.blue += color.blue
        this.alpha += color.alpha

        return this
    }

    operator fun times(scalar: Float): Color4 =
        Color4(this.red * scalar, this.green * scalar, this.blue * scalar,
               this.alpha * scalar)

    /**
     * Scales this color by the specified value and also modifies this
     * color as a result.
     *
     * @param scalar
     *        The value to scale by.
     * @return A reference to this color for easy chaining.
     */
    fun timesSelf(scalar: Float): Color4 {
        this.red *= scalar
        this.green *= scalar
        this.blue *= scalar
        this.alpha *= scalar

        return this
    }

    /**
     * Converts this color to a format that can be used by JavaFX to colorize
     * drawing operations.
     *
     * @return A JavaFX compatible color.
     */
    fun toPaint(): Paint = Color.rgb(safeConvert(this.red),
                                     safeConvert(this.green),
                                     safeConvert(this.blue),
                                     this.alpha.toDouble())
}