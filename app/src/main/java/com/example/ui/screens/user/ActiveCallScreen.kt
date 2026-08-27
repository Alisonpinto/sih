package com.example.ui.screens.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.DetectionVerdict
import com.example.data.models.RiskLevel
import com.example.ui.components.AudioWaveVisualizer
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedBorder
import com.example.ui.theme.DangerRedSurface
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenBorder
import com.example.ui.theme.SafeGreenSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningYellow
import com.example.ui.viewmodels.ActiveCallPhase
import com.example.ui.viewmodels.CallViewModel

@Composable
fun ActiveCallScreen(
  callViewModel: CallViewModel,
  onCallEnded: () -> Unit
) {
  val callerName by callViewModel.activeCallerName.collectAsState()
  val phoneNumber by callViewModel.activePhoneNumber.collectAsState()
  val callSeconds by callViewModel.callSeconds.collectAsState()
  val callPhase by callViewModel.callPhase.collectAsState()
  val simulateAiCall by callViewModel.simulateAiCall.collectAsState()

  var isMuted by remember { mutableStateOf(false) }
  var isSpeaker by remember { mutableStateOf(true) }

  val formattedDuration = remember(callSeconds) {
    val mins = callSeconds / 60
    val secs = callSeconds % 60
    String.format("%02d:%02d", mins, secs)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .padding(horizontal = 24.dp, vertical = 20.dp)
      .testTag("active_call_screen"),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    // Top Simulation Mode Switcher for SIH Judges
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(CyberDarkSurface)
          .border(1.dp, CyberCardBorder, RoundedCornerShape(12.dp))
          .padding(4.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Surface(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
              callViewModel.startLiveCall(
                callerName = "Unknown Number",
                phoneNumber = "+91 88921 55670",
                forceAi = true
              )
            }
            .testTag("test_ai_call_btn"),
          color = if (simulateAiCall) DangerRedSurface else androidx.compose.ui.graphics.Color.Transparent,
          border = if (simulateAiCall) androidx.compose.foundation.BorderStroke(1.dp, DangerRed) else null
        ) {
          Text(
            text = "Test AI Deepfake",
            color = if (simulateAiCall) DangerRed else TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Surface(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
              callViewModel.startLiveCall(
                callerName = "Rahul Mehta",
                phoneNumber = "+91 98234 11223",
                forceAi = false
              )
            }
            .testTag("test_genuine_call_btn"),
          color = if (!simulateAiCall) SafeGreenSurface else androidx.compose.ui.graphics.Color.Transparent,
          border = if (!simulateAiCall) androidx.compose.foundation.BorderStroke(1.dp, SafeGreen) else null
        ) {
          Text(
            text = "Test Genuine Call",
            color = if (!simulateAiCall) SafeGreen else TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Caller Avatar & Details
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(CyberDarkSurface)
          .border(
            2.dp,
            when (callPhase) {
              is ActiveCallPhase.ResultReady -> {
                val res = callPhase as ActiveCallPhase.ResultReady
                if (res.verdict == DetectionVerdict.AI_DETECTED) DangerRed else SafeGreen
              }
              else -> CyberCyan
            },
            CircleShape
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = null,
          tint = when (callPhase) {
            is ActiveCallPhase.ResultReady -> {
              val res = callPhase as ActiveCallPhase.ResultReady
              if (res.verdict == DetectionVerdict.AI_DETECTED) DangerRed else SafeGreen
            }
            else -> CyberCyan
          },
          modifier = Modifier.size(44.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = callerName,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Text(
        text = phoneNumber,
        fontSize = 14.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 2.dp)
      )

      Text(
        text = formattedDuration,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = CyberCyan,
        modifier = Modifier.padding(top = 6.dp)
      )
    }

    // Central Live AI Detection Visualizer Banner
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val phase = callPhase) {
        is ActiveCallPhase.Connecting, ActiveCallPhase.Scanning -> {
          CyberCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = CyberDarkSurface,
            borderColor = CyberCyan
          ) {
            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Shield,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "AI ANALYSIS ACTIVE",
                  color = CyberCyan,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              AudioWaveVisualizer(
                isScanning = true,
                barCount = 20,
                activeColor = CyberCyan
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = "Scanning voice frequency & spectral acoustic signature...",
                color = TextSecondary,
                fontSize = 12.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }

        is ActiveCallPhase.ResultReady -> {
          val isAi = phase.verdict == DetectionVerdict.AI_DETECTED
          AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically()
          ) {
            CyberCard(
              modifier = Modifier.fillMaxWidth(),
              backgroundColor = if (isAi) DangerRedSurface else SafeGreenSurface,
              borderColor = if (isAi) DangerRedBorder else SafeGreenBorder
            ) {
              Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (isAi) Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (isAi) DangerRed else SafeGreen,
                    modifier = Modifier.size(22.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = if (isAi) "AI-GENERATED VOICE DETECTED" else "GENUINE HUMAN VOICE",
                    color = if (isAi) DangerRed else SafeGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = "Confidence: ${phase.confidence}% • Risk Level: ${phase.riskLevel.name}",
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                AudioWaveVisualizer(
                  isScanning = false,
                  barCount = 20,
                  activeColor = if (isAi) DangerRed else SafeGreen
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = if (isAi)
                    "Warning: Synthetic voice cues and unnatural prosody detected. Do not share financial or confidential data."
                  else
                    "Acoustic harmonics verify human vocal tract resonance.",
                  color = TextSecondary,
                  fontSize = 11.sp,
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                  lineHeight = 15.sp
                )
              }
            }
          }
        }
      }
    }

    // Call Action Controls & End Call Button
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CallControlButton(
          icon = Icons.Default.MicOff,
          label = if (isMuted) "Unmute" else "Mute",
          isActive = isMuted,
          onClick = { isMuted = !isMuted }
        )

        CallControlButton(
          icon = Icons.Default.VolumeUp,
          label = if (isSpeaker) "Speaker" else "Earpiece",
          isActive = isSpeaker,
          onClick = { isSpeaker = !isSpeaker }
        )

        CallControlButton(
          icon = Icons.Default.Security,
          label = "Shield Details",
          isActive = false,
          onClick = {}
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Large End Call Button
      Box(
        modifier = Modifier
          .size(68.dp)
          .clip(CircleShape)
          .background(DangerRed)
          .clickable { callViewModel.endLiveCall(onCallEnded) }
          .testTag("end_call_button"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.CallEnd,
          contentDescription = "End Call",
          tint = androidx.compose.ui.graphics.Color.White,
          modifier = Modifier.size(34.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))
    }
  }
}

@Composable
fun CallControlButton(
  icon: ImageVector,
  label: String,
  isActive: Boolean,
  onClick: () -> Unit
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .size(52.dp)
        .clip(CircleShape)
        .background(if (isActive) CyberCyan.copy(alpha = 0.2f) else CyberDarkSurface)
        .border(1.dp, if (isActive) CyberCyan else CyberCardBorder, CircleShape)
        .clickable { onClick() },
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = if (isActive) CyberCyan else TextSecondary,
        modifier = Modifier.size(24.dp)
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = label,
      color = TextSecondary,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium
    )
  }
}
