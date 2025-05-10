package com.example.tena.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.ui.draw.clip
import com.example.tenamed.data.repository.AuthRepository
import com.example.tenamed.data.repository.AuthRepository
import com.example.tenamed.data.network.SharedPrefsManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, authRepository: AuthRepository, context: Context) {
    val sharedPrefsManager = SharedPrefsManager(context)
    val scope = rememberCoroutineScope() // Create a coroutine scope for launching suspend functions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔵 Circle with user initials
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFF58CFFD)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "ET",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 👤 User name
        Text(
            text = "Eldana Tasew",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ⚙️ Settings Options
        SettingsOption(icon = Icons.Default.Settings, title = "Settings", onClick = { /* Settings action */ })
        Divider()
        SettingsOption(icon = Icons.Default.Security, title = "License and Privacy Policy", onClick = { /* License action */ })
        Divider()

        // Updated Signout button
        SettingsOption(
            icon = Icons.Default.Logout,
            title = "Signout",
            iconTint = Color(0xFF58CFFD),
            onClick = {
                // Launch a coroutine to perform the account deletion in the background
                scope.launch {
                    val token = sharedPrefsManager.getToken() ?: return@launch // Retrieve the token from shared prefs
                    val userId = 7 // Replace this with the actual logged-in user's ID
                    val response = authRepository.deleteDoctorAccount(userId, token)

                    // Check if the response was successful
                    if (response.isSuccessful) {
                        // Remove token after deletion
                        sharedPrefsManager.removeToken()

                        // After deletion, navigate to login or home screen
                        navController.navigate("login") // Replace "login" with your actual login screen route
                    } else {
                        // Handle the error (e.g., show a toast or error message)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // 🔽 Bottom Navigation
        BottomNavBar(selectedIndex = 2, navController = navController)
    }
}

@Composable
fun SettingsOption(
    icon: ImageVector,
    title: String,
    iconTint: Color = Color(0xFF58CFFD),
    onClick: () -> Unit // Ensure onClick is passed
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = iconTint)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun BottomNavBar(selectedIndex: Int, navController: NavController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = selectedIndex == 0,
            onClick = { navController.navigate("post_booking_home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Appointments") },
            selected = selectedIndex == 1,
            onClick = { navController.navigate("appointments") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            selected = selectedIndex == 2,
            onClick = { navController.navigate("profile") }
        )
    }
}
