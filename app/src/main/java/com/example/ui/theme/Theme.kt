package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val VoxShieldDarkColorScheme = darkColorScheme(
  primary = CyberCyan,
  onPrimary = CyberBlack,
  primaryContainer = CyberDarkSurface,
  onPrimaryContainer = CyberCyan,
  secondary = CyberBlue,
  onSecondary = CyberBlack,
  secondaryContainer = CyberCardBg,
  onSecondaryContainer = TextPrimary,
  tertiary = CyberElectricBlue,
  onTertiary = TextPrimary,
  background = CyberBlack,
  onBackground = TextPrimary,
  surface = CyberDarkSurface,
  onSurface = TextPrimary,
  surfaceVariant = CyberCardBg,
  onSurfaceVariant = TextSecondary,
  outline = CyberCardBorder,
  outlineVariant = CyberCardBorderLight,
  error = DangerRed,
  onError = TextPrimary,
  errorContainer = DangerRedSurface,
  onErrorContainer = DangerRed
)

@Composable
fun VoxShieldTheme(
  content: @Composable () -> Unit
) {
  // VoxShield enforces a tailored cybersecurity dark theme across all screens
  MaterialTheme(
    colorScheme = VoxShieldDarkColorScheme,
    typography = Typography,
    content = content
  )
}
