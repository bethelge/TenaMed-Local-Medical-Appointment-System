package com.example.tenamed.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun DoctorBottomNavBar(navController: NavController, selectedIndex: Int) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = selectedIndex == 0,
            onClick = {
                if (selectedIndex != 0) {
                    navController.navigate("doctor_appointments_list") {
                        popUpTo("doctor_appointments_list") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            selected = selectedIndex == 1,
            onClick = {
                if (selectedIndex != 1) {
                    navController.navigate("doctor_profile_after_setup") {
                        popUpTo("doctor_profile_after_setup") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}


