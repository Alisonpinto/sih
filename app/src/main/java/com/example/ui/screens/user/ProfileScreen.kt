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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.data.repository.MockRepository
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.components.VoxShieldButton
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCardBorderLight
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  authViewModel: AuthViewModel,
  onLogout: () -> Unit
) {
  val currentUser by MockRepository.currentUser.collectAsState()
  val organization by MockRepository.organization.collectAsState()

  var infoDialogTitle by remember { mutableStateOf<String?>(null) }
  var infoDialogMessage by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 20.dp, vertical = 16.dp)
      .testTag("user_profile_screen"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(10.dp))

    // Profile Avatar
    Box(
      modifier = Modifier
        .size(80.dp)
        .clip(CircleShape)
        .background(CyberDarkSurface)
        .border(2.dp, CyberCyan, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Person,
        contentDescription = "Profile Picture",
        tint = CyberCyan,
        modifier = Modifier.size(44.dp)
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = currentUser?.name ?: "Alison Pinto",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary
    )

    Text(
      text = currentUser?.email ?: "employee@abc.com",
      fontSize = 13.sp,
      color = TextSecondary,
      modifier = Modifier.padding(top = 2.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    // User Profile Details Card
    CyberCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = CyberDarkSurface,
      borderColor = CyberCardBorder
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        ProfileInfoRow(label = "Employee ID", value = currentUser?.employeeId ?: "EMP001")
        ProfileInfoRow(label = "Department", value = currentUser?.department ?: "Finance")
        ProfileInfoRow(label = "Role", value = currentUser?.role?.name ?: "Employee")
        ProfileInfoRow(label = "Organization", value = organization.name)
        ProfileInfoRow(label = "Voice Profile", value = "Verified", isBadge = true, isSafe = true)
        ProfileInfoRow(label = "Security Status", value = "Protected", isBadge = true, isSafe = true)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Quick Actions
    ProfileActionButton(
      title = "Edit Profile",
      icon = Icons.Default.Edit,
      onClick = {
        infoDialogTitle = "Edit Profile"
        infoDialogMessage = "Employee profile edits are managed in sync with your organization's enterprise active directory."
      }
    )

    Spacer(modifier = Modifier.height(10.dp))

    ProfileActionButton(
      title = "Voice Profile",
      icon = Icons.Default.Fingerprint,
      onClick = {
        infoDialogTitle = "Voice Profile Telemetry"
        infoDialogMessage = "Your biometrics voiceprint signature is fully enrolled and actively verifying incoming communication."
      }
    )

    Spacer(modifier = Modifier.height(10.dp))

    ProfileActionButton(
      title = "Security Information",
      icon = Icons.Default.Shield,
      onClick = {
        infoDialogTitle = "Security Protocols"
        infoDialogMessage = "VoxShield Deepfake AI Protection protocol v2.4 is active with real-time acoustic spectral frequency verification."
      }
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Logout Button
    VoxShieldButton(
      text = "Logout",
      onClick = { authViewModel.logout(onLogout) },
      isSecondary = true,
      leadingIcon = Icons.AutoMirrored.Filled.ExitToApp,
      modifier = Modifier.fillMaxWidth(),
      testTag = "user_logout_btn"
    )

    Spacer(modifier = Modifier.height(20.dp))
  }

  // Action Info Dialog
  if (infoDialogTitle != null) {
    BasicAlertDialog(onDismissRequest = { infoDialogTitle = null }) {
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
            text = infoDialogTitle ?: "",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = infoDialogMessage ?: "",
            color = TextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(20.dp))
          VoxShieldButton(
            text = "Got It",
            onClick = { infoDialogTitle = null },
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
  }
}

@Composable
fun ProfileInfoRow(
  label: String,
  value: String,
  isBadge: Boolean = false,
  isSafe: Boolean = false
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = label, color = TextSecondary, fontSize = 13.sp)
    if (isBadge) {
      StatusBadge(
        text = value,
        verdict = if (isSafe) DetectionVerdict.GENUINE else DetectionVerdict.AI_DETECTED
      )
    } else {
      Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
  }
}

@Composable
fun ProfileActionButton(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit
) {
  CyberCard(
    modifier = Modifier.fillMaxWidth(),
    onClick = onClick,
    backgroundColor = CyberCardBg,
    borderColor = CyberCardBorder
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(CyberDarkSurface)
            .border(1.dp, CyberCardBorder, RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CyberCyan,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(
          text = title,
          color = TextPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium
        )
      }
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = TextMuted,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}
