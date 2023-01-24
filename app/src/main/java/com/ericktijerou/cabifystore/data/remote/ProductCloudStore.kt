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

import com.ericktijerou.cabifystore.core.ApiException
import com.ericktijerou.cabifystore.core.orZero
import com.ericktijerou.cabifystore.data.entity.PageInfoModel
import com.ericktijerou.cabifystore.data.entity.ProductModel
import com.ericktijerou.cabifystore.data.remote.api.CabifyApi
import com.ericktijerou.cabifystore.data.remote.entity.ProductListResponse
import com.ericktijerou.cabifystore.data.remote.entity.toData

class ProductCloudStore(private val api: CabifyApi) {

    suspend fun getProductList(
        page: Int,
        pageSize: Int
    ): Pair<PageInfoModel, List<ProductModel>> {
        val productResponse = getProductResponse(page, pageSize)
        return PageInfoModel(
            productResponse.page.orZero().plus(1),
            productResponse.products.size == pageSize
        ) to productResponse.products.map { it.toData() }
    }

    private suspend fun getProductResponse(
        page: Int,
        pageSize: Int,
    ): ProductListResponse {
        val response = api.getProductList(page, pageSize)
        response.page = 1 // TODO Remove this line when the service supports pagination
        return try {
            response
        } catch (e: Exception) {
            throw ApiException()
        }
    }
}