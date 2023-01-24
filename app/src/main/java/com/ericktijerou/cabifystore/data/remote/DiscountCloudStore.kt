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
package com.ericktijerou.cabifystore.data.remote

import com.ericktijerou.cabifystore.core.DiscountType
import com.ericktijerou.cabifystore.data.entity.DiscountModel
import com.ericktijerou.cabifystore.data.remote.api.CabifyApi

class DiscountCloudStore(private val api: CabifyApi) {

    // Discounts should be obtained from a service
    suspend fun getDiscountList(): List<DiscountModel> {
        return SAMPLE_DISCOUNTS
    }

    companion object {
        val SAMPLE_DISCOUNTS = listOf(
            DiscountModel(
                type = DiscountType.FREE,
                freeSize = 1,
                groupSize = 2,
                productId = "VOUCHER"
            ),
            DiscountModel(
                type = DiscountType.BULK,
                groupSize = 3,
                productId = "TSHIRT",
                newPrice = 19.00
            )
        )
    }
}