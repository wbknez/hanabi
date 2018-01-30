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

/**
 * Represents a collection of particles who are themselves represented as a
 * collection of distinct yet contiguous blocks of data elements.
 *
 * @property buffer
 *           The contiguous array of particle data.
 * @property particles
 *           The total number of particles this buffer supports.
 * @property size
 *           The total number of data elements in the buffer.
 * @property stride
 *           The number of data elements assigned per particle.
 */
class ParticleBuffer(numParticles: Int, val stride: Int = 4) {

    private val buffer = FloatArray(numParticles * stride)

    val particles: Int
        get() = this.buffer.size / this.stride

    val size: Int
        get() = this.buffer.size

    /**
     * Constructor.
     *
     * @param particles
     *        The particle buffer to copy from.
     */
    constructor(particles: ParticleBuffer?)
        : this(particles!!.buffer.size, particles.stride) {
        System.arraycopy(particles.buffer, 0, this.buffer, 0,
                         particles.buffer.size)
    }

    override fun equals(other: Any?): Boolean =
        when(other) {
            is ParticleBuffer -> this.buffer.contentEquals(other.buffer) &&
                                 this.stride == other.stride
            else              -> false
        }

    override fun hashCode(): Int = hash(this.buffer, this.stride)

    operator fun get(index: Int): Float = this.buffer[index]

    operator fun set(index: Int, value: Float) { this.buffer[index] = value }
}