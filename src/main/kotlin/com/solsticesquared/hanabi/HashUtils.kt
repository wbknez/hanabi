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
 * Represents a mechanism for hashing arbitrary numbers of objects.
 *
 * This method is inspired by both Python's method of hashing objects as well
 * as the Kotlin library HashKode, the latter from which the constants are
 * chosen.  This method allows classes to write one-line hash code
 * implementations without needing to concern themselves with best practices
 * or explicit result values.  The implementation itself comes from Josh
 * Bloch's "Effective Java" and uses both a prime initial value as well as a
 * per-object multiplier to achieve a reasonable degree of uniqueness, in
 * addition to holding up the general contract of [Object.hashCode].
 *
 * @param fields
 *        A variable list of object to hash.
 * @param initialValue
 *        The initial value to use; this should be prime.
 * @param multiplier
 *        The per-object hash multiplier to use; this should also be prime.
 * @return A reasonably unique hash code.
 */
fun hash(vararg fields: Any?, initialValue: Int = 17, multiplier: Int = 37)
    : Int {
    var result = initialValue
    fields.forEach {
        result = (result * multiplier) + (it?.hashCode() ?: 0)
    }
    return result
}
