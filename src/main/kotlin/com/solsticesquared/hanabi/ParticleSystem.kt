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

import javafx.scene.image.Image

/**
 * Represents a collection of particle data and other parameters, such as a
 * point sprite image, that combine to form a complete particle system.
 *
 * @property emitters
 *           The list of particle emitters (each with associated generators).
 * @property pool
 *           The collection of particle buffer data.
 * @property sprite
 *           The point sprite image (optional).
 * @property updaters
 *           The list of particle updaters.
 */
class ParticleSystem(maxParticles: Int, val sprite: Image? = null) {

    val emitters = mutableListOf<ParticleEmitter>()

    val pool = ParticlePool(maxParticles)

    val updaters = mutableListOf<ParticleUpdater>()

    /**
     * Updates this particle system, emitting new particles and updating the
     * state of those already active.
     *
     * @param dt
     *        The amount of time that has elapsed since the last update,
     *        given in fractions of a second.
     */
    fun update(dt: Float) {
        this.emitters.forEach({ it.emit(dt, this.pool) })
        this.updaters.forEach({ it.update(dt, this.pool) })
    }
}