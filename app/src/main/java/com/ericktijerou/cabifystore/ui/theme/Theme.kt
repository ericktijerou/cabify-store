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
package com.ericktijerou.cabifystore.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val cabifyDarkColorScheme = darkColorScheme(
    primary = cabifyDarkPrimary,
    onPrimary = cabifyDarkOnPrimary,
    primaryContainer = cabifyDarkPrimaryContainer,
    onPrimaryContainer = cabifyDarkOnPrimaryContainer,
    inversePrimary = cabifyDarkPrimaryInverse,
    secondary = cabifyDarkSecondary,
    onSecondary = cabifyDarkOnSecondary,
    secondaryContainer = cabifyDarkSecondaryContainer,
    onSecondaryContainer = cabifyDarkOnSecondaryContainer,
    tertiary = cabifyDarkTertiary,
    onTertiary = cabifyDarkOnTertiary,
    tertiaryContainer = cabifyDarkTertiaryContainer,
    onTertiaryContainer = cabifyDarkOnTertiaryContainer,
    error = cabifyDarkError,
    onError = cabifyDarkOnError,
    errorContainer = cabifyDarkErrorContainer,
    onErrorContainer = cabifyDarkOnErrorContainer,
    background = cabifyDarkBackground,
    onBackground = cabifyDarkOnBackground,
    surface = cabifyDarkSurface,
    onSurface = cabifyDarkOnSurface,
    inverseSurface = cabifyDarkInverseSurface,
    inverseOnSurface = cabifyDarkInverseOnSurface,
    surfaceVariant = cabifyDarkSurfaceVariant,
    onSurfaceVariant = cabifyDarkOnSurfaceVariant,
    outline = cabifyDarkOutline
)

private val cabifyLightColorScheme = lightColorScheme(
    primary = cabifyLightPrimary,
    onPrimary = cabifyLightOnPrimary,
    primaryContainer = cabifyLightPrimaryContainer,
    onPrimaryContainer = cabifyLightOnPrimaryContainer,
    inversePrimary = cabifyLightPrimaryInverse,
    secondary = cabifyLightSecondary,
    onSecondary = cabifyLightOnSecondary,
    secondaryContainer = cabifyLightSecondaryContainer,
    onSecondaryContainer = cabifyLightOnSecondaryContainer,
    tertiary = cabifyLightTertiary,
    onTertiary = cabifyLightOnTertiary,
    tertiaryContainer = cabifyLightTertiaryContainer,
    onTertiaryContainer = cabifyLightOnTertiaryContainer,
    error = cabifyLightError,
    onError = cabifyLightOnError,
    errorContainer = cabifyLightErrorContainer,
    onErrorContainer = cabifyLightOnErrorContainer,
    background = cabifyLightBackground,
    onBackground = cabifyLightOnBackground,
    surface = cabifyLightSurface,
    onSurface = cabifyLightOnSurface,
    inverseSurface = cabifyLightInverseSurface,
    inverseOnSurface = cabifyLightInverseOnSurface,
    surfaceVariant = cabifyLightSurfaceVariant,
    onSurfaceVariant = cabifyLightOnSurfaceVariant,
    outline = cabifyLightOutline
)

private val LightColorPalette = CabifyColors(
    searchBoxColor = GraySearchBoxLight,
    isDark = false
)

private val DarkColorPalette = CabifyColors(
    searchBoxColor = GraySearchBoxDark,
    isDark = true
)

@Composable
fun CabifyStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val (colors, customColors) = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/
        darkTheme -> cabifyDarkColorScheme to DarkColorPalette
        else -> cabifyLightColorScheme to LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
        ProvideCabifyColors(customColors) {
            MaterialTheme(
                colorScheme = colors,
                typography = cabifyTypography,
                shapes = shapes,
                content = content
            )
        }
    }
}

object CabifyTheme {
    val colors: CabifyColors
        @Composable
        get() = LocalCabifyColors.current
    val cardShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = LocalCardShape.current
}

@Composable
fun ProvideCabifyColors(
    colors: CabifyColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalCabifyColors provides colorPalette, content = content)
}

@Stable
class CabifyColors(
    searchBoxColor: Color,
    isDark: Boolean
) {
    var searchBoxColor by mutableStateOf(searchBoxColor)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: CabifyColors) {
        searchBoxColor = other.searchBoxColor
        isDark = other.isDark
    }
}

private val LocalCabifyColors = staticCompositionLocalOf<CabifyColors> {
    error("No colors provided")
}

val LocalCardShape = staticCompositionLocalOf<Shape> { RoundedCornerShape(8.dp) }

