package com.example.tenamed.data.model

data class SignupRequest(
    val email: String,
    val password: String,
    val confirmPassword: String
)

