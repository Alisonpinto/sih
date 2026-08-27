package com.example.data.repository

import com.example.data.models.AIDetectionResult
import com.example.data.models.CallRecord
import com.example.data.models.CallType
import com.example.data.models.DetectionVerdict
import com.example.data.models.Employee
import com.example.data.models.EmployeeStatus
import com.example.data.models.Organization
import com.example.data.models.RiskLevel
import com.example.data.models.SecurityAlert
import com.example.data.models.TelephonyConfig
import com.example.data.models.User
import com.example.data.models.UserRole
import com.example.data.models.VoiceProfileStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object MockRepository {

  // Current session
  private val _currentUser = MutableStateFlow<User?>(null)
  val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

  // Organization
  private val _organization = MutableStateFlow(
    Organization(
      id = "ABC001",
      name = "ABC Bank",
      industry = "Banking",
      size = "500-1000 Employees",
      adminName = "Alison Pinto",
      adminEmail = "admin@abcbank.com",
      adminPhone = "+91 98765 43210",
      status = "Active"
    )
  )
  val organization: StateFlow<Organization> = _organization.asStateFlow()

  // Employees
  private val _employees = MutableStateFlow(
    listOf(
      Employee(
        id = "1",
        name = "Rahul Mehta",
        employeeId = "EMP001",
        department = "Finance",
        role = "CFO",
        email = "rahul.mehta@abcbank.com",
        phone = "+91 98234 11223",
        voiceProfileStatus = VoiceProfileStatus.ENROLLED,
        status = EmployeeStatus.ACTIVE
      ),
      Employee(
        id = "2",
        name = "Priya Shah",
        employeeId = "EMP002",
        department = "Finance",
        role = "Manager",
        email = "priya.shah@abcbank.com",
        phone = "+91 97123 44556",
        voiceProfileStatus = VoiceProfileStatus.ENROLLED,
        status = EmployeeStatus.ACTIVE
      ),
      Employee(
        id = "3",
        name = "Amit Patil",
        employeeId = "EMP003",
        department = "IT",
        role = "IT Admin",
        email = "amit.patil@abcbank.com",
        phone = "+91 96543 21098",
        voiceProfileStatus = VoiceProfileStatus.PENDING,
        status = EmployeeStatus.ACTIVE
      ),
      Employee(
        id = "4",
        name = "Sneha Rao",
        employeeId = "EMP004",
        department = "HR",
        role = "HR Manager",
        email = "sneha.rao@abcbank.com",
        phone = "+91 91234 56789",
        voiceProfileStatus = VoiceProfileStatus.PENDING,
        status = EmployeeStatus.INACTIVE
      ),
      Employee(
        id = "5",
        name = "Vikram Joshi",
        employeeId = "EMP005",
        department = "Operations",
        role = "Manager",
        email = "vikram.joshi@abcbank.com",
        phone = "+91 93456 78901",
        voiceProfileStatus = VoiceProfileStatus.NOT_ENROLLED,
        status = EmployeeStatus.ACTIVE
      )
    )
  )
  val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

  // Predefined AI Detection Results & Calls
  private val aiResult1 = AIDetectionResult(
    id = "res_1",
    callId = "call_1",
    verdict = DetectionVerdict.GENUINE,
    confidencePercentage = 97,
    riskLevel = RiskLevel.LOW,
    voiceAuthenticityPercentage = 97,
    aiProbabilityPercentage = 3,
    spectralPatternStatus = "Natural",
    speechPatternStatus = "Natural",
    prosodyStatus = "Natural",
    frequencyStatus = "Natural",
    recommendation = "Genuine human voice confirmed. Caller verified against enrolled corporate voiceprint."
  )

  private val aiResult2 = AIDetectionResult(
    id = "res_2",
    callId = "call_2",
    verdict = DetectionVerdict.AI_DETECTED,
    confidencePercentage = 94,
    riskLevel = RiskLevel.HIGH,
    voiceAuthenticityPercentage = 6,
    aiProbabilityPercentage = 94,
    spectralPatternStatus = "Suspicious",
    speechPatternStatus = "Suspicious",
    prosodyStatus = "Abnormal",
    frequencyStatus = "Suspicious",
    recommendation = "Do not share sensitive information. Verify the caller through an independent communication channel."
  )

  private val aiResult3 = AIDetectionResult(
    id = "res_3",
    callId = "call_3",
    verdict = DetectionVerdict.GENUINE,
    confidencePercentage = 98,
    riskLevel = RiskLevel.LOW,
    voiceAuthenticityPercentage = 98,
    aiProbabilityPercentage = 2,
    spectralPatternStatus = "Natural",
    speechPatternStatus = "Natural",
    prosodyStatus = "Natural",
    frequencyStatus = "Natural",
    recommendation = "Call authenticated successfully with low background jitter."
  )

  private val aiResult4 = AIDetectionResult(
    id = "res_4",
    callId = "call_4",
    verdict = DetectionVerdict.AI_DETECTED,
    confidencePercentage = 91,
    riskLevel = RiskLevel.HIGH,
    voiceAuthenticityPercentage = 9,
    aiProbabilityPercentage = 91,
    spectralPatternStatus = "Synthetic Synthesizer Artifacts",
    speechPatternStatus = "Monotone Cadence",
    prosodyStatus = "Unnatural Pitch Modulation",
    frequencyStatus = "Frequency Gap at 4kHz",
    recommendation = "High risk of deepfake impersonation. Caller flagged as automated voice clone."
  )

  private val aiResult5 = AIDetectionResult(
    id = "res_5",
    callId = "call_5",
    verdict = DetectionVerdict.GENUINE,
    confidencePercentage = 96,
    riskLevel = RiskLevel.LOW,
    voiceAuthenticityPercentage = 96,
    aiProbabilityPercentage = 4,
    spectralPatternStatus = "Natural",
    speechPatternStatus = "Natural",
    prosodyStatus = "Natural",
    frequencyStatus = "Natural",
    recommendation = "Genuine corporate caller verified. Standard biometrics match."
  )

  private val aiResult6 = AIDetectionResult(
    id = "res_6",
    callId = "call_6",
    verdict = DetectionVerdict.UNCERTAIN,
    confidencePercentage = 68,
    riskLevel = RiskLevel.MEDIUM,
    voiceAuthenticityPercentage = 48,
    aiProbabilityPercentage = 52,
    spectralPatternStatus = "Degraded Audio",
    speechPatternStatus = "Possible Audio Compression",
    prosodyStatus = "Borderline",
    frequencyStatus = "Noise Interference",
    recommendation = "Inconclusive acoustic signature due to line degradation. Proceed with caution."
  )

  // Call Logs
  private val _callLogs = MutableStateFlow(
    listOf(
      CallRecord(
        id = "call_1",
        callerName = "Rahul Mehta",
        phoneNumber = "+91 98234 11223",
        date = "Today",
        time = "02:12 PM",
        duration = "02:14",
        callType = CallType.INCOMING,
        verdict = DetectionVerdict.GENUINE,
        riskLevel = RiskLevel.LOW,
        aiResult = aiResult1
      ),
      CallRecord(
        id = "call_2",
        callerName = "Unknown Number",
        phoneNumber = "+91 88921 55670",
        date = "Today",
        time = "12:43 PM",
        duration = "04:22",
        callType = CallType.INCOMING,
        verdict = DetectionVerdict.AI_DETECTED,
        riskLevel = RiskLevel.HIGH,
        aiResult = aiResult2
      ),
      CallRecord(
        id = "call_3",
        callerName = "Priya Shah",
        phoneNumber = "+91 97123 44556",
        date = "Yesterday",
        time = "06:32 PM",
        duration = "03:10",
        callType = CallType.OUTGOING,
        verdict = DetectionVerdict.GENUINE,
        riskLevel = RiskLevel.LOW,
        aiResult = aiResult3
      ),
      CallRecord(
        id = "call_4",
        callerName = "Support Dispatch",
        phoneNumber = "+91 90000 12345",
        date = "24 Aug",
        time = "10:15 AM",
        duration = "01:45",
        callType = CallType.INCOMING,
        verdict = DetectionVerdict.AI_DETECTED,
        riskLevel = RiskLevel.HIGH,
        aiResult = aiResult4
      ),
      CallRecord(
        id = "call_5",
        callerName = "Amit Patil",
        phoneNumber = "+91 96543 21098",
        date = "23 Aug",
        time = "04:50 PM",
        duration = "05:12",
        callType = CallType.INCOMING,
        verdict = DetectionVerdict.GENUINE,
        riskLevel = RiskLevel.LOW,
        aiResult = aiResult5
      ),
      CallRecord(
        id = "call_6",
        callerName = "Financial Advisor",
        phoneNumber = "+91 80123 99887",
        date = "22 Aug",
        time = "11:20 AM",
        duration = "00:58",
        callType = CallType.INCOMING,
        verdict = DetectionVerdict.UNCERTAIN,
        riskLevel = RiskLevel.MEDIUM,
        aiResult = aiResult6
      )
    )
  )
  val callLogs: StateFlow<List<CallRecord>> = _callLogs.asStateFlow()

  // Selected Call for AI Results Screen
  private val _selectedCall = MutableStateFlow<CallRecord?>(null)
  val selectedCall: StateFlow<CallRecord?> = _selectedCall.asStateFlow()

  // Security Alerts
  private val _securityAlerts = MutableStateFlow(
    listOf(
      SecurityAlert(
        id = "alt_1",
        title = "AI-generated voice detected",
        description = "Deepfake synthesis pattern detected on Extension 204 from +91 88921 55670.",
        timestamp = "12:43 PM Today",
        severity = RiskLevel.HIGH
      ),
      SecurityAlert(
        id = "alt_2",
        title = "Unknown caller detected",
        description = "Unenrolled voice signature attempted financial authorization transfer.",
        timestamp = "10:15 AM 24 Aug",
        severity = RiskLevel.MEDIUM
      ),
      SecurityAlert(
        id = "alt_3",
        title = "High-risk call blocked",
        description = "Automated voice clone with 94% synthetic probability flagged & isolated.",
        timestamp = "09:30 AM 22 Aug",
        severity = RiskLevel.HIGH
      )
    )
  )
  val securityAlerts: StateFlow<List<SecurityAlert>> = _securityAlerts.asStateFlow()

  // Telephony config
  private val _telephonyConfig = MutableStateFlow(TelephonyConfig())
  val telephonyConfig: StateFlow<TelephonyConfig> = _telephonyConfig.asStateFlow()

  // Settings State
  private val _aiDetectionEnabled = MutableStateFlow(true)
  val aiDetectionEnabled: StateFlow<Boolean> = _aiDetectionEnabled.asStateFlow()

  private val _callAlertsEnabled = MutableStateFlow(true)
  val callAlertsEnabled: StateFlow<Boolean> = _callAlertsEnabled.asStateFlow()

  private val _riskNotificationsEnabled = MutableStateFlow(true)
  val riskNotificationsEnabled: StateFlow<Boolean> = _riskNotificationsEnabled.asStateFlow()

  private val _darkModeEnabled = MutableStateFlow(true)
  val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled.asStateFlow()

  fun setAiDetectionEnabled(value: Boolean) { _aiDetectionEnabled.value = value }
  fun setCallAlertsEnabled(value: Boolean) { _callAlertsEnabled.value = value }
  fun setRiskNotificationsEnabled(value: Boolean) { _riskNotificationsEnabled.value = value }
  fun setDarkModeEnabled(value: Boolean) { _darkModeEnabled.value = value }

  // Authentication
  fun login(userIdInput: String, emailInput: String, passwordInput: String): UserRole? {
    val trimmedId = userIdInput.trim()
    val trimmedEmail = emailInput.trim()
    val trimmedPass = passwordInput.trim()

    // Admin demo matching
    if ((trimmedId.equals("admin001", ignoreCase = true) || trimmedEmail.equals("admin@abc.com", ignoreCase = true) || trimmedEmail.equals("admin@abcbank.com", ignoreCase = true)) &&
      trimmedPass == "123456"
    ) {
      val admin = User(
        id = "admin001",
        name = "Alison Pinto",
        email = "admin@abc.com",
        role = UserRole.ADMIN,
        organizationId = _organization.value.id,
        department = "Administration",
        voiceProfileStatus = VoiceProfileStatus.ENROLLED
      )
      _currentUser.value = admin
      return UserRole.ADMIN
    }

    // Employee demo matching
    if ((trimmedId.equals("EMP001", ignoreCase = true) || trimmedEmail.equals("employee@abc.com", ignoreCase = true)) &&
      trimmedPass == "123456"
    ) {
      val emp = User(
        id = "EMP001",
        name = "Alison Pinto",
        email = "employee@abc.com",
        role = UserRole.EMPLOYEE,
        organizationId = _organization.value.id,
        department = "Finance",
        voiceProfileStatus = VoiceProfileStatus.ENROLLED
      )
      _currentUser.value = emp
      return UserRole.EMPLOYEE
    }

    // Generic check for custom registered org admin
    if (trimmedEmail.isNotEmpty() && trimmedPass.isNotEmpty() && trimmedPass == "123456") {
      val customUser = User(
        id = if (trimmedId.isNotEmpty()) trimmedId else "USR" + (100..999).random(),
        name = if (trimmedEmail.contains("@")) trimmedEmail.substringBefore("@").replace(".", " ").capitalize(Locale.ROOT) else "Authorized User",
        email = trimmedEmail,
        role = if (trimmedId.startsWith("admin", ignoreCase = true)) UserRole.ADMIN else UserRole.EMPLOYEE,
        organizationId = _organization.value.id
      )
      _currentUser.value = customUser
      return customUser.role
    }

    return null
  }

  fun registerOrganization(
    orgName: String,
    orgId: String,
    industry: String,
    size: String,
    adminName: String,
    adminEmail: String,
    adminPhone: String
  ) {
    val newOrg = Organization(
      id = orgId,
      name = orgName,
      industry = industry,
      size = size,
      adminName = adminName,
      adminEmail = adminEmail,
      adminPhone = adminPhone,
      status = "Active"
    )
    _organization.value = newOrg

    val newAdmin = User(
      id = "admin001",
      name = adminName,
      email = adminEmail,
      role = UserRole.ADMIN,
      organizationId = orgId,
      department = "Executive",
      voiceProfileStatus = VoiceProfileStatus.ENROLLED
    )
    _currentUser.value = newAdmin
  }

  fun addEmployee(
    name: String,
    employeeId: String,
    department: String,
    role: String,
    email: String,
    phone: String
  ) {
    val newEmployee = Employee(
      id = UUID.randomUUID().toString(),
      name = name,
      employeeId = employeeId,
      department = department,
      role = role,
      email = email,
      phone = phone,
      voiceProfileStatus = VoiceProfileStatus.PENDING,
      status = EmployeeStatus.ACTIVE
    )
    _employees.value = listOf(newEmployee) + _employees.value
  }

  fun enrollVoiceProfile(employeeId: String) {
    _employees.value = _employees.value.map { emp ->
      if (emp.employeeId.equals(employeeId, ignoreCase = true) || emp.id == employeeId) {
        emp.copy(voiceProfileStatus = VoiceProfileStatus.ENROLLED)
      } else {
        emp
      }
    }
  }

  fun selectCallForAiDetails(call: CallRecord) {
    _selectedCall.value = call
  }

  fun addCallRecord(
    callerName: String,
    phoneNumber: String,
    isAiDetected: Boolean,
    confidencePercentage: Int = if (isAiDetected) 94 else 97
  ): CallRecord {
    val newCallId = "call_" + System.currentTimeMillis()
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

    val aiResult = if (isAiDetected) {
      AIDetectionResult(
        id = "res_" + System.currentTimeMillis(),
        callId = newCallId,
        verdict = DetectionVerdict.AI_DETECTED,
        confidencePercentage = confidencePercentage,
        riskLevel = RiskLevel.HIGH,
        voiceAuthenticityPercentage = (100 - confidencePercentage).coerceAtLeast(3),
        aiProbabilityPercentage = confidencePercentage,
        spectralPatternStatus = "Suspicious",
        speechPatternStatus = "Synthetic Phase Inversion",
        prosodyStatus = "Abnormal Intonation",
        frequencyStatus = "Voice Clone Frequency Signature",
        recommendation = "Do not share sensitive information. Verify the caller through an independent communication channel."
      )
    } else {
      AIDetectionResult(
        id = "res_" + System.currentTimeMillis(),
        callId = newCallId,
        verdict = DetectionVerdict.GENUINE,
        confidencePercentage = confidencePercentage,
        riskLevel = RiskLevel.LOW,
        voiceAuthenticityPercentage = confidencePercentage,
        aiProbabilityPercentage = (100 - confidencePercentage).coerceAtLeast(2),
        spectralPatternStatus = "Natural Dynamic Range",
        speechPatternStatus = "Human Acoustic Variability",
        prosodyStatus = "Natural Micro-pitch Shifts",
        frequencyStatus = "Full Spectrum Resonances",
        recommendation = "Genuine human voice confirmed. Caller verified against enrolled corporate voiceprint."
      )
    }

    val newCall = CallRecord(
      id = newCallId,
      callerName = callerName,
      phoneNumber = phoneNumber,
      date = "Today",
      time = timeFormat,
      duration = "00:35",
      callType = CallType.INCOMING,
      verdict = if (isAiDetected) DetectionVerdict.AI_DETECTED else DetectionVerdict.GENUINE,
      riskLevel = if (isAiDetected) RiskLevel.HIGH else RiskLevel.LOW,
      aiResult = aiResult
    )

    _callLogs.value = listOf(newCall) + _callLogs.value

    if (isAiDetected) {
      val newAlert = SecurityAlert(
        id = "alt_" + System.currentTimeMillis(),
        title = "AI-generated voice detected",
        description = "Live call from $callerName ($phoneNumber) flagged with $confidencePercentage% synthetic probability.",
        timestamp = "$timeFormat Today",
        severity = RiskLevel.HIGH
      )
      _securityAlerts.value = listOf(newAlert) + _securityAlerts.value
    }

    return newCall
  }

  fun logout() {
    _currentUser.value = null
  }
}
