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
package com.ericktijerou.cabifystore.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericktijerou.cabifystore.R
import com.ericktijerou.cabifystore.ui.util.circleLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CabifyTopBar(
    modifier: Modifier = Modifier,
    bagSize: Int,
    goToBag: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            var textState by remember { mutableStateOf(TextFieldValue()) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { /* Empty */ },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = ""
                    )
                }
                UserInputText(
                    textFieldValue = textState,
                    onTextChanged = { textState = it },
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f)
                )
                IconButton(
                    onClick = goToBag,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = stringResource(id = R.string.tab_bag),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        if (bagSize > 0) {
                            Text(
                                text = bagSize.toString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White
                                ),
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.TopEnd)
                                    .background(Color.Red, shape = RoundedCornerShape(40))
                                    .circleLayout(),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    )
}