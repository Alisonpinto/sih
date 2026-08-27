package com.example.ui.screens.admin

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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBlue
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
import com.example.ui.viewmodels.AdminViewModel
import com.example.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminDashboardScreen(
  adminViewModel: AdminViewModel,
  authViewModel: AuthViewModel,
  onNavigateToEmployees: () -> Unit,
  onNavigateToVoiceProfiles: () -> Unit,
  onNavigateToTelephony: () -> Unit,
  onNavigateToSecurity: () -> Unit,
  onLogout: () -> Unit
) {
  val organization by adminViewModel.organization.collectAsState()
  val employees by adminViewModel.employees.collectAsState()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerContainerColor = CyberDarkSurface,
        drawerContentColor = TextPrimary
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
        ) {
          // Drawer Brand Header
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(CyberBlack)
                .border(1.dp, CyberCyan, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "VOXSHIELD",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
              )
              Text(
                text = "Enterprise Admin Portal",
                color = TextSecondary,
                fontSize = 11.sp
              )
            }
          }

          Text(
            text = "PANEL",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            modifier = Modifier.padding(vertical = 8.dp)
          )

          DrawerMenuItem(
            title = "Organization",
            icon = Icons.Default.Apartment,
            isSelected = true,
            onClick = { scope.launch { drawerState.close() } }
          )

          DrawerMenuItem(
            title = "Employees",
            icon = Icons.Default.People,
            isSelected = false,
            onClick = {
              scope.launch {
                drawerState.close()
                onNavigateToEmployees()
              }
            }
          )

          DrawerMenuItem(
            title = "Voice Profiles",
            icon = Icons.Default.Fingerprint,
            isSelected = false,
            onClick = {
              scope.launch {
                drawerState.close()
                onNavigateToVoiceProfiles()
              }
            }
          )

          DrawerMenuItem(
            title = "Telephony",
            icon = Icons.Default.PhoneInTalk,
            isSelected = false,
            onClick = {
              scope.launch {
                drawerState.close()
                onNavigateToTelephony()
              }
            }
          )

          DrawerMenuItem(
            title = "Security",
            icon = Icons.Default.Security,
            isSelected = false,
            onClick = {
              scope.launch {
                drawerState.close()
                onNavigateToSecurity()
              }
            }
          )

          Spacer(modifier = Modifier.weight(1f))

          // Logout Item
          DrawerMenuItem(
            title = "Logout",
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            isSelected = false,
            isDanger = true,
            onClick = {
              scope.launch {
                drawerState.close()
                authViewModel.logout(onLogout)
              }
            }
          )
        }
      }
    }
  ) {
    Scaffold(
      containerColor = CyberBlack,
      topBar = {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(CyberBlack)
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = { scope.launch { drawerState.open() } },
              modifier = Modifier.testTag("admin_drawer_menu_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open Drawer Menu",
                tint = TextPrimary
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "VoxShield Admin",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = organization.name,
                color = CyberCyan,
                fontSize = 12.sp
              )
            }
          }

          IconButton(
            onClick = { authViewModel.logout(onLogout) },
            modifier = Modifier.testTag("admin_logout_top_btn")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ExitToApp,
              contentDescription = "Logout",
              tint = TextSecondary
            )
          }
        }
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 12.dp)
          .testTag("admin_dashboard_screen")
      ) {
        // Welcome Header
        Text(
          text = "Welcome to VoxShield, ${organization.name}",
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = "Centralized Voice Authentication & AI Deepfake Shield",
          fontSize = 13.sp,
          color = TextSecondary,
          modifier = Modifier.padding(top = 2.dp, bottom = 18.dp)
        )

        // ORGANIZATION SETUP CARD
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
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberCardBg)
                    .border(1.dp, CyberCyan, RoundedCornerShape(8.dp)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Apartment,
                    contentDescription = null,
                    tint = CyberCyan,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "ORGANIZATION SETUP",
                  color = CyberCyan,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 0.8.sp
                )
              }
              StatusBadge(text = organization.status, verdict = com.example.data.models.DetectionVerdict.GENUINE)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OrgDetailRow(label = "Organization Name", value = organization.name)
            OrgDetailRow(label = "Organization ID", value = organization.id)
            OrgDetailRow(label = "Admin", value = organization.adminName)
            OrgDetailRow(label = "Industry", value = organization.industry)
            OrgDetailRow(label = "Scale", value = organization.size)
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Telephony & Security Pulse
        Text(
          text = "SYSTEM PULSE",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          QuickMetricCard(
            title = "Protected Personnel",
            value = "${employees.size}",
            subtitle = "Active monitoring",
            icon = Icons.Default.People,
            accentColor = CyberCyan,
            modifier = Modifier.weight(1f),
            onClick = onNavigateToEmployees
          )
          QuickMetricCard(
            title = "Voice Profiles",
            value = "${employees.count { it.voiceProfileStatus == com.example.data.models.VoiceProfileStatus.ENROLLED }}",
            subtitle = "Enrolled prints",
            icon = Icons.Default.Fingerprint,
            accentColor = SafeGreen,
            modifier = Modifier.weight(1f),
            onClick = onNavigateToVoiceProfiles
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // NEXT STEP SECTION
        Text(
          text = "NEXT STEP",
          color = TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Action Card 1: Add Employee
        CyberCard(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_action_add_employee"),
          onClick = onNavigateToEmployees,
          borderColor = CyberCardBorder,
          backgroundColor = CyberCardBg
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(CyberDarkSurface)
                  .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.PersonAdd,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = "Add Employee",
                  color = TextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "Enroll new staff into VoxShield directory",
                  color = TextSecondary,
                  fontSize = 12.sp
                )
              }
            }
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action Card 2: Register Voice
        CyberCard(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_action_register_voice"),
          onClick = onNavigateToVoiceProfiles,
          borderColor = CyberCardBorder,
          backgroundColor = CyberCardBg
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(CyberDarkSurface)
                  .border(1.dp, SafeGreen, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.GraphicEq,
                  contentDescription = null,
                  tint = SafeGreen,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = "Register Voice",
                  color = TextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "Record & verify biometrics voice signature",
                  color = TextSecondary,
                  fontSize = 12.sp
                )
              }
            }
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = SafeGreen,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun DrawerMenuItem(
  title: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  isDanger: Boolean = false
) {
  NavigationDrawerItem(
    label = {
      Text(
        text = title,
        color = if (isDanger) DangerRed else if (isSelected) CyberCyan else TextPrimary,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        fontSize = 14.sp
      )
    },
    icon = {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isDanger) DangerRed else if (isSelected) CyberCyan else TextSecondary,
        modifier = Modifier.size(22.dp)
      )
    },
    selected = isSelected,
    onClick = onClick,
    shape = RoundedCornerShape(10.dp),
    colors = NavigationDrawerItemDefaults.colors(
      selectedContainerColor = CyberCardBg,
      unselectedContainerColor = androidx.compose.ui.graphics.Color.Transparent
    ),
    modifier = Modifier.padding(vertical = 4.dp)
  )
}

@Composable
fun OrgDetailRow(label: String, value: String) {
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

@Composable
fun QuickMetricCard(
  title: String,
  value: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  CyberCard(
    modifier = modifier,
    onClick = onClick,
    backgroundColor = CyberCardBg,
    borderColor = CyberCardBorder
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
          tint = accentColor,
          modifier = Modifier.size(16.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        color = TextPrimary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = subtitle,
        color = accentColor,
        fontSize = 11.sp
      )
    }
  }
}
