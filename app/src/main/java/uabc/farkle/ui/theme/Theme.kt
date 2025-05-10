package uabc.farkle.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Colores personalizados (puedes mover estas definiciones a otro archivo si quieres)
@Immutable
data class ExtendedColorScheme(
    val customColor1: Color,
    val customColor1Container: Color,
    val onCustomColor1: Color,
    val onCustomColor1Container: Color,

    val customColor2: Color,
    val customColor2Container: Color,
    val onCustomColor2: Color,
    val onCustomColor2Container: Color,

    val customColor3: Color,
    val customColor3Container: Color,
    val onCustomColor3: Color,
    val onCustomColor3Container: Color
)

val extendedLight = ExtendedColorScheme(
    customColor1 = customColor1Light,
    customColor1Container = customColor1ContainerLight,
    onCustomColor1 = onCustomColor1Light,
    onCustomColor1Container = onCustomColor1ContainerLight,

    customColor2 = customColor2Light,
    customColor2Container = customColor2ContainerLight,
    onCustomColor2 = onCustomColor2Light,
    onCustomColor2Container = onCustomColor2ContainerLight,

    customColor3 = customColor3Light,
    customColor3Container = customColor3ContainerLight,
    onCustomColor3 = onCustomColor3Light,
    onCustomColor3Container = onCustomColor3ContainerLight
)

val extendedDark = ExtendedColorScheme(
    customColor1 = customColor1Dark,
    customColor1Container = customColor1ContainerDark,
    onCustomColor1 = onCustomColor1Dark,
    onCustomColor1Container = onCustomColor1ContainerDark,

    customColor2 = customColor2Dark,
    customColor2Container = customColor2ContainerDark,
    onCustomColor2 = onCustomColor2Dark,
    onCustomColor2Container = onCustomColor2ContainerDark,

    customColor3 = customColor3Dark,
    customColor3Container = customColor3ContainerDark,
    onCustomColor3 = onCustomColor3Dark,
    onCustomColor3Container = onCustomColor3ContainerDark
)

// CompositionLocal para los colores extendidos
val LocalExtendedColors = compositionLocalOf { extendedLight }

// Acceso desde MaterialTheme.extendedColors
val MaterialTheme.extendedColors: ExtendedColorScheme
    @Composable
    get() = LocalExtendedColors.current

// Color scheme principal
private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark
)

// FarkleTheme con soporte para colores extendidos
@Composable
fun FarkleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val extendedColors = if (darkTheme) extendedDark else extendedLight

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
