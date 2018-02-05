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

import com.solsticesquared.hanabi.Effect
import javafx.animation.AnimationTimer
import java.util.concurrent.ConcurrentLinkedQueue

typealias AnimationTask = (animator: Animator) -> Unit

/**
 * Represents a mechanism for drawing a particle system to a graphics display
 * repeatedly in a coordinated, consistent manner.
 *
 * @property context
 *           The graphics display to draw to.
 * @property effect
 *           The particle effect to draw.
 * @property fpsTimer
 *           The utility to measure both the average and instantaneous frames
 *           per second.
 * @property renderer
 *           The renderer to use.
 * @property tasks
 *           The list of tasks to execute on the rendering thread.
 */
class Animator(val context: RenderContext,
               var effect: Effect?,
               var renderer: Renderer?)
    : AnimationTimer() {

    val fpsTimer = FpsTimer()

    private val tasks = ConcurrentLinkedQueue<AnimationTask>()

    /**
     * Removes and executes all currently pending tasks on the rendering thread.
     */
    private fun doTasks() {
        var task = this.tasks.poll()

        while(task != null) {
            task.invoke(this)
            task = this.tasks.poll()
        }
    }

    override fun handle(now: Long) {
        this.doTasks()
        this.fpsTimer.update(now)

        this.effect?.update(this.fpsTimer.elapsedTime)
        this.effect?.render(this.renderer!!, this.context)
    }
}