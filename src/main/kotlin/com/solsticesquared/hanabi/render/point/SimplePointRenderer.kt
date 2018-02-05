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

package com.solsticesquared.hanabi.render.point

import com.solsticesquared.hanabi.ParticleSystem
import com.solsticesquared.hanabi.math.Color4
import com.solsticesquared.hanabi.math.Vector3
import com.solsticesquared.hanabi.render.RenderContext
import com.solsticesquared.hanabi.render.Renderer

/**
 * Represents an implementation of [Renderer] that draws each particle as a
 * constant-size point (filled circle) in two-dimensional space.
 *
 * @property size
 *           The radius of each point.
 */
class SimplePointRenderer(var size: Double = 5.0) : Renderer {

    override fun cleanUp(rc: RenderContext) {
    }

    override fun cleanUpParticleSystem(system: ParticleSystem,
                                       rc: RenderContext) {
    }

    override fun initialize(rc: RenderContext) {
    }

    override fun initializeParticleSystem(system: ParticleSystem,
                                          rc: RenderContext) {
    }

    override fun render(system: ParticleSystem, rc: RenderContext) {
        val colorBuffer = system.pool["color"]
        val posBuffer = system.pool["pos"]

        val color = Color4()
        val position = Vector3()

        rc.graphics.clearRect(rc.clip.y, rc.clip.y,
                              rc.clip.width, rc.clip.height)

        for(i in 0..(system.pool.numAlive - 1)) {
            color.marshal(i, colorBuffer)
            position.marshal(i, posBuffer)

            rc.graphics.fill = color.toPaint()
            rc.graphics.fillOval(position.x.toDouble(), position.y.toDouble(),
                                 this.size, this.size)
        }
    }
}