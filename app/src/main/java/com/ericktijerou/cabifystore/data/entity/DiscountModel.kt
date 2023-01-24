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

import com.ericktijerou.cabifystore.core.DiscountType
import com.ericktijerou.cabifystore.core.orZero
import com.ericktijerou.cabifystore.domain.entity.Discount

data class DiscountModel(
    @DiscountType val type: Int,
    val groupSize: Int,
    val freeSize: Int? = null,
    val newPrice: Double? = null,
    val productId: String
)

fun DiscountModel.toDomain(): Discount {
    return when (type) {
        DiscountType.FREE -> {
            Discount.FreeDiscount(
                groupSize,
                productId,
                freeSize.orZero()
            )
        }
        else -> {
            Discount.BulkDiscount(
                groupSize,
                productId,
                newPrice.orZero()
            )
        }
    }
}