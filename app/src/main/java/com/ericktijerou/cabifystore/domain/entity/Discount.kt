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
package com.ericktijerou.cabifystore.domain.entity

import com.ericktijerou.cabifystore.core.orZero

sealed class Discount(
    val groupSize: Int,
    val productId: String
) : IDiscount {

    class FreeDiscount(
        groupSize: Int,
        productId: String,
        private val freeSize: Int
    ) : Discount(groupSize, productId) {
        override fun computeDiscount(productsList: List<ProductBag>): Double {
            val productWithDiscount = productsList.firstOrNull { it.code == productId }
            val items = productWithDiscount?.quantity.orZero() / groupSize * freeSize.orZero()
            return items * productWithDiscount?.price.orZero()
        }
    }

    class BulkDiscount(
        groupSize: Int,
        productId: String,
        private val newPrice: Double
    ) : Discount(groupSize, productId) {
        override fun computeDiscount(productsList: List<ProductBag>): Double {
            val productWithDiscount = productsList.firstOrNull { it.code == productId }
            val price = productWithDiscount?.price.orZero()
            val discountPerProduct = price - newPrice.orZero()
            return if (productWithDiscount?.quantity.orZero() >= groupSize) discountPerProduct * productWithDiscount?.quantity.orZero() else 0.0
        }
    }
}

interface IDiscount {
    fun computeDiscount(productsList: List<ProductBag>): Double
}

