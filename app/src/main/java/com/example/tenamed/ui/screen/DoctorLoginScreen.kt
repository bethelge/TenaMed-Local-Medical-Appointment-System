package com.example.tenamed.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tenamed.data.model.LoginRequest
import com.example.tenamed.data.network.RetrofitInstance
import com.example.tenamed.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.PopUpToBuilder
import kotlin.let
import kotlin.text.isNotEmpty


@Composable
fun DoctorLoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Observe error message changes from the ViewModel
    LaunchedEffect(authViewModel.errorMessage) {
        errorMessage = authViewModel.errorMessage ?: ""
    }

    // Observe login response and navigate if successful
    val loginResponse = authViewModel.loginResponse
    LaunchedEffect(loginResponse) {
        loginResponse?.let { response ->
            navController.navigate("doctor_profile_after_setup") {
                NavOptionsBuilder.popUpTo("doctor_login") {
                    PopUpToBuilder.inclusive = true
                } // Prevent going back to login
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Doctor Login", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Don't have an account?")
            Text(
                text = " Sign up",
                color = Color(0xFF00BCD4),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        navController.navigate("doctor_signup")
                    }
            )
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
