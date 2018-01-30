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
class ParticleBuffer(numParticles: Int, val stride: Int = 4) {

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

    override fun hashCode(): Int = hash(this.data, this.stride)

    operator fun get(index: Int): Float = this.data[index]

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
        for(i in 0..(this.stride - 1)) {
            this.data[indexA + i] = this.data[indexB + i].also {
                this.data[indexB + i] = this.data[indexA + i]
            }
        }
    }
}