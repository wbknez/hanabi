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

import kotlin.math.max
import kotlin.math.min

/**
 * Represents a simple clipping area that functions as a rendering optimization:
 * objects that fall within bounds are considered visible and thus drawn,
 * otherwise they are discarded.
 *
 * @property x
 *           The x-axis origin coordinate; this is typically zero.
 * @property y
 *           The y-axis origin coordinate; this is typically zero.
 * @property width
 *           The width of the clipping area.
 * @property height
 *           The height of the clipping area.
 */
data class ClipRect(var x: Double, var y: Double,
                    var width: Double, var height: Double) : Cloneable {

    /**
     * Constructor.
     *
     * @param x
     *        The x-axis origin coordinate to use, given as an integer.
     * @param y
     *        The y-axis origin coordinate to use, given as an integer.
     * @param width
     *        The width of the clipping area to use, given as an integer.
     * @param height
     *        The height of the clipping area to use, given as an integer.
     */
    constructor(x: Int, y: Int, width: Int, height: Int)
        : this(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    /**
     * Constructor.
     *
     * @param clip
     *        The clipping rectangle to copy from.
     */
    constructor(clip: ClipRect?)
        : this(clip!!.x, clip.y, clip.width, clip.height)

    public override fun clone(): ClipRect = ClipRect(this)

    /**
     * Computes whether or not a point with the specified x- and y-axis
     * coordinates lies within this clipping rectangle's bounds and,
     * therefore, whether or not it should be clipped.
     *
     * By convention, containment includes the boundary.
     *
     * @param x
     *        The x-axis coordinate to test.
     * @param y
     *        The y-axis coordinate to test.
     * @return Whether or not a point is contained within a clipping area.
     */
    fun contains(x: Double, y: Double): Boolean {
        return (x >= this.x && x <= this.width) &&
               (y >= this.y && y <= this.height)
    }

    /**
     * Computes whether or not a circle with the specified x- and y-axis
     * coordinates and radius lies within this clipping rectangle's bounds
     * and, there, whether or not it should be clipped.
     *
     * By convention, containment includes the boundary.
     *
     * @param x
     *        The circle's x-axis origin coordinate to test.
     * @param y
     *        The circle's y-axis origin cooordinate to test.
     * @param radius
     *        The radius of the circle.
     * @return Whether or not a circle is contained within a clipping area.
     */
    fun containsCircle(x: Double, y: Double, radius: Double): Boolean {
        val dX = x - max(this.x, min(x, this.width))
        val dY = y - max(this.y, min(y, this.height))
        return (dX * dX + dY * dY) <= (radius * radius)
    }

    fun containsLine(x0: Double, y0: Double, x1: Double, y1: Double): Boolean {
        // FIXME: Implement!
        return false
    }

    fun containsRect(x: Double, y: Double, width: Double, height: Double)
        : Boolean {
        // FIXME: Implement!
        return false
    }
}