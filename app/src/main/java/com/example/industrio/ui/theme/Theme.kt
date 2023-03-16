package com.example.industrio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = MainColor,
    primaryVariant = MainColor,
    secondary = MainColor
)

private val LightColorPalette = lightColors(
    primary = MainColor,
    primaryVariant = MainColor,
    secondary = MainColor

//    background = Color.White,
//    surface = Color.White,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
)

@Composable
fun IndustrioTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun MapsComposeSampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColors() else lightColors(),
        content = content
    )
}