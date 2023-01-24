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
package com.ericktijerou.cabifystore.domain.usecase

import com.ericktijerou.cabifystore.domain.entity.Checkout
import com.ericktijerou.cabifystore.domain.repository.BagRepository
import com.ericktijerou.cabifystore.domain.repository.DiscountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class CheckoutTotalUseCase(
    private val bagRepository: BagRepository,
    private val discountRepository: DiscountRepository
) {
    suspend operator fun invoke(): Flow<Checkout> {
        val discountList = discountRepository.getDiscountList()
        return bagRepository.getBagList().transform {
            val subTotal = it.sumOf { product -> product.price * product.quantity }
            var discount = 0.0
            discountList.forEach { dsc ->
                discount += dsc.computeDiscount(it)
            }
            val total = subTotal - discount
            emit(Checkout(subTotal, discount, total))
        }
    }
}