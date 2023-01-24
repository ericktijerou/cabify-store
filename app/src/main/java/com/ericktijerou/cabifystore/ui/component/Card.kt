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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ericktijerou.cabifystore.ui.entity.ProductBagModelView
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.theme.CabifyTheme
import com.ericktijerou.cabifystore.ui.util.toColor

@Composable
fun ProductCard(
    product: ProductModelView,
    onProductClick: (ProductModelView) -> Unit
) {
    Card(
        shape = CabifyTheme.cardShape,
        modifier = Modifier
            .clip(CabifyTheme.cardShape)
            .fillMaxWidth()
            .clickable {
                onProductClick(product)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.name,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4 / 5f)
                    .background(product.backgroundColor.toColor())
            )
            Text(
                text = product.priceFormatted,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 10.dp),
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BagCard(
    product: ProductBagModelView,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        shape = CabifyTheme.cardShape,
        modifier = Modifier
            .clip(CabifyTheme.cardShape)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            AsyncImage(
                model = product.name,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(4 / 5f)
                    .background(product.backgroundColor.toColor())
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.priceFormatted,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Visible
                    )
                    Counter(
                        count = product.quantity,
                        onIncrease = onIncrease,
                        onDecrease = onDecrease
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExpandableCard(
    title: String,
    imageVector: ImageVector,
    imageDescription: String = title,
    alwaysDisplayedContent: @Composable (() -> Unit)? = null,
    collapsableContent: @Composable () -> Unit
) {
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    Card(
        onClick = { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("asdsadasdasd,asdnajkdhakjsdhajkdhaskjhdkajhdakjdhjkashd")
            }

            if (alwaysDisplayedContent != null) {
                alwaysDisplayedContent()
            }

            if (expanded) {
                collapsableContent()
            }
        }
    }
}
