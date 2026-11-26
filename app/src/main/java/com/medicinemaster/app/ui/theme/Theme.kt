package com.medicinemaster.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppBackground = Color(0xFF0B111B)
private val Surface = Color(0xFF141E2B)
private val SurfaceVariant = Color(0xFF1B2938)
private val Accent = Color(0xFF45D6C5)
private val AccentSoft = Color(0xFF173C3D)
private val TextPrimary = Color(0xFFF2F6FA)
private val TextSecondary = Color(0xFFA7B5C4)
private val Error = Color(0xFFFF7585)

private val AppColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = Color(0xFF06201E),
    secondary = Color(0xFF9BB8FF),
    onSecondary = Color(0xFF101B32),
    tertiary = Color(0xFFFFC477),
    background = AppBackground,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = Color(0xFF35070D)
)

@Composable
fun MedicineMasterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
