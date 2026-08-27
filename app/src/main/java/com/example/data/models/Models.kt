package com.example.data.models

enum class UserRole {
  ADMIN,
  EMPLOYEE
}

enum class VoiceProfileStatus {
  ENROLLED,
  PENDING,
  NOT_ENROLLED
}

enum class EmployeeStatus {
  ACTIVE,
  INACTIVE
}

enum class CallType {
  INCOMING,
  OUTGOING
}

enum class DetectionVerdict {
  GENUINE,
  AI_DETECTED,
  UNCERTAIN
}

enum class RiskLevel {
  LOW,
  MEDIUM,
  HIGH
}

data class User(
  val id: String,
  val employeeId: String = id,
  val name: String,
  val email: String,
  val role: UserRole,
  val organizationId: String,
  val department: String = "Finance",
  val voiceProfileStatus: VoiceProfileStatus = VoiceProfileStatus.ENROLLED
)

data class Organization(
  val id: String,
  val name: String,
  val industry: String,
  val size: String,
  val adminName: String,
  val adminEmail: String,
  val adminPhone: String,
  val status: String = "Active"
)

data class Employee(
  val id: String,
  val name: String,
  val employeeId: String,
  val department: String,
  val role: String,
  val email: String,
  val phone: String,
  val voiceProfileStatus: VoiceProfileStatus,
  val status: EmployeeStatus = EmployeeStatus.ACTIVE
)

data class AIDetectionResult(
  val id: String,
  val callId: String,
  val verdict: DetectionVerdict,
  val confidencePercentage: Int,
  val riskLevel: RiskLevel,
  val voiceAuthenticityPercentage: Int,
  val aiProbabilityPercentage: Int,
  val spectralPatternStatus: String = "Suspicious",
  val speechPatternStatus: String = "Suspicious",
  val prosodyStatus: String = "Abnormal",
  val frequencyStatus: String = "Suspicious",
  val recommendation: String = "Do not share sensitive information. Verify the caller through an independent communication channel."
)

data class CallRecord(
  val id: String,
  val callerName: String,
  val phoneNumber: String,
  val date: String,
  val time: String,
  val duration: String,
  val callType: CallType,
  val verdict: DetectionVerdict,
  val riskLevel: RiskLevel,
  val aiResult: AIDetectionResult
)

data class SecurityAlert(
  val id: String,
  val title: String,
  val description: String,
  val timestamp: String,
  val severity: RiskLevel
)

data class TelephonyConfig(
  val deviceName: String = "Office Landline",
  val extension: String = "204",
  val phoneNumber: String = "+91 98765 43210",
  val status: String = "Connected",
  val connectionStatus: String = "Active"
)
