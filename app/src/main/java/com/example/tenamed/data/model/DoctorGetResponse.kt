package com.example.tena.data.model

data class DoctorGetResponse(
    val id: Int,
    val email: String,
    val full_name: String,
    val years_of_experience: Int,
    val short_description: String,
    val location: String,
    val specialty: String,
    val language: String,
    val hospital: String
)


