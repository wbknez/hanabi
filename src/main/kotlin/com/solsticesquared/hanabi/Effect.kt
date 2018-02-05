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

import com.solsticesquared.hanabi.render.RenderContext
import com.solsticesquared.hanabi.render.Renderer
import javafx.scene.canvas.GraphicsContext

/**
 * Represents a collection of particle system data combined with
 * user-selectable user interface elements that together form a complete
 * particle system effect that may be customized and rendered repeatedly.
 *
 * @property name
 *           The name of the effect.
 * @property systems
 *           The list of particle systems attached to the effect.
 */
abstract class Effect(val name: String) {

    protected val systems = mutableListOf<ParticleSystem>()

    /**
     * Draws this effect using the specified renderer to the specified
     * graphical display.
     *
     * @param renderer
     *        The renderer to use.
     * @param rc
     *        The graphical display to draw to.
     */
    fun render(renderer: Renderer, rc: RenderContext) =
        this.systems.forEach({ renderer.render(it, rc) })

    /**
     * Updates this effect by performing a logical (non-render) update for
     * each individual particle system.
     *
     * This method should always be called before drawing.
     *
     * @param dt
     *        The amount of time that has elapsed since the last update call,
     *        given as a fraction of a second.
     */
    fun update(dt: Double) =
        this.systems.forEach({ it.update(dt) })
}