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
package com.ericktijerou.cabifystore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ericktijerou.cabifystore.core.CURRENCY_SYMBOL
import com.ericktijerou.cabifystore.domain.usecase.GetProductListUseCase
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.entity.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
) : ViewModel() {

    val productList: Flow<PagingData<ProductModelView>> by lazy {
        // TODO: This symbol should be obtained from an external data source
        val currencySymbol = CURRENCY_SYMBOL
        getProductListUseCase().map { pagingData ->
            pagingData.map { it.toView(currencySymbol) }
        }.cachedIn(viewModelScope)
    }
}