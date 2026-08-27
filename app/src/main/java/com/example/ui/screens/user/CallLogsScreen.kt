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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
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
import com.example.data.models.CallRecord
import com.example.data.models.DetectionVerdict
import com.example.ui.components.CallCard
import com.example.ui.components.VoxShieldTextField
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.CallViewModel

@Composable
fun CallLogsScreen(
  callViewModel: CallViewModel,
  onNavigateToCallDetails: (CallRecord) -> Unit
) {
  val callLogs by callViewModel.callLogs.collectAsState()
  val searchQuery by callViewModel.searchQuery.collectAsState()
  val selectedFilter by callViewModel.selectedFilter.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .padding(horizontal = 20.dp)
      .testTag("call_logs_screen")
  ) {
    Spacer(modifier = Modifier.height(10.dp))

    // Search bar
    VoxShieldTextField(
      value = searchQuery,
      onValueChange = { callViewModel.onSearchQueryChange(it) },
      label = "",
      placeholder = "Search call history or caller...",
      leadingIcon = Icons.Default.Search,
      testTag = "call_search_input"
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Filter Chips
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      FilterPill(
        title = "All Calls",
        isSelected = selectedFilter == null,
        onClick = { callViewModel.onFilterSelect(null) }
      )
      FilterPill(
        title = "AI Detected",
        isSelected = selectedFilter == DetectionVerdict.AI_DETECTED,
        accentColor = DangerRed,
        onClick = { callViewModel.onFilterSelect(DetectionVerdict.AI_DETECTED) }
      )
      FilterPill(
        title = "Genuine",
        isSelected = selectedFilter == DetectionVerdict.GENUINE,
        accentColor = SafeGreen,
        onClick = { callViewModel.onFilterSelect(DetectionVerdict.GENUINE) }
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (callLogs.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.Call,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(48.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "No calls match current criteria",
            color = TextSecondary,
            fontSize = 14.sp
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(callLogs, key = { it.id }) { call ->
          CallCard(
            call = call,
            onClick = { onNavigateToCallDetails(call) }
          )
        }

        item {
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }
  }
}

@Composable
fun FilterPill(
  title: String,
  isSelected: Boolean,
  accentColor: androidx.compose.ui.graphics.Color = CyberCyan,
  onClick: () -> Unit
) {
  Surface(
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .border(
        1.dp,
        if (isSelected) accentColor else CyberCardBorder,
        RoundedCornerShape(20.dp)
      )
      .clickable { onClick() },
    color = if (isSelected) CyberCardBg else CyberDarkSurface
  ) {
    Text(
      text = title,
      color = if (isSelected) accentColor else TextSecondary,
      fontSize = 12.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
    )
  }
}
