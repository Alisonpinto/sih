package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.Employee
import com.example.data.models.Organization
import com.example.data.models.SecurityAlert
import com.example.data.models.TelephonyConfig
import com.example.data.repository.MockRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface VoiceEnrollmentState {
  data object Idle : VoiceEnrollmentState
  data class Recording(val employeeName: String) : VoiceEnrollmentState
  data class Analyzing(val employeeName: String) : VoiceEnrollmentState
  data class Success(val employeeName: String) : VoiceEnrollmentState
}

class AdminViewModel : ViewModel() {

  val organization: StateFlow<Organization> = MockRepository.organization
  val telephonyConfig: StateFlow<TelephonyConfig> = MockRepository.telephonyConfig
  val securityAlerts: StateFlow<List<SecurityAlert>> = MockRepository.securityAlerts

  // Employee search and management
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  val employees: StateFlow<List<Employee>> = combine(
    MockRepository.employees,
    _searchQuery
  ) { list, query ->
    if (query.isBlank()) list
    else list.filter {
      it.name.contains(query, ignoreCase = true) ||
      it.employeeId.contains(query, ignoreCase = true) ||
      it.department.contains(query, ignoreCase = true) ||
      it.role.contains(query, ignoreCase = true)
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Add Employee Form State
  private val _showAddDialog = MutableStateFlow(false)
  val showAddDialog: StateFlow<Boolean> = _showAddDialog.asStateFlow()

  private val _formName = MutableStateFlow("")
  val formName: StateFlow<String> = _formName.asStateFlow()

  private val _formId = MutableStateFlow("")
  val formId: StateFlow<String> = _formId.asStateFlow()

  private val _formDepartment = MutableStateFlow("Finance")
  val formDepartment: StateFlow<String> = _formDepartment.asStateFlow()

  private val _formRole = MutableStateFlow("")
  val formRole: StateFlow<String> = _formRole.asStateFlow()

  private val _formEmail = MutableStateFlow("")
  val formEmail: StateFlow<String> = _formEmail.asStateFlow()

  private val _formPhone = MutableStateFlow("")
  val formPhone: StateFlow<String> = _formPhone.asStateFlow()

  private val _formError = MutableStateFlow<String?>(null)
  val formError: StateFlow<String?> = _formError.asStateFlow()

  // Voice Registration State
  private val _voiceEnrollmentState = MutableStateFlow<VoiceEnrollmentState>(VoiceEnrollmentState.Idle)
  val voiceEnrollmentState: StateFlow<VoiceEnrollmentState> = _voiceEnrollmentState.asStateFlow()

  fun onSearchQueryChange(query: String) {
    _searchQuery.value = query
  }

  fun openAddEmployeeDialog() {
    _formName.value = ""
    val nextIdNum = MockRepository.employees.value.size + 1
    _formId.value = "EMP00$nextIdNum"
    _formDepartment.value = "Finance"
    _formRole.value = ""
    _formEmail.value = ""
    _formPhone.value = "+91 "
    _formError.value = null
    _showAddDialog.value = true
  }

  fun closeAddEmployeeDialog() {
    _showAddDialog.value = false
    _formError.value = null
  }

  fun onFormNameChange(v: String) { _formName.value = v; _formError.value = null }
  fun onFormIdChange(v: String) { _formId.value = v; _formError.value = null }
  fun onFormDepartmentChange(v: String) { _formDepartment.value = v; _formError.value = null }
  fun onFormRoleChange(v: String) { _formRole.value = v; _formError.value = null }
  fun onFormEmailChange(v: String) { _formEmail.value = v; _formError.value = null }
  fun onFormPhoneChange(v: String) { _formPhone.value = v; _formError.value = null }

  fun saveEmployee(): Boolean {
    if (_formName.value.isBlank()) {
      _formError.value = "Employee name cannot be empty."
      return false
    }
    if (_formId.value.isBlank()) {
      _formError.value = "Employee ID cannot be empty."
      return false
    }
    if (_formRole.value.isBlank()) {
      _formError.value = "Please enter employee role."
      return false
    }
    if (_formEmail.value.isBlank()) {
      _formError.value = "Please enter employee email."
      return false
    }

    MockRepository.addEmployee(
      name = _formName.value.trim(),
      employeeId = _formId.value.trim(),
      department = _formDepartment.value.trim(),
      role = _formRole.value.trim(),
      email = _formEmail.value.trim(),
      phone = _formPhone.value.trim()
    )

    _showAddDialog.value = false
    return true
  }

  fun startVoiceEnrollment(employee: Employee) {
    viewModelScope.launch {
      _voiceEnrollmentState.value = VoiceEnrollmentState.Recording(employee.name)
      delay(1800)
      _voiceEnrollmentState.value = VoiceEnrollmentState.Analyzing(employee.name)
      delay(1800)
      MockRepository.enrollVoiceProfile(employee.employeeId)
      _voiceEnrollmentState.value = VoiceEnrollmentState.Success(employee.name)
      delay(2000)
      _voiceEnrollmentState.value = VoiceEnrollmentState.Idle
    }
  }

  fun dismissVoiceEnrollmentDialog() {
    _voiceEnrollmentState.value = VoiceEnrollmentState.Idle
  }
}
