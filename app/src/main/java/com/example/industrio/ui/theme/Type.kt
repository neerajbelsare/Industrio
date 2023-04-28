package com.example.industrio.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.industrio.R

// Set of Material typography styles to start with
private val Roboto = FontFamily(
    Font(R.font.roboto_black, weight = FontWeight.Black),
    Font(R.font.roboto_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.roboto_italic, style = FontStyle.Italic),
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.roboto_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_thin, weight = FontWeight.Thin),
    Font(R.font.roboto_thinitalic, weight = FontWeight.Light, style = FontStyle.Italic),
)

private val GoogleSans = FontFamily(
    Font(R.font.googlesans_bold, weight = FontWeight.Bold),
    Font(R.font.googlesans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.googlesans_italic, style = FontStyle.Italic),
    Font(R.font.googlesans_medium, weight = FontWeight.Medium),
    Font(R.font.googlesans_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.googlesans_regular, weight = FontWeight.Normal),
    Font(R.font.googlesansdisplay_bold, weight = FontWeight.Bold),
    Font(R.font.googlesansdisplay_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.googlesansdisplay_italic, style = FontStyle.Italic),
    Font(R.font.googlesansdisplay_medium, weight = FontWeight.Medium),
    Font(R.font.googlesansdisplay_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.googlesansdisplay_regular, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = GoogleSans
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)