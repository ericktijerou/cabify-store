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

import androidx.paging.*
import com.ericktijerou.cabifystore.core.DEFAULT_PAGE_SIZE
import com.ericktijerou.cabifystore.data.local.ProductDataStore
import com.ericktijerou.cabifystore.data.local.entity.toDomain
import com.ericktijerou.cabifystore.data.remote.ProductCloudStore
import com.ericktijerou.cabifystore.domain.entity.Product
import com.ericktijerou.cabifystore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class ProductRepositoryImpl(
    private val remote: ProductCloudStore,
    private val local: ProductDataStore
) :
    ProductRepository {
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    override fun getProductList(): Flow<PagingData<Product>> {
        val pagingSourceFactory = { local.getProductList() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = ProductRemoteMediator(
                local,
                remote
            )
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}