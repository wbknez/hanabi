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
 * Represents a mechanism for emitting new particles into a particle system.
 *
 * @property generators
 *           The list of particle generators to use to initialze newly
 *           emitted particles.
 */
abstract class ParticleEmitter {

    val generators = mutableListOf<ParticleGenerator>()

    /**
     * Emits an arbitrary number of new particles and initializes them to
     * some state using arbitrary logic.
     *
     * @param dt
     *        The amount of time that has elapsed since the last update; given
     *        in seconds.
     * @param pool
     *        The pool of particle data to emit.
     */
    abstract fun emit(dt: Double, pool: ParticlePool)
}