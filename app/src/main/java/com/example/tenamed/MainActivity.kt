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
import com.example.tenamed.ui.DoctorAppointmentDetailScreen
//import com.example.tena.ui.BookAppointmentPlaceholderScreen
import com.example.tenamed.ui.PatientLandingScreen
import com.example.tenamed.ui.screen.DoctorSignupScreen
import com.example.tenamed.ui.theme.TenaTheme
import com.example.tenamed.ui.DoctorLoginScreen
//import com.example.tena.ui.PostBookingHomeScreen
import com.example.tenamed.ui.PatientLoginScreen
import com.example.tenamed.ui.PatientSignupScreen
import com.example.tenamed.ui.WelcomeScreen
import com.example.tenamed.ui.DoctorProfileScreen
import com.example.tenamed.viewmodel.AuthViewModel
import com.example.tenamed.ui.DoctorDetailScreen
import com.example.tenamed.ui.PostBookingHomeScreen
import com.example.tenamed.ui.ProfileScreen
import com.example.tenamed.ui.screen.AppointmentListScreen
import com.example.tenamed.ui.screen.BookAppointmentPlaceholderScreen
import com.example.tenamed.ui.screen.BookingSuccessScreen
import com.example.tenamed.ui.DoctorHomeAfterAppointmentsScreen
import com.example.tenamed.ui.DoctorProfileScreenAfterSetup
import com.example.tenamed.data.repository.AuthRepository


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
//
                    composable("doctor_profile") {
                        DoctorProfileScreen(navController, authViewModel)
                    }

                    composable("doctor_detail") {
                        DoctorDetailScreen(navController)
                    }
                    composable("book_placeholder") {
                        BookAppointmentPlaceholderScreen(navController)
                    }
                    composable("booking_success") {
                        BookingSuccessScreen(navController)
                    }
                    composable("post_booking_home") {
                        PostBookingHomeScreen(navController)
                    }
                    composable("appointments") {
                        AppointmentListScreen(navController)
                    }
                    composable("profile") {
                        ProfileScreen(navController = navController, authRepository = authRepository, context = context)
                    }
                    composable("doctor_profile_after_setup") {
                        DoctorProfileScreenAfterSetup(navController)
                    }
                    composable("doctor_appointments_list") {
                        DoctorHomeAfterAppointmentsScreen(navController)
                    }
                    composable("doctor_appointment_detail") {
                        DoctorAppointmentDetailScreen(navController)
                    }
                }
            }
        }
    }
}

