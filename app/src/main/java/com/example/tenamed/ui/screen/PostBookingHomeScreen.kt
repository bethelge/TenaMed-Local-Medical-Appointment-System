package com.example.tenamed.ui


import com.example.tenamed.data.model.Doctor
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.foundation.clickable
import com.example.tenamed.data.model.DoctorCard


@Composable
fun PostBookingHomeScreen(navController: NavController) {
    val categories = listOf("Optometrist", "Dentist", "Cardiologist", "Neurologist", "Pediatrician", "Dermatologist")
    val doctors = listOf(
        DoctorCard("Dr. Sofia Samuel", "Dentist", R.drawable.doctors),
        DoctorCard("Dr. Ezra Melaku", "Cardiologist", R.drawable.doctors),
        DoctorCard("Dr. Hana Bekele", "Optometrist", R.drawable.doctors),
    )
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val filteredDoctors = doctors.filter {
        (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.specialization.contains(searchQuery, ignoreCase = true)) &&
                (selectedCategory == null || it.specialization.equals(selectedCategory, ignoreCase = true))
    }

    Scaffold(
        bottomBar = {
            MainBottomNavBar(selectedIndex = 0, navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 🧭 Title & See All
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Upcoming Appointments", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = "See all",
                    color = Color(0xFF00BCD4),
                    modifier = Modifier.clickable {
                        navController.navigate("appointments")
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔷 Upcoming Appointment Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB2EBF2)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.doctors),
                            contentDescription = null,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Dr. Doc Doc", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Optometrist", fontSize = 14.sp, color = Color.DarkGray)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFF00BCD4))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("April 25 at 10:00 AM", fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔍 Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Let's find your doctor, Name, Specialization") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🧠 Horizontal category tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = if (selectedCategory == category) null else category
                        },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(containerColor = Color(0xFFB2EBF2))
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🩺 Doctor list
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredDoctors) { doctor ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = doctor.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(doctor.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(doctor.specialization, fontSize = 14.sp, color = Color.Gray)
                            }

                            Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainBottomNavBar(selectedIndex: Int, navController: NavController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            selected = selectedIndex == 0,
            onClick = { navController.navigate("post_booking_home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
            selected = selectedIndex == 1,
            onClick = { navController.navigate("appointments") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            selected = selectedIndex == 2,
            onClick = { navController.navigate("profile") }
        )
    }
}