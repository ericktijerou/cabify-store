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
package com.ericktijerou.cabifystore.ui.navigation

import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "main")
    object BagScreen : Screen(route = "bag")
    object ProductScreen : Screen(route = "product/{model}") {
        const val ARG_PRODUCT = "model"
        fun route(model: ProductModelView): String {
            val modelString = Gson().toJson(model)
            return "product/$modelString"
        }
    }
}