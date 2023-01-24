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
package com.ericktijerou.cabifystore.ui

import androidx.paging.PagingData
import com.ericktijerou.cabifystore.domain.entity.Product
import com.ericktijerou.cabifystore.domain.usecase.GetProductListUseCase
import com.ericktijerou.cabifystore.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HomeViewModelTest {
    private val productListUseCase = mockk<GetProductListUseCase>()
    private val viewModel = HomeViewModel(productListUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN HomeViewModelTest WHEN productListUseCase returns Success THEN returns data`() =
        runTest {
            val expected: Flow<PagingData<Product>> = flow { emit(PagingData.from(emptyList())) }
            coEvery { productListUseCase.invoke() } returns expected
            viewModel.productList
            coVerify { productListUseCase.invoke() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = Throwable::class)
    fun `GIVEN HomeViewModelTest WHEN productListUseCase returns Failure THEN throw exception`() =
        runTest {
            val exception = Exception()
            coEvery { productListUseCase.invoke() } throws exception
            viewModel.productList
        }
}