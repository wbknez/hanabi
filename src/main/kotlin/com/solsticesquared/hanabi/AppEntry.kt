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

import com.solsticesquared.hanabi.ui.AppUi
import javafx.application.Application

/**
 * The main driver for the Hanabi Particle System Editor project.
 */
sealed class AppEntry {
    companion object {

        /**
         * The application entry point.
         *
         * @param args
         *        The array of command line arguments, if any.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(AppUi::class.java, *args)
        }
    }
}