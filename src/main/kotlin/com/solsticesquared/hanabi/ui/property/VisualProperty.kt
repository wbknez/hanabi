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

package com.solsticesquared.hanabi.ui.property

/**
 * Represents a [ComponentProperty] that denotes a descriptor of a single,
 * exclusive UI component that will allow the user to modify a property in
 * some way.
 *
 * Like [ComponentProperty], this annotation is a marker annotation.  The key
 * difference is that this annotation is also a marker of exclusivity -
 * properties may only be marked with a single annotation that has been
 * tagged with this one.
 */
@ComponentProperty
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class VisualProperty