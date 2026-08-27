package com.example.ui.screens.admin

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EmployeeCard
import com.example.ui.components.VoxShieldButton
import com.example.ui.components.VoxShieldTextField
import com.example.ui.components.VoxShieldTopAppBar
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesScreen(
  adminViewModel: AdminViewModel,
  onNavigateBack: () -> Unit
) {
  val employees by adminViewModel.employees.collectAsState()
  val searchQuery by adminViewModel.searchQuery.collectAsState()
  val showAddDialog by adminViewModel.showAddDialog.collectAsState()

  val formName by adminViewModel.formName.collectAsState()
  val formId by adminViewModel.formId.collectAsState()
  val formDept by adminViewModel.formDepartment.collectAsState()
  val formRole by adminViewModel.formRole.collectAsState()
  val formEmail by adminViewModel.formEmail.collectAsState()
  val formPhone by adminViewModel.formPhone.collectAsState()
  val formError by adminViewModel.formError.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(CyberBlack)
      .testTag("employees_screen")
  ) {
    VoxShieldTopAppBar(
      title = "Employees",
      subtitle = "${employees.size} registered directory profiles",
      onBackClick = onNavigateBack
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
    ) {
      Spacer(modifier = Modifier.height(12.dp))

      // Search & Add Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        VoxShieldTextField(
          value = searchQuery,
          onValueChange = { adminViewModel.onSearchQueryChange(it) },
          label = "",
          placeholder = "Search by name, ID or department...",
          leadingIcon = Icons.Default.Search,
          modifier = Modifier.weight(1f),
          testTag = "employee_search_input"
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      VoxShieldButton(
        text = "+ Add employee",
        onClick = { adminViewModel.openAddEmployeeDialog() },
        leadingIcon = Icons.Default.Add,
        modifier = Modifier.fillMaxWidth(),
        testTag = "add_employee_trigger_btn"
      )

      Spacer(modifier = Modifier.height(16.dp))

      if (employees.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = TextMuted,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No employees found matching '$searchQuery'",
              color = TextSecondary,
              fontSize = 14.sp
            )
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(employees, key = { it.id }) { employee ->
            EmployeeCard(
              employee = employee,
              onRegisterVoiceClick = { adminViewModel.startVoiceEnrollment(employee) }
            )
          }
          item {
            Spacer(modifier = Modifier.height(24.dp))
          }
        }
      }
    }
  }

  // ADD EMPLOYEE MODAL DIALOG
  if (showAddDialog) {
    BasicAlertDialog(
      onDismissRequest = { adminViewModel.closeAddEmployeeDialog() },
      modifier = Modifier.testTag("add_employee_dialog")
    ) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = CyberDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberCardBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
        ) {
          Text(
            text = "Add New Employee",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "Register staff member into the VoxShield security registry.",
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
          )

          VoxShieldTextField(
            value = formName,
            onValueChange = { adminViewModel.onFormNameChange(it) },
            label = "Employee Name",
            placeholder = "e.g. Ramesh Kulkarni",
            leadingIcon = Icons.Default.Person,
            testTag = "form_emp_name"
          )

          Spacer(modifier = Modifier.height(12.dp))

          VoxShieldTextField(
            value = formId,
            onValueChange = { adminViewModel.onFormIdChange(it) },
            label = "Employee ID",
            placeholder = "e.g. EMP006",
            leadingIcon = Icons.Default.Badge,
            testTag = "form_emp_id"
          )

          Spacer(modifier = Modifier.height(12.dp))

          VoxShieldTextField(
            value = formDept,
            onValueChange = { adminViewModel.onFormDepartmentChange(it) },
            label = "Department",
            placeholder = "e.g. Finance / Security / Ops",
            leadingIcon = Icons.Default.Work,
            testTag = "form_emp_dept"
          )

          Spacer(modifier = Modifier.height(12.dp))

          VoxShieldTextField(
            value = formRole,
            onValueChange = { adminViewModel.onFormRoleChange(it) },
            label = "Role",
            placeholder = "e.g. Senior Analyst",
            leadingIcon = Icons.Default.Work,
            testTag = "form_emp_role"
          )

          Spacer(modifier = Modifier.height(12.dp))

          VoxShieldTextField(
            value = formEmail,
            onValueChange = { adminViewModel.onFormEmailChange(it) },
            label = "Email",
            placeholder = "e.g. ramesh@abcbank.com",
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            testTag = "form_emp_email"
          )

          Spacer(modifier = Modifier.height(12.dp))

          VoxShieldTextField(
            value = formPhone,
            onValueChange = { adminViewModel.onFormPhoneChange(it) },
            label = "Phone",
            placeholder = "+91 98XXX XXXXX",
            leadingIcon = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            testTag = "form_emp_phone"
          )

          if (formError != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = formError ?: "",
              color = DangerRed,
              fontSize = 12.sp
            )
          }

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            VoxShieldButton(
              text = "Cancel",
              onClick = { adminViewModel.closeAddEmployeeDialog() },
              isSecondary = true,
              modifier = Modifier.weight(1f),
              testTag = "form_emp_cancel_btn"
            )

            VoxShieldButton(
              text = "Save Employee",
              onClick = { adminViewModel.saveEmployee() },
              modifier = Modifier.weight(1f),
              testTag = "form_emp_save_btn"
            )
          }
        }
      }
    }
  }
}
