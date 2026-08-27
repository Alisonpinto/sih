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
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.RiskLevel
import com.example.data.models.SecurityAlert
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.components.VoxShieldTopAppBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCardBorderLight
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
import com.example.ui.theme.WarningYellowBorder
import com.example.ui.theme.WarningYellowSurface
import com.example.ui.viewmodels.AdminViewModel

@Composable
fun SecurityScreen(
  adminViewModel: AdminViewModel,
  onNavigateBack: () -> Unit
) {
  val alerts by adminViewModel.securityAlerts.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("security_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Security Overview",
      subtitle = "Real-time AI Threat Telemetry",
      onBackClick = onNavigateBack
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(6.dp))

        // 2x2 Grid of Security Status Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          SecurityMetricPill(
            title = "Voice Protection",
            status = "ACTIVE",
            icon = Icons.Default.Shield,
            isActive = true,
            modifier = Modifier.weight(1f)
          )
          SecurityMetricPill(
            title = "AI Detection",
            status = "ACTIVE",
            icon = Icons.Default.GraphicEq,
            isActive = true,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          SecurityMetricPill(
            title = "Call Monitoring",
            status = "ACTIVE",
            icon = Icons.Default.PhoneInTalk,
            isActive = true,
            modifier = Modifier.weight(1f)
          )
          SecurityMetricPill(
            title = "Threat Alerts",
            status = "${alerts.size}",
            icon = Icons.Default.Warning,
            isAlert = true,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
          text = "RECENT SECURITY EVENTS",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )
      }

      items(alerts, key = { it.id }) { alert ->
        SecurityAlertCard(alert = alert)
      }

      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun SecurityMetricPill(
  title: String,
  status: String,
  icon: ImageVector,
  modifier: Modifier = Modifier,
  isActive: Boolean = false,
  isAlert: Boolean = false
) {
  CyberCard(
    modifier = modifier,
    backgroundColor = CyberCardBg,
    borderColor = if (isAlert) DangerRedBorder else CyberCardBorder
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          color = TextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isAlert) DangerRed else SafeGreen,
          modifier = Modifier.size(16.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = status,
        color = if (isAlert) DangerRed else SafeGreen,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
      )
    }
  }
}

@Composable
fun SecurityAlertCard(alert: SecurityAlert) {
  CyberCard(
    modifier = Modifier.fillMaxWidth(),
    borderColor = if (alert.severity == RiskLevel.HIGH) DangerRedBorder else WarningYellowBorder,
    backgroundColor = CyberDarkSurface
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(if (alert.severity == RiskLevel.HIGH) DangerRedSurface else WarningYellowSurface)
          .border(
            1.dp,
            if (alert.severity == RiskLevel.HIGH) DangerRedBorder else WarningYellowBorder,
            RoundedCornerShape(10.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = if (alert.severity == RiskLevel.HIGH) DangerRed else WarningYellow,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = alert.title,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          StatusBadge(
            text = if (alert.severity == RiskLevel.HIGH) "High Risk" else "Warning",
            riskLevel = alert.severity
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = alert.description,
          color = TextSecondary,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = alert.timestamp,
          color = TextMuted,
          fontSize = 11.sp
        )
      }
    }
  }
}
