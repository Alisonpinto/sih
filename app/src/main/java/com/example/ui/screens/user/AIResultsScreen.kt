package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CallRecord
import com.example.data.models.DetectionVerdict
import com.example.data.models.RiskLevel
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.components.VoxShieldTopAppBar
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
import com.example.ui.viewmodels.CallViewModel

@Composable
fun AIResultsScreen(
  callViewModel: CallViewModel,
  onNavigateBack: (() -> Unit)? = null
) {
  val selectedCall by callViewModel.selectedCall.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("ai_results_screen")
  ) {
    VoxShieldTopAppBar(
      title = "AI Detection Results",
      subtitle = "Neural Acoustic Spectral Telemetry",
      onBackClick = onNavigateBack
    )

    if (selectedCall == null) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.GraphicEq,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(48.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Select a call log to view AI detection details",
            color = TextSecondary,
            fontSize = 14.sp
          )
        }
      }
    } else {
      val call = selectedCall!!
      val result = call.aiResult
      val isAi = call.verdict == DetectionVerdict.AI_DETECTED

      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        // Caller Info Header Card
        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = CyberDarkSurface,
          borderColor = CyberCardBorder
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(CyberCardBg)
                .border(1.dp, if (isAi) DangerRed else SafeGreen, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = if (isAi) DangerRed else SafeGreen,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = call.callerName,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${call.phoneNumber} • ${call.duration}",
                color = TextSecondary,
                fontSize = 12.sp
              )
              Text(
                text = "${call.time} • ${call.date}",
                color = TextMuted,
                fontSize = 11.sp
              )
            }
            StatusBadge(verdict = call.verdict, riskLevel = call.riskLevel)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // AI VERDICT CARD
        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = if (isAi) DangerRedSurface else SafeGreenSurface,
          borderColor = if (isAi) DangerRedBorder else SafeGreenBorder
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (isAi) Icons.Default.Warning else Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isAi) DangerRed else SafeGreen,
                modifier = Modifier.size(26.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = if (isAi) "AI-GENERATED VOICE DETECTED" else "GENUINE HUMAN VOICE VERIFIED",
                  color = if (isAi) DangerRed else SafeGreen,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "Confidence: ${result.confidencePercentage}% • Risk: ${result.riskLevel.name}",
                  color = TextPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CONFIDENCE BREAKDOWN
        Text(
          text = "SPECTRAL CONFIDENCE BREAKDOWN",
          color = TextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = CyberCardBg,
          borderColor = CyberCardBorder
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            // Voice Authenticity Bar
            ConfidenceRow(
              label = "Voice Authenticity",
              percentage = result.voiceAuthenticityPercentage,
              barColor = if (result.voiceAuthenticityPercentage > 50) SafeGreen else DangerRed
            )

            Spacer(modifier = Modifier.height(14.dp))

            // AI Probability Bar
            ConfidenceRow(
              label = "AI Synthesis Probability",
              percentage = result.aiProbabilityPercentage,
              barColor = if (result.aiProbabilityPercentage > 50) DangerRed else SafeGreen
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DETECTION FACTORS
        Text(
          text = "ANALYZED ACOUSTIC FACTORS",
          color = TextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = CyberDarkSurface,
          borderColor = CyberCardBorder
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            FactorRow(label = "Voice Spectral Pattern", value = result.spectralPatternStatus)
            FactorRow(label = "Speech Cadence & Prosody", value = result.speechPatternStatus)
            FactorRow(label = "Formant & Pitch Contour", value = result.prosodyStatus)
            FactorRow(label = "Harmonic Frequency Integrity", value = result.frequencyStatus, isLast = true)
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // RECOMMENDATION CARD
        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = CyberCardBg,
          borderColor = if (isAi) DangerRedBorder else CyberCardBorder
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = if (isAi) Icons.Default.Warning else Icons.Default.Shield,
              contentDescription = null,
              tint = if (isAi) DangerRed else CyberCyan,
              modifier = Modifier
                .size(22.dp)
                .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "SECURITY RECOMMENDATION",
                color = if (isAi) DangerRed else CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = result.recommendation,
                color = TextPrimary,
                fontSize = 13.sp,
                lineHeight = 18.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun ConfidenceRow(
  label: String,
  percentage: Int,
  barColor: androidx.compose.ui.graphics.Color
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = label, color = TextSecondary, fontSize = 13.sp)
      Text(text = "$percentage%", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(6.dp))
    LinearProgressIndicator(
      progress = { percentage.toFloat() / 100f },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = barColor,
      trackColor = CyberDarkSurface
    )
  }
}

@Composable
fun FactorRow(
  label: String,
  value: String,
  isLast: Boolean = false
) {
  val isSuspicious = value.contains("Suspicious", ignoreCase = true) || value.contains("Abnormal", ignoreCase = true)

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = label, color = TextSecondary, fontSize = 13.sp)
    Text(
      text = value,
      color = if (isSuspicious) DangerRed else SafeGreen,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold
    )
  }
}
