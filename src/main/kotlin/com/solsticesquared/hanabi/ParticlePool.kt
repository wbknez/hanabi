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
 * Represents a collection of [ParticleBuffer] objects that collectively
 * contains all of the necessary rendering and physics information for a
 * single, complete particle system to function.
 *
 * @property numAlive
 *           The number of particles that are currently active, or alive.
 * @property numDead
 *           The number of particles that are currently not alive.
 * @property maxParticles
 *           The total number of particles this particle pool can support.
 */
class ParticlePool(@JvmField val maxParticles: Int) {

    private val buffers = mutableMapOf<String, ParticleBuffer>()

    private val cache = mutableListOf<ParticleBuffer>()

    var numAlive: Int = 0
        private set

    val numDead: Int
        get() = this.maxParticles - this.numAlive

    /**
     * Creates a new particle buffer and adds it to this particle pool,
     * associating it with the specified name.
     *
     * @param name
     *        The name to use.
     * @param stride
     *        The number of data elements per particle.
     */
    fun addBuffer(name: String, stride: Int = 4) {
        require(stride > 0) {
            "The stride must be positive."
        }

        this.addBuffer(name, ParticleBuffer(this.maxParticles, stride))
    }

    /**
     * Adds the specified particle buffer to this particle pool, associating
     * it with the specified name.
     *
     * @param name
     *        The name to use.
     * @param buffer
     *        The particle buffer to add.
     */
    fun addBuffer(name: String, buffer: ParticleBuffer) {
        require(!this.buffers.containsKey(name)) {
            "A buffer with $name already exists."
        }

        this.buffers.put(name, buffer)
        this.cache.add(buffer)
    }

    operator fun get(name: String): ParticleBuffer {
        require(this.buffers.containsKey(name)) {
            "No buffer with $name exists."
        }

        return this.buffers.get(name)!!
    }

    /**
     * Finds and returns the particle buffer associated with the specified
     * name in this particle pool.
     *
     * @param name
     *        The name of the buffer to retrieve.
     */
    fun getBuffer(name: String): ParticleBuffer {
        return this.get(name)
    }

    /**
     * Removes the particle buffer with the specified name from this particle
     * pool.
     *
     * @param name
     *        The name of the buffer to remove.
     */
    fun removeBuffer(name: String) {
        require(this.buffers.containsKey(name)) {
            "No buffer with $name exists."
        }

        this.cache.remove(this.buffers.remove(name))
    }

    /**
     * Removes the particle at the specified index in this particle buffer
     * from the range of active particles, so that it will no longer
     * be updated or drawn.
     *
     * @param index
     *        The index of the particle to put to sleep.
     */
    fun sleep(index: Int) {
        require(index >= 0) {
            "The index must be positive."
        }

        require(index < this.numAlive) {
            "The index can be only as large as the number of woke particles."
        }

        for(buffer in this.cache) {
            buffer.swap(index, this.numAlive - 1)
        }

        this.numAlive -= 1
    }

    /**
     * Removes the particles at the specified indices from the range of
     * active particles, so that they will no longer be updated or drawn.
     *
     * @param first
     *        The index of the first particle to put to sleep, inclusive.
     * @param last
     *        The index of the last particle to put to sleep, exclusive.
     */
    fun sleep(first: Int, last:Int) {
        require(first <= last) {
            "First index must be at least as small as the last."
        }

        require(first >= 0) {
            "First index must be positive."
        }

        require(last < this.numAlive) {
            "Last index can be only as large as the number of woke particles."
        }

        for(buffer in this.cache) {
            var tempAlive = this.numAlive
            for(i in first..(last - 1)) {
                buffer.swap(i, tempAlive)
                tempAlive += 1
            }
        }
    }

    /**
     * Wakes the specified number of particles in this particle buffer so
     * they may be considered eligible to be updated and drawn.
     *
     * @param amount
     *        The number of particles to wake.
     */
    fun wake(amount: Int) {
        require(this.numAlive + amount <= this.maxParticles) {
            "Attempting to wake more particles than this buffer can support."
        }

        this.numAlive += amount
    }
}