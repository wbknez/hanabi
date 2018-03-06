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

package com.solsticesquared.hanabi.ui

import com.solsticesquared.hanabi.render.Animator
import com.solsticesquared.hanabi.render.RenderContext
import com.sun.javafx.binding.ContentBinding.bind
import javafx.geometry.Insets
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.borderpane
import tornadofx.button
import tornadofx.flowpane
import tornadofx.hbox
import tornadofx.hgrow
import tornadofx.label
import tornadofx.tab
import tornadofx.tabpane
import tornadofx.textfield
import tornadofx.toolbar

/**
 * Represents an implementation of [View] that serves as the basis of the
 * user interface for this project.
 */
class MainView : View() {

    private val animator by lazy{
        Animator(this.context, null, null)
    }

    private val context = RenderContext(600, 600)

    override val root = BorderPane()

    init {

        with(this.root) {

            // left: Example selection.

            center = borderpane {

                center = tabpane {

                    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

                    tab("Visualization") {
                        borderpane {
                            center = context.canvas
                        }
                    }
                    tab("Source Code") {
                        borderpane {
                        }
                    }
                }

                bottom = toolbar {
                    button("Play")
                    button("Pause")
                    button("Stop")
                    hbox(10) {
                        this.hgrow = Priority.ALWAYS
                    }
                    label("FPS:")
                    label("00.00")
                }
            }

            // right: Control/widget panel.
        }
    }
}