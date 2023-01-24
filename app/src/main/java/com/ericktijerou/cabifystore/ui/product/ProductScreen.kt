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
package com.ericktijerou.cabifystore.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ericktijerou.cabifystore.R
import com.ericktijerou.cabifystore.ui.bag.ShoppingBagViewModel
import com.ericktijerou.cabifystore.ui.component.RatingBar
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.util.toColor
import com.ericktijerou.cabifystore.ui.util.verticalGradientScrim

@Composable
fun ProductScreen(
    viewModel: ShoppingBagViewModel,
    product: ProductModelView,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.onAddSuccess.collectAsState().value
    if (state) onBackPressed()
    Box(modifier = modifier.background(color = MaterialTheme.colorScheme.onPrimary)) {
        ProductContent(
            product = product,
            onBackPressed = onBackPressed,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
        ProductFooter(
            Modifier.align(Alignment.BottomCenter)
        ) {
            viewModel.addProductToBag(product)
        }
    }
}

@Composable
fun ProductContent(product: ProductModelView, onBackPressed: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Box {
            AsyncImage(
                model = product.name,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4 / 5f)
                    .background(product.backgroundColor.toColor())
            )
            HeaderGradient(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp), 1f, 0f
            )
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.label_back),
                    tint = Color.White
                )
            }
        }
        ProductDescription(product)
    }
}

@Composable
fun HeaderGradient(modifier: Modifier, startYPercentage: Float, endYPercentage: Float) {
    Spacer(
        modifier = modifier.verticalGradientScrim(
            color = Color.Black.copy(alpha = 0.3f),
            startYPercentage = startYPercentage,
            endYPercentage = endYPercentage
        )
    )
}

@Composable
fun ProductFooter(modifier: Modifier, addToBagListener: () -> Unit) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.label_favorite),
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
            )
            Button(
                onClick = addToBagListener,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxSize()
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_to_bag),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProductDescription(product: ProductModelView) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = stringResource(R.string.label_share),
            modifier = Modifier.size(30.dp)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.priceFormatted,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(rating = 4.5f)
            Text(
                text = "(1000+)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
