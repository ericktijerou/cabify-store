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

import androidx.paging.ExperimentalPagingApi
import com.ericktijerou.cabifystore.data.local.ProductDataStore
import com.ericktijerou.cabifystore.data.remote.ProductCloudStore
import com.ericktijerou.cabifystore.data.repository.ProductRepositoryImpl
import com.ericktijerou.cabifystore.mock.ModelMock.productEntity
import com.ericktijerou.cabifystore.mock.PagingSourceTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class ProductRepositoryTest {

    private val dataStore = mockk<ProductDataStore>()
    private val cloudStore = mockk<ProductCloudStore>()
    private val userRepository = ProductRepositoryImpl(cloudStore, dataStore)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN Repository WHEN getProductList THEN model is filled`() = runTest {
        val expected = PagingSourceTest(data = listOf(productEntity, productEntity))
        coEvery { dataStore.getProductList() } returns expected
        val response = userRepository.getProductList().first()
        assertNotNull(response)
    }
}