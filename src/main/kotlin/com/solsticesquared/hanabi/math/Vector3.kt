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

package com.solsticesquared.hanabi.math

import kotlin.math.sqrt

/**
 * Computes the inverse of the specified value, taking care to correctly
 * handle the case where that value may be either positive or negative zero.
 *
 * Differentiating between positive and negative zero allows algorithms that
 * call this method to correctly handle a "divide by zero" inverse attempt
 * while still preserving the sign of the result.
 *
 * @param f
 *        The value to invert.
 * @return The inverse of a value.
 */
private fun safeInverse(f: Float): Float {
    return when (f == 0f) {
        false -> 1f / f
        true  -> when (f == -0f) {
            false -> Float.POSITIVE_INFINITY
            true  -> Float.NEGATIVE_INFINITY
        }
    }
}

/**
 * Represents a mathematical vector in three dimensional space.
 *
 * Contrary to typical implementations, this vector is represented
 * programmatically as a tuple of four floating point numbers.  This was done
 * to reflect the use of a four-tuple as the standard format for a block of
 * particle data in order to easily align along power-of-two memory
 * boundaries (in this case, a 4 byte boundary).  As such, care should be
 * taken when marshalling and unmarshalling from a buffer as the fourth value
 * will likely be overwritten (usually a by a constant of one) due to it
 * being unused during vector operations.
 *
 * @property magnitude
 *           The length of the vector.
 * @property magnitudeSquared
 *           The length of the vector squared.
 */
class Vector3(x: Float = 0f, y: Float = 0f, z: Float = 0f)
    : Cloneable, Tuple4(x, y, z, 1f) {

    val magnitude: Float
        get() = sqrt(this.x * this.x + this.y * this.y + this.z * this.z)

    val magnitudeSquared: Float
        get() = this.x * this.x + this.y * this.y + this.z * this.z

    /**
     * Constructor.
     *
     * @param vec
     *        The vector to copy from.
     */
    constructor(vec: Vector3?) : this(vec!!.x, vec.y, vec.z)

    override fun clone(): Vector3 = Vector3(this)

    /**
     * Computes the cross product of this vector with the specified one.
     *
     * @param vec
     *        The vector to cross with.
     * @return The cross product.
     */
    fun cross(vec: Vector3): Vector3 =
        Vector3(this.y * vec.z - this.z * vec.y,
                this.z * vec.x - this.x * vec.z,
                this.x * vec.y - this.y * vec.x)

    /**
     * Computes the cross product of this vector with the specified one and
     * also modifies this vector as a result.
     *
     * @param vec
     *        The vector to cross with.
     * @return A reference to this vector for easy chaining.
     */
    fun crossSelf(vec: Vector3): Vector3 {
        val cX = this.y * vec.z - this.z * vec.y
        val cY = this.z * vec.x - this.x * vec.z
        val cZ = this.x * vec.y - this.y * vec.x

        this.x = cX; this.y = cY; this.z = cZ
        return this
    }

    /**
     * Computes the dot product of this vector with the specified one.
     *
     * @param vec
     *        The vector to dot with.
     * @return The dot product.
     */
    fun dot(vec: Vector3): Float =
        this.x * vec.x + this.y * vec.y + this.z * vec.z

    /**
     * Computes the inverse of this vector.
     *
     * @return The inverse.
     */
    fun invert(): Vector3 =
        Vector3(safeInverse(this.x), safeInverse(this.y), safeInverse(this.z))

    /**
     * Computes the inverse of this vector and also modifies this vector as a
     * result.
     *
     * @return A reference to this vector for easy chaining.
     */
    fun invertSelf(): Vector3 {
        this.x = safeInverse(this.x)
        this.y = safeInverse(this.y)
        this.z = safeInverse(this.z)
        return this
    }

    operator fun minus(vec: Vector3): Vector3 =
        Vector3(this.x - vec.x, this.y - vec.y, this.z - vec.z)

    /**
     * Subtracts this vector from the specified one and also modifies this
     * vector as a result.
     *
     * @param vec
     *        The vector to subtract with.
     * @return A reference to this vector for easy chaining.
     */
    fun minusSelf(vec: Vector3): Vector3 {
        this.x -= vec.x; this.y -= vec.y; this.z -= vec.z
        return this
    }

    /**
     * Computes the negation of this vector.
     *
     * @return The negation.
     */
    fun negate(): Vector3 = Vector3(-this.x, -this.y, -this.z)

    /**
     * Computes the negation of this vector and also modifies this vector as a
     * result.
     *
     * @return A reference to this vector for easy chaining.
     */
    fun negateSelf(): Vector3 {
        this.x = -this.x; this.y = -this.y; this.z = -this.z
        return this
    }

    /**
     * Computes the normalization of this vector by ensuring it has a length of
     * one.
     *
     * @return The normalized vector.
     */
    fun normalize(): Vector3 {
        val mag = this.magnitude
        return Vector3(this).apply {
            if(mag != 0f) {
                timesSelf(1f / mag)
            }
        }
    }

    /**
     * Computes the normalization of this vector by ensuring it has a length
     * of one and also modifies this vector as a result.
     *
     * @return A reference to this vector for easy chaining.
     */
    fun normalizeSelf(): Vector3 {
        val mag = this.magnitude

        if(mag != 0f) {
            this.timesSelf(1f / mag)
        }

        return this
    }

    operator fun plus(vec: Vector3): Vector3 =
        Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z)

    /**
     * Adds this vector and the specified one and also modifies this vector
     * as a result.
     *
     * @param vec
     *        The vector to add with.
     * @return A reference to this vector for easy chaining.
     */
    fun plusSelf(vec: Vector3): Vector3 {
        this.x += vec.x; this.y += vec.y; this.z += vec.z
        return this
    }

    operator fun times(scalar: Float): Vector3 =
        Vector3(this.x * scalar, this.y * scalar, this.z * scalar)

    /**
     * Scales this vector by the specified value and also modifies this
     * vector as a result.
     *
     * @param scalar
     *        The value to scale by.
     * @return A reference to this vector for easy chaining.
     */
    fun timesSelf(scalar: Float): Vector3 {
        this.x *= scalar; this.y *= scalar; this.z *= scalar
        return this
    }
}