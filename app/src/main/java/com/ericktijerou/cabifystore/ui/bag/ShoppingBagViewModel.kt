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
package com.ericktijerou.cabifystore.ui.bag

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericktijerou.cabifystore.core.CURRENCY_SYMBOL
import com.ericktijerou.cabifystore.core.toPriceFull
import com.ericktijerou.cabifystore.domain.usecase.*
import com.ericktijerou.cabifystore.ui.entity.ProductBagModelView
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.entity.toDomain
import com.ericktijerou.cabifystore.ui.entity.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingBagViewModel @Inject constructor(
    private val addProductToBagUseCase: AddProductToBagUseCase,
    private val getBagListUseCase: GetBagListUseCase,
    private val updateProductBagUseCase: UpdateProductBagUseCase,
    private val getBagSizeUseCase: GetBagSizeUseCase,
    private val checkoutTotalUseCase: CheckoutTotalUseCase,
    private val removeProductBagUseCase: RemoveProductBagUseCase
) : ViewModel() {
    private val _bagList =
        MutableStateFlow<SnapshotStateList<ProductBagModelView>>(mutableStateListOf())
    val bagList: StateFlow<SnapshotStateList<ProductBagModelView>> = _bagList

    private val _onAddSuccess = MutableStateFlow(false)
    val onAddSuccess: StateFlow<Boolean> = _onAddSuccess

    private val _bagSize = MutableStateFlow(0)
    val bagSize: StateFlow<Int> = _bagSize

    private val _checkout = MutableStateFlow(CheckoutUIState())
    val checkout: StateFlow<CheckoutUIState> = _checkout

    init {
        observeBagList()
        observeBagSize()
        observeCheckout()
    }

    private fun observeBagList() {
        viewModelScope.launch(Dispatchers.IO) {
            getBagListUseCase().collect { list ->
                // TODO: This symbol should be obtained from an external data source
                val currencySymbol = CURRENCY_SYMBOL
                val newList = SnapshotStateList<ProductBagModelView>()
                list.forEach { newList.add(it.toView(currencySymbol)) }
                _bagList.value = newList
            }
        }
    }

    private fun observeBagSize() {
        viewModelScope.launch(Dispatchers.IO) {
            getBagSizeUseCase().collect {
                _bagSize.value = it
            }
        }
    }

    private fun observeCheckout() {
        viewModelScope.launch(Dispatchers.IO) {
            checkoutTotalUseCase().collect {
                // TODO: This symbol should be obtained from an external data source
                val currencySymbol = CURRENCY_SYMBOL
                _checkout.value = CheckoutUIState(
                    subTotal = it.subTotal.toPriceFull(currencySymbol),
                    discount = it.discount.toPriceFull(currencySymbol),
                    total = it.total.toPriceFull(currencySymbol)
                )
            }
        }
    }

    fun updateProduct(product: ProductBagModelView) {
        viewModelScope.launch(Dispatchers.IO) {
            updateProductBagUseCase(product.toDomain())
        }
    }

    fun addProductToBag(productModelView: ProductModelView) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToBagUseCase(productModelView.toDomain())
            _onAddSuccess.value = true
        }
    }

    fun removeItem(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductBagUseCase(productId)
        }
    }
}

data class CheckoutUIState(
    val subTotal: String = "",
    val discount: String = "",
    val total: String = ""
)
