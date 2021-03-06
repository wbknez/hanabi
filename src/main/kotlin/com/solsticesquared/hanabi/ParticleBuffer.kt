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

import java.util.Arrays

/**
 * Represents a collection of particles who are themselves represented as a
 * collection of distinct yet contiguous blocks of data elements.
 *
 * @property data
 *           The contiguous array of particle data.
 * @property particles
 *           The total number of particles this buffer supports.
 * @property size
 *           The total number of data elements in the buffer.
 * @property stride
 *           The number of data elements assigned per particle.
 */
class ParticleBuffer(numParticles: Int, @JvmField val stride: Int = 4)
    : Cloneable, Iterable<Float> {

    private val data = FloatArray(numParticles * stride)

    val particles: Int
        get() = this.data.size / this.stride

    val size: Int
        get() = this.data.size

    /**
     * Constructor.
     *
     * @param particles
     *        The particle buffer to copy from.
     */
    constructor(particles: ParticleBuffer?)
        : this(particles!!.data.size, particles.stride) {
        System.arraycopy(particles.data, 0, this.data, 0,
                         particles.data.size)
    }

    override public fun clone(): ParticleBuffer = ParticleBuffer(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is ParticleBuffer -> this.data.contentEquals(other.data) &&
                                 this.stride == other.stride
            else              -> false
        }

    /**
     * Assigns every data element of this particle buffer to the specified
     * value.
     *
     * @param value
     *        The value to fill the buffer with.
     */
    fun fill(value: Float) {
        Arrays.fill(this.data, value)
    }

    operator fun get(index: Int): Float = this.data[index]

    override fun hashCode(): Int = hash(this.data, this.stride)

    override fun iterator(): Iterator<Float> = this.data.iterator()

    /**
     * Assigns the data element at the specified index in this particle buffer
     * to the specified value.
     *
     * @param index
     *        The index to assign a value to.
     * @param value
     *        The new value to use.
     * @return A reference to this particle buffer for easy chaining.
     */
    fun put(index: Int, value: Float): ParticleBuffer {
        this.data[index] = value
        return this
    }

    operator fun set(index: Int, value: Float) { this.data[index] = value }

    /**
     * Swaps the data blocks of the specified particles with one another,
     * performing an element-wise swap for every available data element up to
     * this particle buffer's stride.
     *
     * @param indexA
     *        The index of the first partcle to swap.
     * @param indexB
     *        The index of the second particle to swap.
     */
    fun swap(indexA: Int, indexB: Int) {
        val offSetA = indexA * stride
        val offSetB = indexB * stride

        for(i in 0..(this.stride - 1)) {
            this.data[offSetA + i] = this.data[offSetB + i].also {
                this.data[offSetB + i] = this.data[offSetA + i]
            }
        }
    }
}