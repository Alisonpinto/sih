package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CallRecord
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.AuthViewModel
import com.example.ui.viewmodels.CallViewModel

enum class UserNavTab(val title: String, val icon: ImageVector) {
  HOME("Home", Icons.Default.Home),
  CALLS("Calls", Icons.Default.Call),
  AI_RESULTS("AI Results", Icons.Default.GraphicEq),
  PROFILE("Profile", Icons.Default.Person)
}

@Composable
fun UserMainContainerScreen(
  authViewModel: AuthViewModel,
  callViewModel: CallViewModel,
  onNavigateToActiveCall: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onLogout: () -> Unit
) {
  var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

  Scaffold(
    containerColor = CyberBlack,
    topBar = {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(CyberBlack)
          .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // App Shield Logo + Name
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(CyberDarkSurface)
              .border(1.dp, CyberCyan, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
          }

          Text(
            text = "VoxShield",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp)
          )
        }

        // Settings Icon
        IconButton(
          onClick = onNavigateToSettings,
          modifier = Modifier.testTag("user_settings_icon_btn")
        ) {
          Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = TextSecondary
          )
        }
      }
    },
    bottomBar = {
      NavigationBar(
        containerColor = CyberDarkSurface,
        modifier = Modifier
          .border(androidx.compose.foundation.BorderStroke(0.5.dp, CyberCardBorder))
          .testTag("user_bottom_nav_bar")
      ) {
        UserNavTab.entries.forEachIndexed { index, tab ->
          val isSelected = selectedTabIndex == index
          NavigationBarItem(
            selected = isSelected,
            onClick = { selectedTabIndex = index },
            icon = {
              Icon(
                imageVector = tab.icon,
                contentDescription = tab.title,
                modifier = Modifier.size(24.dp)
              )
            },
            label = {
              Text(
                text = tab.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = CyberCyan,
              selectedTextColor = CyberCyan,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted,
              indicatorColor = CyberCardBg
            ),
            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (UserNavTab.entries[selectedTabIndex]) {
        UserNavTab.HOME -> {
          UserHomeScreen(
            callViewModel = callViewModel,
            onNavigateToActiveCall = onNavigateToActiveCall,
            onNavigateToCallDetails = { call ->
              callViewModel.selectCall(call)
              selectedTabIndex = UserNavTab.AI_RESULTS.ordinal
            },
            onViewAllCalls = {
              selectedTabIndex = UserNavTab.CALLS.ordinal
            }
          )
        }
        UserNavTab.CALLS -> {
          CallLogsScreen(
            callViewModel = callViewModel,
            onNavigateToCallDetails = { call ->
              callViewModel.selectCall(call)
              selectedTabIndex = UserNavTab.AI_RESULTS.ordinal
            }
          )
        }
        UserNavTab.AI_RESULTS -> {
          AIResultsScreen(
            callViewModel = callViewModel,
            onNavigateBack = {
              selectedTabIndex = UserNavTab.HOME.ordinal
            }
          )
        }
        UserNavTab.PROFILE -> {
          ProfileScreen(
            authViewModel = authViewModel,
            onLogout = onLogout
          )
        }
      }
    }
  }
}
