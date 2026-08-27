package com.example.ui.screens.admin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import com.example.data.models.Employee
import com.example.data.models.VoiceProfileStatus
import com.example.ui.components.AudioWaveVisualizer
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.components.VoxShieldButton
import com.example.ui.components.VoxShieldTopAppBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCardBorderLight
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningYellow
import com.example.ui.viewmodels.AdminViewModel
import com.example.ui.viewmodels.VoiceEnrollmentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceProfilesScreen(
  adminViewModel: AdminViewModel,
  onNavigateBack: () -> Unit
) {
  val employees by adminViewModel.employees.collectAsState()
  val enrollmentState by adminViewModel.voiceEnrollmentState.collectAsState()

  val enrolledCount = employees.count { it.voiceProfileStatus == VoiceProfileStatus.ENROLLED }
  val pendingCount = employees.count { it.voiceProfileStatus != VoiceProfileStatus.ENROLLED }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("voice_profiles_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Voice Profiles",
      subtitle = "$enrolledCount Enrolled • $pendingCount Pending",
      onBackClick = onNavigateBack
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(6.dp))

        // Info Banner
        CyberCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = CyberDarkSurface,
          borderColor = CyberCardBorderLight
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(CyberCardBg)
                .border(1.dp, CyberCyan, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = "VOICE BIOMETRIC DIRECTORY",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
              Text(
                text = "Acoustic baseline print registration prevents voice deepfakes during incoming telephony.",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
      }

      items(employees, key = { it.id }) { employee ->
        VoiceProfileRowCard(
          employee = employee,
          onEnrollClick = { adminViewModel.startVoiceEnrollment(employee) }
        )
      }

      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }

  // SIMULATED VOICE ENROLLMENT DIALOG
  if (enrollmentState !is VoiceEnrollmentState.Idle) {
    BasicAlertDialog(
      onDismissRequest = { adminViewModel.dismissVoiceEnrollmentDialog() },
      modifier = Modifier.testTag("voice_enrollment_dialog")
    ) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        color = CyberDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberCardBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          when (val state = enrollmentState) {
            is VoiceEnrollmentState.Recording -> {
              Box(
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .background(CyberCardBg)
                  .border(2.dp, CyberCyan, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Mic,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(36.dp)
                )
              }

              Spacer(modifier = Modifier.height(20.dp))

              Text(
                text = "Recording voice sample...",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )

              Text(
                text = "Capturing acoustic spectral data for ${state.employeeName}",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
              )

              AudioWaveVisualizer(
                isScanning = true,
                barCount = 16,
                activeColor = CyberCyan
              )
            }

            is VoiceEnrollmentState.Analyzing -> {
              Box(
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .background(CyberCardBg)
                  .border(2.dp, WarningYellow, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                CircularProgressIndicator(
                  modifier = Modifier.size(40.dp),
                  color = WarningYellow,
                  strokeWidth = 3.dp
                )
              }

              Spacer(modifier = Modifier.height(20.dp))

              Text(
                text = "Analyzing voice...",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )

              Text(
                text = "Extracting formant frequencies & harmonic pitch contour...",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
              )

              AudioWaveVisualizer(
                isScanning = true,
                barCount = 16,
                activeColor = WarningYellow
              )
            }

            is VoiceEnrollmentState.Success -> {
              Box(
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .background(SafeGreenSurface)
                  .border(2.dp, SafeGreen, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = SafeGreen,
                  modifier = Modifier.size(40.dp)
                )
              }

              Spacer(modifier = Modifier.height(20.dp))

              Text(
                text = "Voice profile successfully registered.",
                color = SafeGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )

              Text(
                text = "Verified biometric voice signature active for ${state.employeeName}",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
              )

              VoxShieldButton(
                text = "Done",
                onClick = { adminViewModel.dismissVoiceEnrollmentDialog() },
                modifier = Modifier.fillMaxWidth()
              )
            }

            VoiceEnrollmentState.Idle -> {}
          }
        }
      }
    }
  }
}

@Composable
fun VoiceProfileRowCard(
  employee: Employee,
  onEnrollClick: () -> Unit
) {
  CyberCard(
    modifier = Modifier.fillMaxWidth(),
    borderColor = if (employee.voiceProfileStatus == VoiceProfileStatus.ENROLLED) CyberCardBorder else CyberCardBorderLight,
    backgroundColor = CyberCardBg
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(CyberDarkSurface)
            .border(
              1.dp,
              when (employee.voiceProfileStatus) {
                VoiceProfileStatus.ENROLLED -> SafeGreen
                VoiceProfileStatus.PENDING -> WarningYellow
                VoiceProfileStatus.NOT_ENROLLED -> TextMuted
              },
              CircleShape
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Fingerprint,
            contentDescription = null,
            tint = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> SafeGreen
              VoiceProfileStatus.PENDING -> WarningYellow
              VoiceProfileStatus.NOT_ENROLLED -> TextMuted
            },
            modifier = Modifier.size(22.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = employee.name,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> "Voice Registered — Verified"
              VoiceProfileStatus.PENDING -> "Voice Pending"
              VoiceProfileStatus.NOT_ENROLLED -> "Not Enrolled"
            },
            color = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> SafeGreen
              VoiceProfileStatus.PENDING -> WarningYellow
              VoiceProfileStatus.NOT_ENROLLED -> TextMuted
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      if (employee.voiceProfileStatus != VoiceProfileStatus.ENROLLED) {
        VoxShieldButton(
          text = "Register Voice",
          onClick = onEnrollClick,
          isSecondary = true,
          modifier = Modifier.height(40.dp)
        )
      } else {
        StatusBadge(text = "Verified", verdict = com.example.data.models.DetectionVerdict.GENUINE)
      }
    }
  }
}
