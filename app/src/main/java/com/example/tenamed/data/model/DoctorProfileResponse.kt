package com.example.tena.data.model

import com.google.gson.annotations.SerializedName

// SuccessResponse.kt
data class DoctorProfileResponse(
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("specialty") val specialty: String? = null,
    @SerializedName("yearsOfExperience") val yearsOfExperience: Int? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("hospital") val hospital: String? = null,
    @SerializedName("description") val description: String? = null,
    val message: String
)
