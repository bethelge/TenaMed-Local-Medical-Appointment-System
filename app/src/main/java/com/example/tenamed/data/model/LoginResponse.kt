package com.example.tenamed.data.model

data class LoginResponse(
    val token: String,
    val message: String,
    val doctor: Doctor
)
data class Doctor(
    val id: String,
    val email: String,
    val fullName: String,
    // Add other fields as needed
)
