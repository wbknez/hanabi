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
 * Represents a mechanism for generating initial particle states.
 */
interface ParticleGenerator {

    /**
     * Initializes the particle data in the specified particle pool from the
     * specified ranges using arbitrary logic.
     *
     * @param dt
     *        The amount of time that has elapsed since the last update; given
     *        in seconds.
     * @param pool
     *        The pool of particle data to use.
     * @param first
     *        The index of the first particle to generate state for, inclusive.
     * @param last
     *        The index of the last particle to generate state for, exclusive.
     */
    fun generate(dt: Double, pool: ParticlePool, first: Int, last: Int)
}