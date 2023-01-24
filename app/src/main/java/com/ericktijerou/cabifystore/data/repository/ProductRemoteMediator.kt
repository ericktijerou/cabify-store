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

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ericktijerou.cabifystore.core.ApiException
import com.ericktijerou.cabifystore.data.local.ProductDataStore
import com.ericktijerou.cabifystore.data.local.entity.ProductEntity
import com.ericktijerou.cabifystore.data.remote.ProductCloudStore
import java.io.IOException

@ExperimentalPagingApi
class ProductRemoteMediator(
    private val local: ProductDataStore,
    private val remote: ProductCloudStore,
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as? Int
        }
        return try {
            val (pageInfo, userList) = remote.getProductList(
                page ?: 1,
                state.config.pageSize
            )
            local.doOperationInTransaction {
                if (loadType == LoadType.REFRESH) local.clearProducts()
                local.setLastProductCursor(pageInfo.endCursor)
                local.saveProductList(userList)
            }
            MediatorResult.Success(endOfPaginationReached = !pageInfo.hasNextPage)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: ApiException) {
            MediatorResult.Error(exception)
        }
    }

    private fun getKeyPageData(loadType: LoadType): Any? {
        return when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastProductCursor()
        }
    }
}