package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.admin.EmployeesScreen
import com.example.ui.screens.admin.SecurityScreen
import com.example.ui.screens.admin.TelephonyScreen
import com.example.ui.screens.admin.VoiceProfilesScreen
import com.example.ui.screens.auth.AdminDetailsScreen
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.OrganizationDetailsScreen
import com.example.ui.screens.auth.OrganizationPasswordScreen
import com.example.ui.screens.auth.RegisterChoiceScreen
import com.example.ui.screens.auth.SplashScreen
import com.example.ui.screens.auth.TermsAndSecurityScreen
import com.example.ui.screens.user.ActiveCallScreen
import com.example.ui.screens.user.SettingsScreen
import com.example.ui.screens.user.UserMainContainerScreen
import com.example.ui.viewmodels.AdminViewModel
import com.example.ui.viewmodels.AuthViewModel
import com.example.ui.viewmodels.CallViewModel

object Routes {
  const val SPLASH = "splash"
  const val LOGIN = "login"
  const val REGISTER_CHOICE = "register_choice"
  const val ORG_DETAILS = "org_details"
  const val ADMIN_DETAILS = "admin_details"
  const val ORG_PASSWORD = "org_password"
  const val TERMS_SECURITY = "terms_security"
  const val ADMIN_DASHBOARD = "admin_dashboard"
  const val ADMIN_EMPLOYEES = "admin_employees"
  const val ADMIN_VOICE_PROFILES = "admin_voice_profiles"
  const val ADMIN_TELEPHONY = "admin_telephony"
  const val ADMIN_SECURITY = "admin_security"
  const val USER_MAIN = "user_main"
  const val ACTIVE_CALL = "active_call"
  const val SETTINGS = "settings"
}

@Composable
fun VoxShieldNavGraph(
  navController: NavHostController = rememberNavController(),
  authViewModel: AuthViewModel = viewModel(),
  adminViewModel: AdminViewModel = viewModel(),
  callViewModel: CallViewModel = viewModel()
) {
  NavHost(
    navController = navController,
    startDestination = Routes.SPLASH,
    enterTransition = { fadeIn(animationSpec = tween(300)) },
    exitTransition = { fadeOut(animationSpec = tween(300)) }
  ) {
    // 1. SPLASH SCREEN
    composable(Routes.SPLASH) {
      SplashScreen(
        onNavigateToLogin = {
          navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.SPLASH) { inclusive = true }
          }
        }
      )
    }

    // 2. LOGIN SCREEN
    composable(Routes.LOGIN) {
      LoginScreen(
        authViewModel = authViewModel,
        onNavigateToAdminDashboard = {
          navController.navigate(Routes.ADMIN_DASHBOARD) {
            popUpTo(Routes.LOGIN) { inclusive = true }
          }
        },
        onNavigateToUserHome = {
          navController.navigate(Routes.USER_MAIN) {
            popUpTo(Routes.LOGIN) { inclusive = true }
          }
        },
        onNavigateToRegister = {
          navController.navigate(Routes.REGISTER_CHOICE)
        }
      )
    }

    // 3. REGISTER CHOICE SCREEN
    composable(Routes.REGISTER_CHOICE) {
      RegisterChoiceScreen(
        onNavigateToOrgDetails = {
          navController.navigate(Routes.ORG_DETAILS)
        },
        onNavigateToEmployeeLogin = {
          navController.popBackStack()
        },
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }

    // 4. ORG REGISTRATION STEP 1
    composable(Routes.ORG_DETAILS) {
      OrganizationDetailsScreen(
        authViewModel = authViewModel,
        onNavigateToNext = {
          navController.navigate(Routes.ADMIN_DETAILS)
        },
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }

    // 5. ORG REGISTRATION STEP 2
    composable(Routes.ADMIN_DETAILS) {
      AdminDetailsScreen(
        authViewModel = authViewModel,
        onNavigateToNext = {
          navController.navigate(Routes.ORG_PASSWORD)
        },
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }

    // 6. ORG REGISTRATION STEP 3
    composable(Routes.ORG_PASSWORD) {
      OrganizationPasswordScreen(
        authViewModel = authViewModel,
        onNavigateToNext = {
          navController.navigate(Routes.TERMS_SECURITY)
        },
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }

    // 7. ORG REGISTRATION STEP 4
    composable(Routes.TERMS_SECURITY) {
      TermsAndSecurityScreen(
        authViewModel = authViewModel,
        onNavigateToDashboard = {
          navController.navigate(Routes.ADMIN_DASHBOARD) {
            popUpTo(Routes.LOGIN) { inclusive = true }
          }
        },
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }

    // 8. ADMIN DASHBOARD
    composable(Routes.ADMIN_DASHBOARD) {
      AdminDashboardScreen(
        adminViewModel = adminViewModel,
        authViewModel = authViewModel,
        onNavigateToEmployees = { navController.navigate(Routes.ADMIN_EMPLOYEES) },
        onNavigateToVoiceProfiles = { navController.navigate(Routes.ADMIN_VOICE_PROFILES) },
        onNavigateToTelephony = { navController.navigate(Routes.ADMIN_TELEPHONY) },
        onNavigateToSecurity = { navController.navigate(Routes.ADMIN_SECURITY) },
        onLogout = {
          navController.navigate(Routes.LOGIN) {
            popUpTo(0) { inclusive = true }
          }
        }
      )
    }

    // 9. ADMIN EMPLOYEES
    composable(Routes.ADMIN_EMPLOYEES) {
      EmployeesScreen(
        adminViewModel = adminViewModel,
        onNavigateBack = { navController.popBackStack() }
      )
    }

    // 10. ADMIN VOICE PROFILES
    composable(Routes.ADMIN_VOICE_PROFILES) {
      VoiceProfilesScreen(
        adminViewModel = adminViewModel,
        onNavigateBack = { navController.popBackStack() }
      )
    }

    // 11. ADMIN TELEPHONY
    composable(Routes.ADMIN_TELEPHONY) {
      TelephonyScreen(
        adminViewModel = adminViewModel,
        callViewModel = callViewModel,
        onNavigateToActiveCall = { navController.navigate(Routes.ACTIVE_CALL) },
        onNavigateBack = { navController.popBackStack() }
      )
    }

    // 12. ADMIN SECURITY
    composable(Routes.ADMIN_SECURITY) {
      SecurityScreen(
        adminViewModel = adminViewModel,
        onNavigateBack = { navController.popBackStack() }
      )
    }

    // 13. USER MAIN CONTAINER (Home, Calls, AI Results, Profile)
    composable(Routes.USER_MAIN) {
      UserMainContainerScreen(
        authViewModel = authViewModel,
        callViewModel = callViewModel,
        onNavigateToActiveCall = { navController.navigate(Routes.ACTIVE_CALL) },
        onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
        onLogout = {
          navController.navigate(Routes.LOGIN) {
            popUpTo(0) { inclusive = true }
          }
        }
      )
    }

    // 14. ACTIVE CALL SIMULATION
    composable(Routes.ACTIVE_CALL) {
      ActiveCallScreen(
        callViewModel = callViewModel,
        onCallEnded = { navController.popBackStack() }
      )
    }

    // 15. SETTINGS
    composable(Routes.SETTINGS) {
      SettingsScreen(
        authViewModel = authViewModel,
        onNavigateBack = { navController.popBackStack() },
        onLogout = {
          navController.navigate(Routes.LOGIN) {
            popUpTo(0) { inclusive = true }
          }
        }
      )
    }
  }
}
