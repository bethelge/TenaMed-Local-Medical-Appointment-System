package com.example.tena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.tena.ui.PatientLoginScreen
import com.example.tena.ui.PatientSignupScreen




import com.example.tena.ui.DoctorLoginScreen
import com.example.tena.ui.PatientLandingScreen
import com.example.tena.ui.WelcomeScreen
import com.example.tenamed.data.repository.AuthRepository
import com.example.tenamed.ui.screen.DoctorSignupScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TenaTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val authRepository = AuthRepository()
                val context = LocalContext.current
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(navController)
                    }
                    composable("doctor_login") {
                        DoctorLoginScreen(navController)
                    }
                    composable("doctor_signup") {
                        DoctorSignupScreen(navController)
                    }
                    composable("patient_login") {
                        PatientLoginScreen(navController)
                    }
                    composable("patient_signup") {
                        PatientSignupScreen(navController)
                    }
                    composable("patient_landing") {
                        PatientLandingScreen(navController, authViewModel)
                    }


                }
            }
        }
    }
}

