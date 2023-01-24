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
package com.ericktijerou.cabifystore.data.entity

import com.ericktijerou.cabifystore.data.local.entity.BagEntity
import com.ericktijerou.cabifystore.data.local.entity.ProductEntity
import com.ericktijerou.cabifystore.domain.entity.Product

data class ProductModel(
    val code: String,
    val name: String,
    val price: Double,
    val backgroundColor: String
)

fun ProductModel.toLocal(): ProductEntity {
    return ProductEntity(code, name, price, backgroundColor)
}

fun ProductModel.toBag(): BagEntity {
    return BagEntity(code, name, price, backgroundColor, 1)
}

fun Product.toData(): ProductModel {
    return ProductModel(code, name, price, backgroundColor)
}