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
import com.solsticesquared.hanabi.hash

/**
 * Represents a general vector-like mathematical object in four dimensional
 * space.
 *
 * @property x
 *           The x-axis coordinate.
 * @property y
 *           The y-axis coordinate.
 * @property z
 *           The z-axis coordinate.
 * @property w
 *           The w-axis coordinate.
 */
open class Tuple4(@JvmField var x: Float = 0f, @JvmField var y: Float = 0f,
                  @JvmField var z: Float = 0f, @JvmField var w: Float = 0f)
    : Cloneable {

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple4?) : this(tuple!!.x, tuple.y, tuple.z, tuple.w)

    public override fun clone(): Tuple4 = Tuple4(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Tuple4 -> this.x == other.x && this.y == other.y &&
                         this.z == other.z && this.w == other.w
            else      -> false
        }

    override fun hashCode(): Int = hash(this.x, this.y, this.z, this.w)

    /**
     * Reads the data for this tuple for a single particle at the specified
     * index in the specified particle buffer.
     *
     * @param index
     *        The index of the particle to use.
     * @param buffer
     *        The particle data to read from.
     */
    fun marshal(index: Int, buffer: ParticleBuffer) {
        require(buffer.stride == 4) {
            "The stride must be four."
        }

        this.x = buffer[index * 4]
        this.y = buffer[index * 4 + 1]
        this.z = buffer[index * 4 + 2]
        this.w = buffer[index * 4 + 3]
    }

    override fun toString(): String = "($this.x, $this.y, $this.z, $this.w)"

    /**
     * Writes the data for this tuple to a single particle at the specified
     * index in the specified particle buffer.
     *
     * @param index
     *        The index of the particle to use.
     * @param buffer
     *        The particle data to write to.
     */
    fun unmarshal(index: Int, buffer: ParticleBuffer) {
        require(buffer.stride == 4) {
            "The stride must be four."
        }

        buffer[index * 4] = this.x
        buffer[index * 4 + 1] = this.y
        buffer[index * 4 + 2] = this.z
        buffer[index * 4 + 3] = this.w
    }
}