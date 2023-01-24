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
package com.ericktijerou.cabifystore.data.repository

import com.ericktijerou.cabifystore.data.entity.toData
import com.ericktijerou.cabifystore.data.entity.toDomain
import com.ericktijerou.cabifystore.data.local.BagDataStore
import com.ericktijerou.cabifystore.domain.entity.Product
import com.ericktijerou.cabifystore.domain.entity.ProductBag
import com.ericktijerou.cabifystore.domain.repository.BagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BagRepositoryImpl(private val bagDataStore: BagDataStore) :
    BagRepository {
    override suspend fun insert(productModel: Product) {
        bagDataStore.insert(productModel.toData())
    }

    override suspend fun updateBag(product: ProductBag) {
        bagDataStore.updateById(product.toData())
    }

    override suspend fun getBagList(): Flow<List<ProductBag>> {
        return bagDataStore.getBagList().map { it.map { bag -> bag.toDomain() } }
    }

    override suspend fun getBagSize(): Flow<Int> {
        return bagDataStore.getBagSize()
    }

    override suspend fun remove(id: String) {
        return bagDataStore.removeById(productId = id)
    }
}