package com.example.tenamed.data.model

data class DoctorProfileRequest(
    val fullName: String,
    val location: String,
    val specialty: String,
    val yearsOfExperience: Int,
    val language: String,
    val hospital: String,
    val description: String
)
