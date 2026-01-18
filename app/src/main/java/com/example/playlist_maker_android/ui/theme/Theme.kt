package com.example.playlist_maker_android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.YPBlack,
    onPrimary = AppColors.White,
    secondary = AppColors.White,
    onSecondary = AppColors.YPBlack,
    background = AppColors.YPBlue,
    onBackground = AppColors.White,
    surface = AppColors.YPBlack,
    onSurface = AppColors.White,
    onSurfaceVariant = AppColors.White,
    onTertiary = AppColors.YPBlack
)

private val LightColorScheme = lightColorScheme(
    primary = AppColors.White,
    onPrimary = AppColors.YPBlack,
    secondary = AppColors.YPLightGray,
    onSecondary = AppColors.YPTextGray,
    background = AppColors.YPBlue,
    onBackground = AppColors.White,
    surface = AppColors.White,
    onSurface = AppColors.YPBlack,
    onSurfaceVariant = AppColors.YPTextGray,
    onTertiary = AppColors.YPBlack
)

@Composable
fun PlaylistmakerandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppMaterialTypography,
        content = content
    )
}