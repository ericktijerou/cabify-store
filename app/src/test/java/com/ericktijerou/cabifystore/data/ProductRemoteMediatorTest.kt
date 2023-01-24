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

import androidx.paging.*
import com.ericktijerou.cabifystore.data.entity.PageInfoModel
import com.ericktijerou.cabifystore.data.local.ProductDataStore
import com.ericktijerou.cabifystore.data.local.entity.ProductEntity
import com.ericktijerou.cabifystore.data.remote.ProductCloudStore
import com.ericktijerou.cabifystore.data.repository.ProductRemoteMediator
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRemoteMediatorTest {
    private val dataStore = mockk<ProductDataStore>()
    private val cloudStore = mockk<ProductCloudStore>()

    @OptIn(ExperimentalPagingApi::class)
    private val mediator = ProductRemoteMediator(dataStore, cloudStore)

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `GIVEN Mediator WHEN getProductList THEN return data`() = runTest {
        val pagingState = PagingState<Int, ProductEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        coEvery {
            cloudStore.getProductList(
                any(),
                any()
            )
        } returns Pair(PageInfoModel(1, true), listOf())
        coEvery {
            dataStore.doOperationInTransaction(captureCoroutine())
        } coAnswers {
            coroutine<suspend () -> Unit>().coInvoke()
        }
        coEvery { dataStore.clearProducts() } just runs
        coEvery { dataStore.getLastProductCursor() } returns 1
        coEvery { dataStore.setLastProductCursor(1) } just runs
        coEvery { dataStore.saveProductList(listOf()) } just runs
        val result = mediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}