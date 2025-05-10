package com.example.tenamed.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tenamed.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookAppointmentPlaceholderScreen(navController: NavController) {
    val appointmentViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault()) // Correct time format (HH:mm)

    val calendar = Calendar.getInstance()
    val selectedDate = remember { mutableStateOf("") }
    val selectedTime = remember { mutableStateOf("") }
    val note = remember { mutableStateOf("") }  // State for the note

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Book Appointment", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Date picker
        Button(onClick = {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    selectedDate.value = dateFormatter.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Pick Date: ${if (selectedDate.value.isEmpty()) "Select Date" else selectedDate.value}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time picker
        Button(onClick = {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedTime.value = timeFormatter.format(calendar.time)  // Formatting the time properly
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }) {
            Text("Pick Time: ${if (selectedTime.value.isEmpty()) "Select Time" else selectedTime.value}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TextField for notes (Added to include a place to enter notes)
        TextField(
            value = note.value,
            onValueChange = { note.value = it },
            label = { Text("Note (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Appointment button
        Button(
            onClick = {
                if (selectedDate.value.isNotEmpty() && selectedTime.value.isNotEmpty()) {
                    val patientId = 4 // Hardcoded for now
                    val doctorId = 2 // Hardcoded for now

                    // Pass the note entered by the user
                    val appointmentNote = note.value

                    // Get the correctly formatted time
                    val time = timeFormatter.format(calendar.time)

                    // Call the ViewModel to book the appointment
                    appointmentViewModel.bookAppointment(patientId, doctorId, selectedDate.value, time, appointmentNote) { appointmentId ->
                        if (appointmentId != null) {
                            Toast.makeText(context, "Appointment booked with ID: $appointmentId", Toast.LENGTH_SHORT).show()
                            navController.navigate("post_booking_home")
                        } else {
                            Toast.makeText(context, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(context, "Please select both date and time", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
            Text("Confirm Appointment")
        }
    }
}
