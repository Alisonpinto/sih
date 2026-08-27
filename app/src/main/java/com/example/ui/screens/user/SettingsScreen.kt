package com.example.ui.screens.user

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.components.CyberCard
import com.example.ui.components.VoxShieldButton
import com.example.ui.components.VoxShieldTopAppBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  authViewModel: AuthViewModel,
  onNavigateBack: () -> Unit,
  onLogout: () -> Unit
) {
  var aiDetectionEnabled by remember { mutableStateOf(true) }
  var callAlertsEnabled by remember { mutableStateOf(true) }
  var riskNotificationsEnabled by remember { mutableStateOf(true) }

  var dialogTitle by remember { mutableStateOf<String?>(null) }
  var dialogContent by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("settings_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Settings",
      subtitle = "System & Security Preferences",
      onBackClick = onNavigateBack
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // 1. SECURITY CONFIGURATION
      Text(
        text = "SECURITY ENGINE",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CyberDarkSurface,
        borderColor = CyberCardBorder
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          SettingsSwitchRow(
            title = "AI Call Detection",
            subtitle = "Analyze incoming audio spectrum for deepfake cues",
            icon = Icons.Default.Shield,
            checked = aiDetectionEnabled,
            onCheckedChange = { aiDetectionEnabled = it }
          )

          Spacer(modifier = Modifier.height(10.dp))

          SettingsSwitchRow(
            title = "Real-time Call Alerts",
            subtitle = "Display visual danger alerts during active suspicious calls",
            icon = Icons.Default.Vibration,
            checked = callAlertsEnabled,
            onCheckedChange = { callAlertsEnabled = it }
          )

          Spacer(modifier = Modifier.height(10.dp))

          SettingsSwitchRow(
            title = "High-Risk Push Notifications",
            subtitle = "Notify administrators upon recurring spoofing attempts",
            icon = Icons.Default.Notifications,
            checked = riskNotificationsEnabled,
            onCheckedChange = { riskNotificationsEnabled = it }
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 2. ACCOUNT SECTION
      Text(
        text = "ACCOUNT",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CyberCardBg,
        borderColor = CyberCardBorder
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          SettingsClickableRow(
            title = "Notifications Preference",
            icon = Icons.Default.Notifications,
            onClick = {
              dialogTitle = "Notifications"
              dialogContent = "In-app and system security notifications are enabled by default for rapid incident response."
            }
          )

          SettingsClickableRow(
            title = "Security & Privacy",
            icon = Icons.Default.Security,
            onClick = {
              dialogTitle = "Security & Privacy"
              dialogContent = "All biometric acoustic voiceprints are encrypted locally and never transmitted across unauthenticated channels."
            }
          )

          SettingsClickableRow(
            title = "Change Password",
            icon = Icons.Default.Lock,
            onClick = {
              dialogTitle = "Change Password"
              dialogContent = "Password updates can be requested through your organization administrator (admin001)."
            }
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 3. APPLICATION & INFO
      Text(
        text = "APPLICATION",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CyberCardBg,
        borderColor = CyberCardBorder
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.DarkMode, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(12.dp))
              Text(text = "Appearance Theme", color = TextPrimary, fontSize = 14.sp)
            }
            Text(text = "Cyber Dark", color = CyberCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          SettingsClickableRow(
            title = "About VoxShield",
            icon = Icons.Default.Info,
            onClick = {
              dialogTitle = "About VoxShield"
              dialogContent = "VoxShield v1.0.0 (SIH Prototype Edition)\nAI-Powered Voice Deepfake Detection & Telephony Protection Platform."
            }
          )

          SettingsClickableRow(
            title = "Terms & Security Policy",
            icon = Icons.Default.Policy,
            onClick = {
              dialogTitle = "Terms & Security Policy"
              dialogContent = "VoxShield provides supplementary voice security intelligence for organizations and enterprises."
            }
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Logout button
      VoxShieldButton(
        text = "Logout",
        onClick = { authViewModel.logout(onLogout) },
        isSecondary = true,
        leadingIcon = Icons.AutoMirrored.Filled.ExitToApp,
        modifier = Modifier.fillMaxWidth(),
        testTag = "settings_logout_btn"
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
  }

  // Dialog for info popup
  if (dialogTitle != null) {
    BasicAlertDialog(onDismissRequest = { dialogTitle = null }) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = CyberDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberCardBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = dialogTitle ?: "",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = dialogContent ?: "",
            color = TextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(20.dp))
          VoxShieldButton(
            text = "Close",
            onClick = { dialogTitle = null },
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
  }
}

@Composable
fun SettingsSwitchRow(
  title: String,
  subtitle: String,
  icon: ImageVector,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(CyberCardBg)
          .border(1.dp, CyberCardBorder, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = CyberCyan,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(text = title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Text(text = subtitle, color = TextSecondary, fontSize = 11.sp, lineHeight = 14.sp)
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = CyberBlack,
        checkedTrackColor = CyberCyan,
        uncheckedThumbColor = TextMuted,
        uncheckedTrackColor = CyberDarkSurface
      )
    )
  }
}

@Composable
fun SettingsClickableRow(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(imageVector = icon, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.width(12.dp))
      Text(text = title, color = TextPrimary, fontSize = 14.sp)
    }
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
      contentDescription = null,
      tint = TextMuted,
      modifier = Modifier.size(16.dp)
    )
  }
}
