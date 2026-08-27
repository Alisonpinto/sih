package com.example.ui.screens.auth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AudioWaveVisualizer
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.CyberElectricBlue
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onNavigateToLogin: () -> Unit
) {
  val alphaAnim = remember { Animatable(0f) }
  val scaleAnim = remember { Animatable(0.85f) }

  LaunchedEffect(Unit) {
    alphaAnim.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )
    scaleAnim.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )
    // Wait for ~2 seconds total then navigate
    delay(1200)
    onNavigateToLogin()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("splash_screen"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .alpha(alphaAnim.value)
        .scale(scaleAnim.value)
        .padding(32.dp)
    ) {
      // VoxShield Glowing Shield Icon
      Box(
        modifier = Modifier
          .size(100.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(CyberCyan.copy(alpha = 0.25f), CyberDarkSurface)
            )
          )
          .border(2.dp, CyberCyan, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Shield,
          contentDescription = "VoxShield Logo",
          tint = CyberCyan,
          modifier = Modifier.size(54.dp)
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      Text(
        text = "VOXSHIELD",
        fontSize = 32.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 4.sp,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "AI-powered voice protection",
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(36.dp))

      // Subtle audio wave animation
      AudioWaveVisualizer(
        modifier = Modifier.padding(horizontal = 48.dp),
        isScanning = true,
        barCount = 14,
        activeColor = CyberCyan
      )

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "SMART INDIA HACKATHON PROTOTYPE",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.5.sp,
        color = CyberCyan.copy(alpha = 0.8f)
      )
    }
  }
}
