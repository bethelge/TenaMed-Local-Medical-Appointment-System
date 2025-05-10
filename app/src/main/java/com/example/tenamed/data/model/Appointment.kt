package com.example.tenamed.data.model

import com.example.tena.R

data class Appointment(
    val name: String,
    val specialty: String,
    val time: String,
    val imageRes: Int = R.drawable.doctors // Make sure this image exists
)

val sampleAppointments = listOf(
    Appointment("Dr. Sofia Samuel", "Dentist", "April 26 at 2:00 PM"),
    Appointment("Dr. Ezra Melaku", "Cardiologist", "April 27 at 11:00 AM"),
    Appointment("Dr. Hana Bekele", "Optometrist", "May 1 at 9:00 AM"),
    Appointment("Dr. Robel Girma", "Surgeon", "May 5 at 10:30 AM")
)