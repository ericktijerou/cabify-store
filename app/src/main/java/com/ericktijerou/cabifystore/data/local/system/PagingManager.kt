/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.cabifystore.data.local.system

import android.content.Context
import android.content.SharedPreferences

class PagingManager(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ericktijerou.cabifystore.preferences"
        private const val PREF_KEY_LAST_PRODUCT_CURSOR = "last_cursor"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastProductCursor: Int
        get() = pref.getInt(PREF_KEY_LAST_PRODUCT_CURSOR, 1)
        set(lastCursor) = pref.edit().putInt(PREF_KEY_LAST_PRODUCT_CURSOR, lastCursor).apply()
}