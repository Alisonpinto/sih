package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CallRecord
import com.example.data.models.CallType
import com.example.data.models.DetectionVerdict
import com.example.data.models.Employee
import com.example.data.models.RiskLevel
import com.example.data.models.VoiceProfileStatus
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBlue
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCardBorderLight
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.CyberElectricBlue
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedBorder
import com.example.ui.theme.DangerRedSurface
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeGreenBorder
import com.example.ui.theme.SafeGreenSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningYellow
import com.example.ui.theme.WarningYellowBorder
import com.example.ui.theme.WarningYellowSurface

@Composable
fun VoxShieldButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  isLoading: Boolean = false,
  isSecondary: Boolean = false,
  isDanger: Boolean = false,
  leadingIcon: ImageVector? = null,
  testTag: String = "vox_shield_button"
) {
  if (isSecondary) {
    OutlinedButton(
      onClick = onClick,
      modifier = modifier
        .height(52.dp)
        .testTag(testTag),
      enabled = enabled && !isLoading,
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = if (isDanger) DangerRed else CyberCyan,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = TextMuted
      ),
      border = ButtonDefaults.outlinedButtonBorder.copy(
        brush = Brush.horizontalGradient(
          listOf(
            if (isDanger) DangerRedBorder else CyberCardBorderLight,
            if (isDanger) DangerRed else CyberBlue
          )
        )
      )
    ) {
      if (isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.size(20.dp),
          color = if (isDanger) DangerRed else CyberCyan,
          strokeWidth = 2.dp
        )
      } else {
        if (leadingIcon != null) {
          Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
          text = text,
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  } else {
    Button(
      onClick = onClick,
      modifier = modifier
        .height(52.dp)
        .testTag(testTag),
      enabled = enabled && !isLoading,
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = if (isDanger) DangerRed else CyberCyan,
        contentColor = if (isDanger) TextPrimary else CyberBlack,
        disabledContainerColor = CyberCardBorder,
        disabledContentColor = TextMuted
      )
    ) {
      if (isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.size(20.dp),
          color = if (isDanger) TextPrimary else CyberBlack,
          strokeWidth = 2.dp
        )
      } else {
        if (leadingIcon != null) {
          Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
          text = text,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }
    }
  }
}

@Composable
fun VoxShieldTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  modifier: Modifier = Modifier,
  placeholder: String = "",
  leadingIcon: ImageVector? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  isError: Boolean = false,
  errorMessage: String? = null,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = true,
  testTag: String = "vox_text_field"
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = label,
      color = TextSecondary,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier.padding(bottom = 6.dp)
    )
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .testTag(testTag),
      placeholder = {
        if (placeholder.isNotEmpty()) {
          Text(text = placeholder, color = TextMuted, fontSize = 14.sp)
        }
      },
      leadingIcon = leadingIcon?.let {
        {
          Icon(
            imageVector = it,
            contentDescription = null,
            tint = if (isError) DangerRed else CyberCyan,
            modifier = Modifier.size(20.dp)
          )
        }
      },
      trailingIcon = trailingIcon,
      isError = isError,
      visualTransformation = visualTransformation,
      keyboardOptions = keyboardOptions,
      keyboardActions = keyboardActions,
      singleLine = singleLine,
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = CyberDarkSurface,
        unfocusedContainerColor = CyberDarkSurface,
        errorContainerColor = DangerRedSurface,
        focusedBorderColor = CyberCyan,
        unfocusedBorderColor = CyberCardBorder,
        errorBorderColor = DangerRed,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        errorTextColor = TextPrimary,
        cursorColor = CyberCyan
      )
    )
    if (isError && !errorMessage.isNullOrEmpty()) {
      Text(
        text = errorMessage,
        color = DangerRed,
        fontSize = 12.sp,
        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
      )
    }
  }
}

@Composable
fun CyberCard(
  modifier: Modifier = Modifier,
  borderColor: Color = CyberCardBorder,
  backgroundColor: Color = CyberCardBg,
  cornerRadius: Dp = 16.dp,
  onClick: (() -> Unit)? = null,
  content: @Composable () -> Unit
) {
  val cardModifier = if (onClick != null) {
    modifier
      .clip(RoundedCornerShape(cornerRadius))
      .border(1.dp, borderColor, RoundedCornerShape(cornerRadius))
      .background(backgroundColor)
      .clickable { onClick() }
  } else {
    modifier
      .clip(RoundedCornerShape(cornerRadius))
      .border(1.dp, borderColor, RoundedCornerShape(cornerRadius))
      .background(backgroundColor)
  }

  Box(modifier = cardModifier.padding(16.dp)) {
    content()
  }
}

@Composable
fun StatusBadge(
  text: String? = null,
  verdict: DetectionVerdict? = null,
  riskLevel: RiskLevel? = null,
  modifier: Modifier = Modifier
) {
  val displayText = text ?: when (verdict) {
    DetectionVerdict.AI_DETECTED -> "AI Detected"
    DetectionVerdict.GENUINE -> "Genuine"
    DetectionVerdict.UNCERTAIN -> "Uncertain"
    null -> if (riskLevel != null) "${riskLevel.name} Risk" else "Active"
  }

  val (bgColor, textColor, borderColor) = when {
    verdict == DetectionVerdict.AI_DETECTED || riskLevel == RiskLevel.HIGH -> {
      Triple(DangerRedSurface, DangerRed, DangerRedBorder)
    }
    verdict == DetectionVerdict.GENUINE || riskLevel == RiskLevel.LOW -> {
      Triple(SafeGreenSurface, SafeGreen, SafeGreenBorder)
    }
    else -> {
      Triple(WarningYellowSurface, WarningYellow, WarningYellowBorder)
    }
  }

  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    color = bgColor,
    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(textColor)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = displayText,
        color = textColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.4.sp
      )
    }
  }
}

@Composable
fun SecurityStatusCard(
  modifier: Modifier = Modifier,
  isActive: Boolean = true,
  organizationName: String = "ABC Bank"
) {
  CyberCard(
    modifier = modifier.fillMaxWidth(),
    borderColor = if (isActive) CyberCardBorderLight else DangerRedBorder,
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
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) SafeGreenSurface else DangerRedSurface)
            .border(1.dp, if (isActive) SafeGreenBorder else DangerRedBorder, RoundedCornerShape(12.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = null,
            tint = if (isActive) SafeGreen else DangerRed,
            modifier = Modifier.size(26.dp)
          )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "VOICE PROTECTION",
              color = TextSecondary,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            StatusBadge(text = if (isActive) "ACTIVE" else "INACTIVE", verdict = if (isActive) DetectionVerdict.GENUINE else DetectionVerdict.AI_DETECTED)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "VoxShield is monitoring your calls.",
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
fun CallCard(
  call: CallRecord,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  CyberCard(
    modifier = modifier.fillMaxWidth(),
    onClick = onClick,
    borderColor = if (call.verdict == DetectionVerdict.AI_DETECTED) DangerRedBorder else CyberCardBorder
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(if (call.verdict == DetectionVerdict.AI_DETECTED) DangerRedSurface else CyberDarkSurface)
            .border(1.dp, if (call.verdict == DetectionVerdict.AI_DETECTED) DangerRedBorder else CyberCardBorder, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (call.callType == CallType.INCOMING) Icons.AutoMirrored.Filled.CallReceived else Icons.AutoMirrored.Filled.CallMade,
            contentDescription = null,
            tint = if (call.verdict == DetectionVerdict.AI_DETECTED) DangerRed else CyberCyan,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = call.callerName,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "${if (call.callType == CallType.INCOMING) "Incoming" else "Outgoing"} • ${call.time}",
              color = TextSecondary,
              fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "(${call.duration})",
              color = TextMuted,
              fontSize = 11.sp
            )
          }
        }
      }

      Column(horizontalAlignment = Alignment.End) {
        StatusBadge(
          text = if (call.verdict == DetectionVerdict.AI_DETECTED) "AI Detected" else if (call.verdict == DetectionVerdict.GENUINE) "Genuine" else "Uncertain",
          verdict = call.verdict
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = if (call.riskLevel == RiskLevel.HIGH) "High Risk" else if (call.riskLevel == RiskLevel.MEDIUM) "Med Risk" else "Low Risk",
          color = if (call.riskLevel == RiskLevel.HIGH) DangerRed else if (call.riskLevel == RiskLevel.MEDIUM) WarningYellow else SafeGreen,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}

@Composable
fun EmployeeCard(
  employee: Employee,
  onRegisterVoiceClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  CyberCard(
    modifier = modifier.fillMaxWidth(),
    borderColor = CyberCardBorder
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(CyberDarkSurface)
              .border(1.dp, CyberCardBorder, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = employee.name.take(2).uppercase(),
              color = CyberCyan,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = employee.name,
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "${employee.employeeId} • ${employee.department} (${employee.role})",
              color = TextSecondary,
              fontSize = 12.sp
            )
          }
        }
        StatusBadge(
          text = employee.status.name,
          verdict = if (employee.status.name == "ACTIVE") DetectionVerdict.GENUINE else null,
          riskLevel = if (employee.status.name == "INACTIVE") RiskLevel.MEDIUM else null
        )
      }

      Spacer(modifier = Modifier.height(12.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Fingerprint,
            contentDescription = null,
            tint = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> SafeGreen
              VoiceProfileStatus.PENDING -> WarningYellow
              VoiceProfileStatus.NOT_ENROLLED -> TextMuted
            },
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> "Voice Profile: Enrolled"
              VoiceProfileStatus.PENDING -> "Voice Profile: Pending"
              VoiceProfileStatus.NOT_ENROLLED -> "Voice Profile: Not Enrolled"
            },
            color = when (employee.voiceProfileStatus) {
              VoiceProfileStatus.ENROLLED -> SafeGreen
              VoiceProfileStatus.PENDING -> WarningYellow
              VoiceProfileStatus.NOT_ENROLLED -> TextMuted
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }

        if (employee.voiceProfileStatus != VoiceProfileStatus.ENROLLED && onRegisterVoiceClick != null) {
          Text(
            text = "Enroll Voice",
            color = CyberCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .clickable { onRegisterVoiceClick() }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SectionHeader(
  title: String,
  modifier: Modifier = Modifier,
  actionText: String? = null,
  onActionClick: (() -> Unit)? = null
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = title,
      color = TextPrimary,
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold
    )
    if (!actionText.isNullOrEmpty() && onActionClick != null) {
      Text(
        text = actionText,
        color = CyberCyan,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.clickable { onActionClick() }
      )
    }
  }
}

@Composable
fun VoxShieldTopAppBar(
  title: String,
  modifier: Modifier = Modifier,
  subtitle: String? = null,
  onBackClick: (() -> Unit)? = null,
  trailingContent: @Composable (() -> Unit)? = null
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(CyberBlack)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
      if (onBackClick != null) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.size(40.dp)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TextPrimary
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
      }
      Column {
        Text(
          text = title,
          color = TextPrimary,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )
        if (!subtitle.isNullOrEmpty()) {
          Text(
            text = subtitle,
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      }
    }
    if (trailingContent != null) {
      trailingContent()
    }
  }
}

@Composable
fun AudioWaveVisualizer(
  modifier: Modifier = Modifier,
  isScanning: Boolean = true,
  barCount: Int = 18,
  activeColor: Color = CyberCyan
) {
  val infiniteTransition = rememberInfiniteTransition(label = "wave_anim")
  val animPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 6.28f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "wave_phase"
  )

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(48.dp),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 0 until barCount) {
      val heightFactor = if (isScanning) {
        val sinVal = kotlin.math.sin(animPhase + (i * 0.4)).toFloat()
        (0.25f + 0.75f * ((sinVal + 1f) / 2f)).coerceIn(0.15f, 1.0f)
      } else {
        0.3f
      }

      Box(
        modifier = Modifier
          .width(3.dp)
          .height((48 * heightFactor).dp)
          .clip(RoundedCornerShape(2.dp))
          .background(activeColor)
      )
    }
  }
}
