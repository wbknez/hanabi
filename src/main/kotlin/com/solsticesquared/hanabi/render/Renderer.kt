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

import com.solsticesquared.hanabi.ParticleSystem
import javafx.scene.canvas.GraphicsContext

/**
 * Represents a mechanism for drawing particle system data to a visual
 * display.
 */
interface Renderer {

    /**
     * Releases any and all resources associated with the specified graphical
     * display.
     *
     * @param gc
     *        The graphical display to draw to.
     */
    fun cleanUp(gc: GraphicsContext)

    /**
     * Releases any and all resources used by the specified particle system
     * that are also specific to the specified graphical display.
     *
     * @param system
     *        The particle system to clean up.
     * @param gc
     *        The graphical display to draw to.
     */
    fun cleanUpParticleSystem(system: ParticleSystem, gc: GraphicsContext)

    /**
     * Initializes any and all resources to be used with the specified
     * graphics display.
     *
     * @param gc
     *        The graphical display to draw to.
     */
    fun initialize(gc: GraphicsContext)

    /**
     * Initializes any and all resources used by the specific particle system
     * that are also specific to the specified graphical display.
     *
     * @param system
     *        The particle system to initialize rendering state for.
     * @param gc
     *        The graphical display to draw to.
     */
    fun initializeParticleSystem(system: ParticleSystem, gc: GraphicsContext)

    /**
     * Draws the specified particle system to the specified graphical display.
     *
     * @param system
     *        The particle system to draw.
     * @param gc
     *        The graphical display to draw to.
     */
    fun render(system: ParticleSystem, gc: GraphicsContext)
}