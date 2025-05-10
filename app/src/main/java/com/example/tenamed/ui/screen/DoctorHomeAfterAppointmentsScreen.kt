package com.example.tenamed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController

data class Appointment(
    val patientName: String,
    val time: String,
    val status: String
)

val dummyAppointments = listOf(
    Appointment("Abel Tesfaye", "April 30, 10:00 AM", "Confirmed"),
    Appointment("Sara Hailu", "April 30, 11:30 AM", "Pending"),
    Appointment("Miki Girma", "May 1, 9:00 AM", "Confirmed")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorHomeAfterAppointmentsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Today's Appointments") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            DoctorBottomNavBar(navController = navController, selectedIndex = 0)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {


            LazyColumn {
                items(dummyAppointments) { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onDetailsClick = {
                            navController.navigate("doctor_appointment_detail") // ✅ This is where navController is used
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

        }
    }
}


@Composable
fun AppointmentCard(appointment: Appointment, onDetailsClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(appointment.patientName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(appointment.time, fontSize = 14.sp, color = Color.DarkGray)
            Text("Status: ${appointment.status}", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDetailsClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
            ) {
                Text("Appointment Details")
            }
        }
    }
}

//@Composable
//fun AppointmentListScreen(navController: NavController, appointments: List<Appointment>) {
//    LazyColumn(
//        modifier = Modifier.padding(16.dp)
//    ) {
//        items(appointments) { appointment ->
//            AppointmentCard(appointment = appointment, onDetailsClick = {
//                navController.navigate("doctor_appointment_detail")
//            })
//
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//    }
//}
