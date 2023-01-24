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
package com.ericktijerou.cabifystore.data.remote.entity

import com.ericktijerou.cabifystore.core.orZero
import com.ericktijerou.cabifystore.core.randomColorHex
import com.ericktijerou.cabifystore.data.entity.ProductModel
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("code") var code: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("price") var price: Double? = null
)

fun ProductResponse.toData(): ProductModel {
    return ProductModel(
        code = code.orEmpty(),
        name = name.orEmpty(),
        price = price.orZero(),
        backgroundColor = randomColorHex()
    )
}