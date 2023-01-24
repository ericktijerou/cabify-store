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
package com.ericktijerou.cabifystore.ui.entity

import com.ericktijerou.cabifystore.core.toPriceFormatted
import com.ericktijerou.cabifystore.domain.entity.ProductBag

data class ProductBagModelView(
    val code: String,
    val name: String,
    val priceFormatted: String,
    val backgroundColor: String,
    var quantity: Int,
    val price: Double
)

fun ProductBagModelView.toDomain(): ProductBag {
    return ProductBag(code, name, price, backgroundColor, quantity)
}

fun ProductBag.toView(currencySymbol: String): ProductBagModelView {
    return ProductBagModelView(
        code,
        name,
        price.toPriceFormatted(currencySymbol),
        backgroundColor,
        quantity,
        price
    )
}