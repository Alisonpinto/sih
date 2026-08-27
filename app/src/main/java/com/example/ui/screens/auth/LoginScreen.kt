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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.UserRole
import com.example.ui.components.CyberCard
import com.example.ui.components.VoxShieldButton
import com.example.ui.components.VoxShieldTextField
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

@Composable
fun LoginScreen(
  authViewModel: AuthViewModel,
  onNavigateToAdminDashboard: () -> Unit,
  onNavigateToUserHome: () -> Unit,
  onNavigateToRegister: () -> Unit
) {
  val userId by authViewModel.userId.collectAsState()
  val email by authViewModel.email.collectAsState()
  val password by authViewModel.password.collectAsState()
  val loginError by authViewModel.loginError.collectAsState()
  val isLoading by authViewModel.isLoading.collectAsState()

  var passwordVisible by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .imePadding()
      .testTag("login_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Header Icon
      Box(
        modifier = Modifier
          .size(64.dp)
          .clip(CircleShape)
          .background(CyberDarkSurface)
          .border(1.5.dp, CyberCyan, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Shield,
          contentDescription = "VoxShield Logo",
          tint = CyberCyan,
          modifier = Modifier.size(32.dp)
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Welcome to VoxShield",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Text(
        text = "Enterprise AI Voice Protection",
        fontSize = 14.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp)
      )

      Spacer(modifier = Modifier.height(28.dp))

      // Demo quick fill chips for judges
      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CyberDarkSurface,
        borderColor = CyberCardBorder
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "⚡ QUICK DEMO CREDENTIALS (SIH JUDGES)",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .clickable { authViewModel.fillAdminDemoCredentials() }
                .testTag("fill_admin_demo"),
              color = CyberCardBg,
              border = androidx.compose.foundation.BorderStroke(1.dp, CyberCardBorder)
            ) {
              Column(modifier = Modifier.padding(8.dp)) {
                Text(
                  text = "Admin Demo",
                  color = TextPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "admin001 • 123456",
                  color = TextMuted,
                  fontSize = 10.sp
                )
              }
            }

            Surface(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .clickable { authViewModel.fillEmployeeDemoCredentials() }
                .testTag("fill_employee_demo"),
              color = CyberCardBg,
              border = androidx.compose.foundation.BorderStroke(1.dp, CyberCardBorder)
            ) {
              Column(modifier = Modifier.padding(8.dp)) {
                Text(
                  text = "Employee Demo",
                  color = TextPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "EMP001 • 123456",
                  color = TextMuted,
                  fontSize = 10.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Input Form
      VoxShieldTextField(
        value = userId,
        onValueChange = { authViewModel.onUserIdChange(it) },
        label = "USER ID",
        placeholder = "e.g. admin001 or EMP001",
        leadingIcon = Icons.Default.Person,
        testTag = "login_user_id_field"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = email,
        onValueChange = { authViewModel.onEmailChange(it) },
        label = "Email",
        placeholder = "e.g. admin@abc.com",
        leadingIcon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        testTag = "login_email_field"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = password,
        onValueChange = { authViewModel.onPasswordChange(it) },
        label = "Password",
        placeholder = "Enter your password",
        leadingIcon = Icons.Default.Lock,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
              contentDescription = "Toggle password visibility",
              tint = TextMuted
            )
          }
        },
        testTag = "login_password_field"
      )

      if (loginError != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = loginError ?: "",
          color = DangerRed,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.testTag("login_error_text")
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      VoxShieldButton(
        text = "Login",
        onClick = {
          authViewModel.login { role ->
            when (role) {
              UserRole.ADMIN -> onNavigateToAdminDashboard()
              UserRole.EMPLOYEE -> onNavigateToUserHome()
            }
          }
        },
        modifier = Modifier.fillMaxWidth(),
        isLoading = isLoading,
        testTag = "login_submit_button"
      )

      Spacer(modifier = Modifier.height(24.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Don't have an organization account? ",
          color = TextSecondary,
          fontSize = 13.sp
        )
        Text(
          text = "Register",
          color = CyberCyan,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier
            .clickable { onNavigateToRegister() }
            .testTag("register_navigation_link")
            .padding(4.dp)
        )
      }
    }
  }
}
