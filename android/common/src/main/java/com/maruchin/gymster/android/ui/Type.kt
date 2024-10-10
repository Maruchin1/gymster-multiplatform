package com.maruchin.gymster.android.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.maruchin.gymster.android.common.R

val bodyFontFamily = FontFamily(
    Font(resId = R.font.exo2_bold, weight = FontWeight.Bold),
    Font(resId = R.font.exo2_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.exo2_light, weight = FontWeight.Light),
    Font(resId = R.font.exo2_medium, weight = FontWeight.Medium),
    Font(resId = R.font.exo2_regular, weight = FontWeight.Normal)
)

val displayFontFamily = FontFamily(
    Font(resId = R.font.oswald_bold, weight = FontWeight.Bold),
    Font(resId = R.font.oswald_light, weight = FontWeight.Light),
    Font(resId = R.font.oswald_medium, weight = FontWeight.Medium),
    Font(resId = R.font.oswald_regular, weight = FontWeight.Normal)
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily)
)
