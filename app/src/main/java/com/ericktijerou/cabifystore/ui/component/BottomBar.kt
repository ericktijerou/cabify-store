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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ericktijerou.cabifystore.R
import com.ericktijerou.cabifystore.ui.navigation.CabifyRoute
import com.ericktijerou.cabifystore.ui.navigation.CabifyTopLevelDestination
import com.ericktijerou.cabifystore.ui.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun CabifyBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (CabifyTopLevelDestination) -> Unit,
    modifier: Modifier
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 8.dp
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { cabifyDestination ->
            val selected = selectedDestination == cabifyDestination.route
            NavigationBarItem(
                selected = selected,
                onClick = { navigateToTopLevelDestination(cabifyDestination) },
                icon = {
                    if (cabifyDestination.route == CabifyRoute.HOME) {
                        val drawable =
                            if (selected) R.drawable.ic_home_full else R.drawable.ic_home
                        Icon(
                            imageVector = ImageVector.vectorResource(id = drawable),
                            contentDescription = stringResource(id = cabifyDestination.iconTextId),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            imageVector = if (selected) cabifyDestination.selectedIcon else cabifyDestination.unselectedIcon,
                            contentDescription = stringResource(id = cabifyDestination.iconTextId),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.inverseSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.3f),
                    indicatorColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}