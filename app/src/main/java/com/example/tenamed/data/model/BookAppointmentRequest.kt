package com.example.tenamed.data.model


data class BookAppointmentRequest(
    val patientId: Int,
    val doctorId: Int,
    val date: String,
    val time: String,
    val note: String
)