package dev.davidodari.weatherupdates.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.davidodari.weatherupdates.R

private val fonts = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_medium, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val typography = typographyFromDefaults(
    displayLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    displayMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    displaySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    headlineLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        lineHeight = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    titleSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500
    ),
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    labelMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    ),
    labelSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold
    )
)

fun typographyFromDefaults(
    displayLarge: TextStyle?,
    displayMedium: TextStyle?,
    displaySmall: TextStyle?,
    headlineLarge: TextStyle?,
    headlineMedium: TextStyle?,
    headlineSmall: TextStyle?,
    titleLarge: TextStyle?,
    titleMedium: TextStyle?,
    titleSmall: TextStyle?,
    bodyLarge: TextStyle?,
    bodyMedium: TextStyle?,
    bodySmall: TextStyle?,
    labelLarge: TextStyle?,
    labelMedium: TextStyle?,
    labelSmall: TextStyle?,
): Typography {
    val defaults = Typography()
    return Typography(
        displayLarge = defaults.displayLarge.merge(displayLarge),
        displayMedium = defaults.displayMedium.merge(displayMedium),
        displaySmall = defaults.displaySmall.merge(displaySmall),
        headlineLarge = defaults.headlineLarge.merge(headlineLarge),
        headlineMedium = defaults.headlineMedium.merge(headlineMedium),
        headlineSmall = defaults.headlineSmall.merge(headlineSmall),
        titleLarge = defaults.titleLarge.merge(titleLarge),
        titleMedium = defaults.titleMedium.merge(titleMedium),
        titleSmall = defaults.titleSmall.merge(titleSmall),
        bodyLarge = defaults.bodyLarge.merge(bodyLarge),
        bodyMedium = defaults.bodyMedium.merge(bodyMedium),
        bodySmall = defaults.bodySmall.merge(bodySmall),
        labelLarge = defaults.labelLarge.merge(labelLarge),
        labelMedium = defaults.labelMedium.merge(labelMedium),
        labelSmall = defaults.labelSmall.merge(labelSmall)
    )
}
