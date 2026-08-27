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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneCallback
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Router
import androidx.compose.material3.Icon
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.AdminViewModel
import com.example.ui.viewmodels.CallViewModel

@Composable
fun TelephonyScreen(
  adminViewModel: AdminViewModel,
  callViewModel: CallViewModel,
  onNavigateToActiveCall: () -> Unit,
  onNavigateBack: () -> Unit
) {
  val telephonyConfig by adminViewModel.telephonyConfig.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("telephony_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Telephony Management",
      subtitle = "PBX & VoIP Trunk Line Integration",
      onBackClick = onNavigateBack
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      // TELEPHONY STATUS OVERVIEW CARD
      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = CyberCardBorderLight,
        backgroundColor = CyberDarkSurface
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(CyberCardBg)
                  .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Router,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "CONNECTED TELEPHONY TRUNK",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 0.8.sp
                )
                Text(
                  text = telephonyConfig.deviceName,
                  color = TextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
            StatusBadge(text = telephonyConfig.status, verdict = com.example.data.models.DetectionVerdict.GENUINE)
          }

          Spacer(modifier = Modifier.height(16.dp))

          TelephonyDetailRow(label = "Connected Device", value = telephonyConfig.deviceName)
          TelephonyDetailRow(label = "Status", value = telephonyConfig.status)
          TelephonyDetailRow(label = "Extension", value = telephonyConfig.extension)
          TelephonyDetailRow(label = "Number", value = telephonyConfig.phoneNumber)
          TelephonyDetailRow(label = "Connection Status", value = telephonyConfig.connectionStatus)
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Informative Telephony Note
      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = CyberCardBorder,
        backgroundColor = CyberCardBg
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = CyberCyan,
            modifier = Modifier
              .size(22.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Incoming calls from the organization's connected telephony system will appear in VoxShield in real time, automatically subjected to neural voice pattern analysis.",
            color = TextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Simulate Incoming Call Action
      Text(
        text = "LIVE TEST BED",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      VoxShieldButton(
        text = "Simulate Incoming Call",
        onClick = {
          callViewModel.startLiveCall(
            callerName = "Unknown Number",
            phoneNumber = "+91 88921 55670",
            forceAi = true
          )
          onNavigateToActiveCall()
        },
        leadingIcon = Icons.Default.PhoneCallback,
        modifier = Modifier.fillMaxWidth(),
        testTag = "simulate_incoming_call_btn"
      )
    }
  }
}

@Composable
fun TelephonyDetailRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 5.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, color = TextSecondary, fontSize = 13.sp)
    Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
  }
}
