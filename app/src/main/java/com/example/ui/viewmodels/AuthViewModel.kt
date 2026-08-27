package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.data.models.User
import com.example.data.models.UserRole
import com.example.data.repository.MockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

  // Login form state
  private val _userId = MutableStateFlow("")
  val userId: StateFlow<String> = _userId.asStateFlow()

  private val _email = MutableStateFlow("")
  val email: StateFlow<String> = _email.asStateFlow()

  private val _password = MutableStateFlow("")
  val password: StateFlow<String> = _password.asStateFlow()

  private val _loginError = MutableStateFlow<String?>(null)
  val loginError: StateFlow<String?> = _loginError.asStateFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  // Multi-step Organization Registration state
  // Step 1: Org Details
  private val _orgName = MutableStateFlow("ABC Bank")
  val orgName: StateFlow<String> = _orgName.asStateFlow()

  private val _orgId = MutableStateFlow("ABC001")
  val orgId: StateFlow<String> = _orgId.asStateFlow()

  private val _industry = MutableStateFlow("Banking")
  val industry: StateFlow<String> = _industry.asStateFlow()

  private val _orgSize = MutableStateFlow("500-1000 Employees")
  val orgSize: StateFlow<String> = _orgSize.asStateFlow()

  // Step 2: Admin Details
  private val _adminFullName = MutableStateFlow("Alison Pinto")
  val adminFullName: StateFlow<String> = _adminFullName.asStateFlow()

  private val _adminEmail = MutableStateFlow("admin@abcbank.com")
  val adminEmail: StateFlow<String> = _adminEmail.asStateFlow()

  private val _adminPhone = MutableStateFlow("+91 98765 43210")
  val adminPhone: StateFlow<String> = _adminPhone.asStateFlow()

  private val _adminRole = MutableStateFlow("Organization Administrator")
  val adminRole: StateFlow<String> = _adminRole.asStateFlow()

  // Step 3: Password
  private val _regPassword = MutableStateFlow("")
  val regPassword: StateFlow<String> = _regPassword.asStateFlow()

  private val _regConfirmPassword = MutableStateFlow("")
  val regConfirmPassword: StateFlow<String> = _regConfirmPassword.asStateFlow()

  // Step 4: Terms
  private val _termsAccepted = MutableStateFlow(false)
  val termsAccepted: StateFlow<Boolean> = _termsAccepted.asStateFlow()

  // Validation errors
  private val _regStepError = MutableStateFlow<String?>(null)
  val regStepError: StateFlow<String?> = _regStepError.asStateFlow()

  // Login Actions
  fun onUserIdChange(value: String) {
    _userId.value = value
    _loginError.value = null
  }

  fun onEmailChange(value: String) {
    _email.value = value
    _loginError.value = null
  }

  fun onPasswordChange(value: String) {
    _password.value = value
    _loginError.value = null
  }

  fun fillAdminDemoCredentials() {
    _userId.value = "admin001"
    _email.value = "admin@abc.com"
    _password.value = "123456"
    _loginError.value = null
  }

  fun fillEmployeeDemoCredentials() {
    _userId.value = "EMP001"
    _email.value = "employee@abc.com"
    _password.value = "123456"
    _loginError.value = null
  }

  fun login(onSuccess: (UserRole) -> Unit) {
    if (_email.value.isBlank() && _userId.value.isBlank()) {
      _loginError.value = "Please enter your User ID or Email."
      return
    }
    if (_password.value.isBlank()) {
      _loginError.value = "Password cannot be empty."
      return
    }

    val role = MockRepository.login(_userId.value, _email.value, _password.value)
    if (role != null) {
      _loginError.value = null
      onSuccess(role)
    } else {
      _loginError.value = "Invalid login credentials. Use demo: admin001 / 123456 or EMP001 / 123456"
    }
  }

  // Registration step updates
  fun onOrgNameChange(value: String) { _orgName.value = value; _regStepError.value = null }
  fun onOrgIdChange(value: String) { _orgId.value = value; _regStepError.value = null }
  fun onIndustryChange(value: String) { _industry.value = value; _regStepError.value = null }
  fun onOrgSizeChange(value: String) { _orgSize.value = value; _regStepError.value = null }

  fun validateStep1(): Boolean {
    if (_orgName.value.isBlank()) {
      _regStepError.value = "Organization name is required."
      return false
    }
    if (_orgId.value.isBlank()) {
      _regStepError.value = "Organization ID is required."
      return false
    }
    if (_industry.value.isBlank()) {
      _regStepError.value = "Please specify industry."
      return false
    }
    if (_orgSize.value.isBlank()) {
      _regStepError.value = "Please specify organization size."
      return false
    }
    _regStepError.value = null
    return true
  }

  fun onAdminFullNameChange(value: String) { _adminFullName.value = value; _regStepError.value = null }
  fun onAdminEmailChange(value: String) { _adminEmail.value = value; _regStepError.value = null }
  fun onAdminPhoneChange(value: String) { _adminPhone.value = value; _regStepError.value = null }
  fun onAdminRoleChange(value: String) { _adminRole.value = value; _regStepError.value = null }

  fun validateStep2(): Boolean {
    if (_adminFullName.value.isBlank()) {
      _regStepError.value = "Admin full name is required."
      return false
    }
    if (_adminEmail.value.isBlank()) {
      _regStepError.value = "Please enter official work email."
      return false
    }
    if (_adminPhone.value.isBlank()) {
      _regStepError.value = "Phone number is required."
      return false
    }
    _regStepError.value = null
    return true
  }

  fun onRegPasswordChange(value: String) { _regPassword.value = value; _regStepError.value = null }
  fun onRegConfirmPasswordChange(value: String) { _regConfirmPassword.value = value; _regStepError.value = null }

  fun validateStep3(): Boolean {
    if (_regPassword.value.isBlank()) {
      _regStepError.value = "Password cannot be empty."
      return false
    }
    if (_regConfirmPassword.value.isBlank()) {
      _regStepError.value = "Please confirm your password."
      return false
    }
    if (_regPassword.value != _regConfirmPassword.value) {
      _regStepError.value = "Passwords do not match."
      return false
    }
    _regStepError.value = null
    return true
  }

  fun onTermsAcceptedChange(value: Boolean) {
    _termsAccepted.value = value
    _regStepError.value = null
  }

  fun createOrganization(onSuccess: () -> Unit) {
    if (!_termsAccepted.value) {
      _regStepError.value = "Please accept the Terms & Security Policy."
      return
    }

    MockRepository.registerOrganization(
      orgName = _orgName.value,
      orgId = _orgId.value,
      industry = _industry.value,
      size = _orgSize.value,
      adminName = _adminFullName.value,
      adminEmail = _adminEmail.value,
      adminPhone = _adminPhone.value
    )
    _regStepError.value = null
    onSuccess()
  }

  fun logout(onLoggedOut: () -> Unit) {
    MockRepository.logout()
    _password.value = ""
    _loginError.value = null
    onLoggedOut()
  }
}
