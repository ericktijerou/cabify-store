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
package com.ericktijerou.cabifystore.data

import com.ericktijerou.cabifystore.core.ApiException
import com.ericktijerou.cabifystore.core.NotFoundException
import com.ericktijerou.cabifystore.data.remote.ProductCloudStore
import com.ericktijerou.cabifystore.data.remote.api.CabifyApi
import com.ericktijerou.cabifystore.data.remote.entity.ProductListResponse
import com.ericktijerou.cabifystore.mock.ModelMock.productListResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ProductCloudStoreTest {

    private val service = mockk<CabifyApi>()
    private val cloudStore = ProductCloudStore(service)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN CloudStore WHEN getProductList THEN return data`() = runTest {
        val expected = ProductListResponse().apply { products = productListResponse }
        coEvery { service.getProductList(any(), any()) } returns expected
        val response = cloudStore.getProductList(1, 1)
        coVerify { service.getProductList(any(), any()) }
        Assert.assertNotNull(response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = NotFoundException::class)
    fun `GIVEN CloudStore WHEN getProductList THEN return not found exception`() = runTest {
        coEvery { service.getProductList(any(), any()) } throws NotFoundException()
        val response = cloudStore.getProductList(1, 1)
        coVerify { service.getProductList(any(), any()) }
        Assert.assertNotNull(response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = ApiException::class)
    fun `GIVEN CloudStore WHEN getProductList THEN return api exception`() = runTest {
        coEvery { service.getProductList(any(), any()) } throws ApiException()
        val response = cloudStore.getProductList(1, 1)
        coVerify { service.getProductList(any(), any()) }
        Assert.assertNotNull(response)
    }
}