package com.example.demo.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SportsColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.OnPrimary,
    background = AppColors.Background,
    surface = AppColors.Surface,
    error = AppColors.Error
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SportsColorScheme,
        typography = AppTypography.default,
        content = content
    )
}
