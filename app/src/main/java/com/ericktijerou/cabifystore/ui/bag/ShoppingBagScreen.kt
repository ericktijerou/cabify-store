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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ericktijerou.cabifystore.R
import com.ericktijerou.cabifystore.ui.component.BagCard
import com.ericktijerou.cabifystore.ui.component.SheetContent
import com.ericktijerou.cabifystore.ui.entity.ProductBagModelView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ShoppingBagScreen(
    viewModel: ShoppingBagViewModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf("") }
    val list by viewModel.bagList.collectAsStateWithLifecycle(emptyList())
    val bagSize by viewModel.bagSize.collectAsStateWithLifecycle()
    val checkoutState by viewModel.checkout.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    if (showDialog.isNotEmpty()) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            text = { Text("Are you sure you want to remove this item?") },
            onDismissRequest = { showDialog = "" },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removeItem(showDialog)
                    showDialog = ""
                }) {
                    Text("REMOVE", style = MaterialTheme.typography.bodySmall)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = "" }) {
                    Text("CANCEL", style = MaterialTheme.typography.bodySmall)
                }
            },
            modifier = Modifier.padding(0.dp)
        )
    }
    Scaffold(
        topBar = {
            BagHeader(
                modifier = Modifier
                    .fillMaxWidth(), onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            BagFooter(
                totalPrice = checkoutState.total,
                bagSize = bagSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(color = MaterialTheme.colorScheme.onPrimary),
                listener = {
                    scope.launch {
                        if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
                            bottomSheetState.show()
                        } else {
                            bottomSheetState.hide()
                        }
                    }
                },
                summaryEnabled = bottomSheetState.currentValue != ModalBottomSheetValue.Hidden
            )
        },
        modifier = modifier
    ) {
        ModalBottomSheetLayout(
            sheetBackgroundColor = MaterialTheme.colorScheme.onPrimary,
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(10.dp),
            sheetContent = {
                SheetContent(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .padding(bottom = 72.dp),
                    checkoutState = checkoutState,
                    onCloseListener = {
                        scope.launch {
                            bottomSheetState.hide()
                        }
                    }
                )
            }) {
            BagList(list = list,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                onIncrease = { product -> viewModel.updateProduct(product.apply { quantity += 1 }) },
                onDecrease = { product ->
                    if (product.quantity == 1) {
                        showDialog = product.code
                    } else {
                        viewModel.updateProduct(product.apply { quantity -= 1 })
                    }
                })
        }
    }
}

@Composable
fun BagList(
    list: List<ProductBagModelView>,
    modifier: Modifier,
    onIncrease: (ProductBagModelView) -> Unit,
    onDecrease: (ProductBagModelView) -> Unit
) {
    if (list.isEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.label_bag_empty), textAlign = TextAlign.Center)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = list,
                itemContent = { _, product ->
                    BagCard(
                        product = product,
                        onIncrease = {
                            onIncrease(product)
                        },
                        onDecrease = {
                            onDecrease(product)
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun BagHeader(modifier: Modifier, onBackPressed: () -> Unit) {
    Surface(
        shadowElevation = 4.dp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.label_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(id = R.string.tab_bag),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun BagFooter(
    totalPrice: String,
    bagSize: Int,
    summaryEnabled: Boolean,
    modifier: Modifier,
    listener: () -> Unit
) {
    Surface(
        shadowElevation = 8.dp,
        modifier = modifier.clickable { listener() },
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = totalPrice,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(end = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            val icon = if (summaryEnabled) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.label_close),
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxHeight(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "${stringResource(id = R.string.label_checkout)} ($bagSize)",
                    color = Color.White
                )
            }
        }
    }
}

