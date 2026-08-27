package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CallRecord
import com.example.ui.components.CallCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.SecurityStatusCard
import com.example.ui.components.VoxShieldButton
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.CallViewModel

@Composable
fun UserHomeScreen(
  callViewModel: CallViewModel,
  onNavigateToActiveCall: () -> Unit,
  onNavigateToCallDetails: (CallRecord) -> Unit,
  onViewAllCalls: () -> Unit
) {
  val callLogs by callViewModel.callLogs.collectAsState()
  val recentCalls = callLogs.take(4)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .padding(horizontal = 20.dp)
      .testTag("user_home_screen"),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(10.dp))

      // User Greeting Header
      Column {
        Text(
          text = "Good evening, Alison",
          color = TextPrimary,
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Your voice security is active",
          color = CyberCyan,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.padding(top = 2.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Voice Protection Security Status Card
      SecurityStatusCard(isActive = true)

      Spacer(modifier = Modifier.height(16.dp))

      // Start Demo Call Banner Button
      VoxShieldButton(
        text = "Start Demo Call",
        onClick = {
          callViewModel.startLiveCall(
            callerName = "Unknown Number",
            phoneNumber = "+91 88921 55670",
            forceAi = true
          )
          onNavigateToActiveCall()
        },
        leadingIcon = Icons.Default.PhoneInTalk,
        modifier = Modifier.fillMaxWidth(),
        testTag = "home_start_demo_call_btn"
      )

      Spacer(modifier = Modifier.height(12.dp))

      SectionHeader(
        title = "Recent Calls",
        actionText = "View all",
        onActionClick = onViewAllCalls
      )
    }

    items(recentCalls, key = { it.id }) { call ->
      CallCard(
        call = call,
        onClick = { onNavigateToCallDetails(call) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
