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
package com.ericktijerou.cabifystore.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.cabifystore.ui.bag.ShoppingBagViewModel
import com.ericktijerou.cabifystore.ui.component.CabifyBottomNavigationBar
import com.ericktijerou.cabifystore.ui.component.CabifyTopBar
import com.ericktijerou.cabifystore.ui.entity.ProductModelView
import com.ericktijerou.cabifystore.ui.home.EmptyComingSoon
import com.ericktijerou.cabifystore.ui.home.HomeScreen
import com.ericktijerou.cabifystore.ui.navigation.CabifyNavigationActions
import com.ericktijerou.cabifystore.ui.navigation.CabifyRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    shoppingBagViewModel: ShoppingBagViewModel,
    goToProduct: (ProductModelView) -> Unit,
    goToBag: () -> Unit
) {
    val bagSize by shoppingBagViewModel.bagSize.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        CabifyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: CabifyRoute.HOME
    Scaffold(
        topBar = {
            CabifyTopBar(goToBag = goToBag, bagSize = bagSize)
        },
        bottomBar = {
            CabifyBottomNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            goToProduct = goToProduct
        )
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    goToProduct: (ProductModelView) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CabifyRoute.HOME,
    ) {
        composable(CabifyRoute.HOME) {
            HomeScreen(viewModel = hiltViewModel(), goToProduct = goToProduct)
        }
        composable(CabifyRoute.FEED) {
            EmptyComingSoon()
        }
        composable(CabifyRoute.CATEGORIES) {
            EmptyComingSoon()
        }
        composable(CabifyRoute.ACCOUNT) {
            EmptyComingSoon()
        }
    }
}
