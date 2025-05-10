package com.example.tenamed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.tenamed.R


@Composable
fun DoctorProfileScreenAfterSetup(navController: NavController) {
    Scaffold(
        bottomBar = {
            DoctorBottomNavBar(navController = navController, selectedIndex = 1)
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Doctor Profile Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F8FF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.doctors),
                        contentDescription = "Doctor Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Dr. Doc Roc", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Optometrist", fontSize = 14.sp)
                        Text("Works at St. Paul", color = Color(0xFF00BCD4), fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.Black
                            )
                            Text("Addis Ababa", fontSize = 14.sp)
                        }
                    }

                    Text(
                        text = "edit",
                        color = Color(0xFF00BCD4),
                        modifier = Modifier.clickable { navController.navigate("doctor_profile") }
                    )
                }
            }

            // Notifications section
            Text(
                text = "Notifications ▼",
                color = Color(0xFF00BCD4),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        } // This closes the Column
    } // This closes the Scaffold
}

