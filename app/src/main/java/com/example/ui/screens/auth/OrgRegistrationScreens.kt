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
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CyberCard
import com.example.ui.components.VoxShieldButton
import com.example.ui.components.VoxShieldTextField
import com.example.ui.components.VoxShieldTopAppBar
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
import com.example.ui.viewmodels.AuthViewModel

@Composable
fun StepProgressBar(currentStep: Int, totalSteps: Int = 4) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp, vertical = 8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "STEP $currentStep OF $totalSteps",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Text(
        text = when (currentStep) {
          1 -> "Organization Details"
          2 -> "Administrator Info"
          3 -> "Security Password"
          else -> "Terms & Security"
        },
        color = TextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    LinearProgressIndicator(
      progress = { currentStep.toFloat() / totalSteps.toFloat() },
      modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
        .clip(RoundedCornerShape(2.dp)),
      color = CyberCyan,
      trackColor = CyberDarkSurface
    )
  }
}

// ---------------- STEP 1: ORGANIZATION DETAILS ----------------
@Composable
fun OrganizationDetailsScreen(
  authViewModel: AuthViewModel,
  onNavigateToNext: () -> Unit,
  onNavigateBack: () -> Unit
) {
  val orgName by authViewModel.orgName.collectAsState()
  val orgId by authViewModel.orgId.collectAsState()
  val industry by authViewModel.industry.collectAsState()
  val orgSize by authViewModel.orgSize.collectAsState()
  val stepError by authViewModel.regStepError.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .imePadding()
      .testTag("org_details_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Organization Details",
      subtitle = "Setup your organization account",
      onBackClick = onNavigateBack
    )

    StepProgressBar(currentStep = 1)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
      Text(
        text = "Organization administrator account",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Text(
        text = "Enter your enterprise credentials for voice encryption and AI monitoring configuration.",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
      )

      VoxShieldTextField(
        value = orgName,
        onValueChange = { authViewModel.onOrgNameChange(it) },
        label = "Organization Name",
        placeholder = "e.g. ABC Bank",
        leadingIcon = Icons.Default.Apartment,
        testTag = "org_name_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = orgId,
        onValueChange = { authViewModel.onOrgIdChange(it) },
        label = "Organization ID",
        placeholder = "e.g. ABC001",
        leadingIcon = Icons.Default.Business,
        testTag = "org_id_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = industry,
        onValueChange = { authViewModel.onIndustryChange(it) },
        label = "Industry",
        placeholder = "e.g. Banking / Telecommunication / IT",
        leadingIcon = Icons.Default.Work,
        testTag = "org_industry_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = orgSize,
        onValueChange = { authViewModel.onOrgSizeChange(it) },
        label = "Organization Size",
        placeholder = "e.g. 500-1000 Employees",
        leadingIcon = Icons.Default.Group,
        testTag = "org_size_input"
      )

      if (stepError != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = stepError ?: "",
          color = DangerRed,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      VoxShieldButton(
        text = "Next",
        onClick = {
          if (authViewModel.validateStep1()) {
            onNavigateToNext()
          }
        },
        modifier = Modifier.fillMaxWidth(),
        testTag = "org_details_next_btn"
      )
    }
  }
}

// ---------------- STEP 2: ADMINISTRATOR DETAILS ----------------
@Composable
fun AdminDetailsScreen(
  authViewModel: AuthViewModel,
  onNavigateToNext: () -> Unit,
  onNavigateBack: () -> Unit
) {
  val adminFullName by authViewModel.adminFullName.collectAsState()
  val adminEmail by authViewModel.adminEmail.collectAsState()
  val adminPhone by authViewModel.adminPhone.collectAsState()
  val adminRole by authViewModel.adminRole.collectAsState()
  val stepError by authViewModel.regStepError.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .imePadding()
      .testTag("admin_details_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Administrator Details",
      subtitle = "Primary admin contact",
      onBackClick = onNavigateBack
    )

    StepProgressBar(currentStep = 2)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
      Text(
        text = "Administrator Details",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Text(
        text = "Define the primary administrative officer responsible for voiceprints and security policies.",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
      )

      VoxShieldTextField(
        value = adminFullName,
        onValueChange = { authViewModel.onAdminFullNameChange(it) },
        label = "Admin Full Name",
        placeholder = "e.g. Alison Pinto",
        leadingIcon = Icons.Default.Person,
        testTag = "admin_fullname_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = adminEmail,
        onValueChange = { authViewModel.onAdminEmailChange(it) },
        label = "Official Work Email",
        placeholder = "e.g. admin@abcbank.com",
        leadingIcon = Icons.Default.Email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        testTag = "admin_email_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = adminPhone,
        onValueChange = { authViewModel.onAdminPhoneChange(it) },
        label = "Phone Number",
        placeholder = "e.g. +91 98765 43210",
        leadingIcon = Icons.Default.Phone,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        testTag = "admin_phone_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = adminRole,
        onValueChange = { authViewModel.onAdminRoleChange(it) },
        label = "Admin Role",
        placeholder = "e.g. Organization Administrator",
        leadingIcon = Icons.Default.Shield,
        testTag = "admin_role_input"
      )

      if (stepError != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = stepError ?: "",
          color = DangerRed,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      VoxShieldButton(
        text = "Next",
        onClick = {
          if (authViewModel.validateStep2()) {
            onNavigateToNext()
          }
        },
        modifier = Modifier.fillMaxWidth(),
        testTag = "admin_details_next_btn"
      )
    }
  }
}

// ---------------- STEP 3: ORGANIZATION PASSWORD ----------------
@Composable
fun OrganizationPasswordScreen(
  authViewModel: AuthViewModel,
  onNavigateToNext: () -> Unit,
  onNavigateBack: () -> Unit
) {
  val password by authViewModel.regPassword.collectAsState()
  val confirmPassword by authViewModel.regConfirmPassword.collectAsState()
  val stepError by authViewModel.regStepError.collectAsState()

  var passwordVisible by remember { mutableStateOf(false) }
  var confirmPasswordVisible by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .imePadding()
      .testTag("org_password_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Create Login",
      subtitle = "Set administrator password",
      onBackClick = onNavigateBack
    )

    StepProgressBar(currentStep = 3)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
      Text(
        text = "Create Login",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Text(
        text = "Choose an administrative password to protect your voice intelligence dashboard.",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
      )

      VoxShieldTextField(
        value = password,
        onValueChange = { authViewModel.onRegPasswordChange(it) },
        label = "Password",
        placeholder = "Enter admin password",
        leadingIcon = Icons.Default.Lock,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
              contentDescription = null,
              tint = TextMuted
            )
          }
        },
        testTag = "reg_password_input"
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoxShieldTextField(
        value = confirmPassword,
        onValueChange = { authViewModel.onRegConfirmPasswordChange(it) },
        label = "Confirm Password",
        placeholder = "Re-enter password",
        leadingIcon = Icons.Default.Lock,
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
          IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
            Icon(
              imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
              contentDescription = null,
              tint = TextMuted
            )
          }
        },
        testTag = "reg_confirm_password_input"
      )

      if (stepError != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = stepError ?: "",
          color = DangerRed,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      VoxShieldButton(
        text = "Next",
        onClick = {
          if (authViewModel.validateStep3()) {
            onNavigateToNext()
          }
        },
        modifier = Modifier.fillMaxWidth(),
        testTag = "org_password_next_btn"
      )
    }
  }
}

// ---------------- STEP 4: TERMS & SECURITY ----------------
@Composable
fun TermsAndSecurityScreen(
  authViewModel: AuthViewModel,
  onNavigateToDashboard: () -> Unit,
  onNavigateBack: () -> Unit
) {
  val termsAccepted by authViewModel.termsAccepted.collectAsState()
  val stepError by authViewModel.regStepError.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("terms_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Terms & Security",
      subtitle = "Review and agree to policy",
      onBackClick = onNavigateBack
    )

    StepProgressBar(currentStep = 4)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
      Text(
        text = "Terms & Security",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Informative Security Terms Card
      CyberCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CyberDarkSurface,
        borderColor = CyberCardBorder
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "VOXSHIELD POLICY FRAMEWORK",
              color = CyberCyan,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          val termsItems = listOf(
            "VoxShield analyzes voice communication acoustics in real-time for enterprise security purposes.",
            "AI-generated or suspicious voice patterns and synthesized audio will be flagged automatically.",
            "The prototype application is intended for authorized organizational and hackathon demo use.",
            "Users must comply with organizational telephony data protection and privacy policies.",
            "AI detection results and confidence scores are advisory and supplementary to corporate safety protocols."
          )

          termsItems.forEachIndexed { index, item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .padding(top = 6.dp)
                  .size(5.dp)
                  .clip(CircleShape)
                  .background(CyberCyan)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = item,
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Checkbox agreement
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(CyberCardBg)
          .border(1.dp, if (termsAccepted) CyberCyan else CyberCardBorder, RoundedCornerShape(10.dp))
          .clickable { authViewModel.onTermsAcceptedChange(!termsAccepted) }
          .padding(12.dp)
          .testTag("terms_checkbox_row"),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Checkbox(
          checked = termsAccepted,
          onCheckedChange = { authViewModel.onTermsAcceptedChange(it) },
          colors = CheckboxDefaults.colors(
            checkedColor = CyberCyan,
            checkmarkColor = CyberBlack,
            uncheckedColor = TextMuted
          )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "I agree to the Terms & Security Policy",
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium
        )
      }

      if (stepError != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = stepError ?: "",
          color = DangerRed,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      VoxShieldButton(
        text = "CREATE ORGANIZATION",
        onClick = {
          authViewModel.createOrganization {
            onNavigateToDashboard()
          }
        },
        enabled = termsAccepted,
        modifier = Modifier.fillMaxWidth(),
        testTag = "create_org_submit_btn"
      )
    }
  }
}
