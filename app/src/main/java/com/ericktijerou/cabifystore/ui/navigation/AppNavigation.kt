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
package com.ericktijerou.cabifystore.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ericktijerou.cabifystore.ui.bag.ShoppingBagScreen
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.main.MainScreen
import com.ericktijerou.cabifystore.ui.product.ProductScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
    ) {
        composable(
            route = Screen.MainScreen.route,
            enterTransition = {
                tabEnterTransition(AnimatedContentScope.SlideDirection.End)
            },
            exitTransition = {
                tabExitTransition(AnimatedContentScope.SlideDirection.Start)
            }
        ) {
            MainScreen(
                shoppingBagViewModel = hiltViewModel(),
                goToProduct = {
                    navController.navigate(Screen.ProductScreen.route(it))
                },
                goToBag = {
                    navController.navigate(Screen.BagScreen.route)
                }
            )
        }

        composable(route = Screen.ProductScreen.route,
            enterTransition = {
                tabEnterTransition(AnimatedContentScope.SlideDirection.Start)
            },
            exitTransition = {
                tabExitTransition(AnimatedContentScope.SlideDirection.End)
            }
        ) {
            val modelString = it.arguments?.getString(Screen.ProductScreen.ARG_PRODUCT)
            val model = Gson().fromJson(modelString, ProductModelView::class.java)
            ProductScreen(
                viewModel = hiltViewModel(),
                product = model,
                onBackPressed = { navController.navigateUp() },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = Screen.BagScreen.route,
            enterTransition = {
                tabEnterTransition(AnimatedContentScope.SlideDirection.Start)
            },
            exitTransition = {
                tabExitTransition(AnimatedContentScope.SlideDirection.End)
            }
        ) {
            ShoppingBagScreen(
                viewModel = hiltViewModel(),
                onBackPressed = { navController.navigateUp() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.tabExitTransition(
    slideDirection: AnimatedContentScope.SlideDirection,
    duration: Int = 500
) = fadeOut(tween(duration / 2, easing = LinearEasing)) + slideOutOfContainer(
    slideDirection,
    tween(duration, easing = LinearEasing),
    targetOffset = { it / 24 }
)

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentScope<NavBackStackEntry>.tabEnterTransition(
    slideDirection: AnimatedContentScope.SlideDirection,
    duration: Int = 500,
    delay: Int = duration - 350
) = fadeIn(tween(duration, duration - delay)) + slideIntoContainer(
    slideDirection,
    animationSpec = tween(duration, duration - delay),
    initialOffset = { it / 24 }
)