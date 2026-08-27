package com.example.ui.screens.auth

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CyberCard
import com.example.ui.components.VoxShieldTopAppBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCardBorderLight
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun RegisterChoiceScreen(
  onNavigateToOrgDetails: () -> Unit,
  onNavigateToEmployeeLogin: () -> Unit,
  onNavigateBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("register_choice_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Create an Account",
      subtitle = "Select your registration role",
      onBackClick = onNavigateBack
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "How would you like to use VoxShield?",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(bottom = 24.dp)
      )

      // Option 1: Organization Administrator
      CyberCard(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("choice_org_admin"),
        onClick = onNavigateToOrgDetails,
        borderColor = CyberCardBorderLight,
        backgroundColor = CyberCardBg
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(CyberDarkSurface)
              .border(1.dp, CyberCyan, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Apartment,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Organization Administrator",
              color = TextPrimary,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Create and manage your organization's VoxShield environment.",
              color = TextSecondary,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = CyberCyan,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Option 2: Employee / User
      CyberCard(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("choice_employee_user"),
        onClick = onNavigateToEmployeeLogin,
        borderColor = CyberCardBorder,
        backgroundColor = CyberDarkSurface
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(CyberCardBg)
              .border(1.dp, CyberCardBorder, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Badge,
              contentDescription = null,
              tint = TextSecondary,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.width(16.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Employee / User",
              color = TextPrimary,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Login using credentials provided by your organization.",
              color = TextSecondary,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
