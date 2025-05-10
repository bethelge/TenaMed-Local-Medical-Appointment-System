package com.example.tenamed.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenamed.data.model.*
import com.example.tenamed.data.repository.AuthRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var doctorList by mutableStateOf<List<DoctorGetResponse>>(emptyList())
        private set

    var loginResponse by mutableStateOf<LoginResponse?>(null)
        private set

    var signupResponse by mutableStateOf<SignupResponse?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var doctorId by mutableStateOf<String?>(null)
        private set

    val timeFormat = SimpleDateFormat("h:mm a", Locale.US)
    val formattedTime = timeFormat.format(Date())

    fun updateDoctorId(id: String) {
        doctorId = id
    }

    fun signup(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerDoctor(SignupRequest(email, password, confirmPassword))
                if (response.isSuccessful) {
                    signupResponse = response.body()
                } else {
                    errorMessage = "Signup failed: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginDoctor(LoginRequest(email, password))
                if (response.isSuccessful) {
                    loginResponse = response.body()
                } else {
                    errorMessage = "Login failed: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun signupP(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerPatient(SignupRequest(email, password, confirmPassword))
                if (response.isSuccessful) {
                    signupResponse = response.body()
                } else {
                    errorMessage = "Signup failed: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun loginP(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginPatient(LoginRequest(email, password))
                if (response.isSuccessful) {
                    loginResponse = response.body()
                } else {
                    errorMessage = "Login failed: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.localizedMessage}"
            }
        }
    }


    fun getDoctorById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getDoctorById(id)
                if (!response.isSuccessful) {
                    errorMessage = "Failed to load profile: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            }
        }
    }

    fun fetchAllDoctors() {
        viewModelScope.launch {
            try {
                val response = repository.getAllDoctors()
                if (response.isSuccessful) {
                    doctorList = response.body() ?: emptyList()
                } else {
                    errorMessage = "Failed to fetch doctors: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching doctors: ${e.localizedMessage}"
            }
        }
    }

    fun bookAppointment(
        patientId: Int,
        doctorId: Int,
        date: String,
        time: String,
        note: String,
        onResult: (Int?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("BookAppointment", "Patient ID: $patientId, Doctor ID: $doctorId, Date: $date, Time: $time, Note: $note")

                val request = BookAppointmentRequest(patientId, doctorId, date, time, note)
                val response = repository.bookAppointment(request)
                val loginState = mutableStateOf<Result<LoginResponse>?>(null)


                if (response.isSuccessful) {
                    val appointmentId = response.body()?.appointmentId
                    if (appointmentId != null) {
                        Log.d("BookAppointment", "Appointment booked successfully with ID: $appointmentId")
                        onResult(appointmentId)  // Pass the valid appointment ID
                    } else {
                        Log.e("Appointment", "Appointment booking succeeded but no appointment ID returned")
                        onResult(null)  // If no ID is returned, return null
                    }
                } else {
                    Log.e("Appointment", "Booking failed: ${response.code()} - ${response.message()}")
                    onResult(null)  // Booking failed, return null
                }
            } catch (e: Exception) {
                Log.e("Appointment", "Exception: ${e.localizedMessage}")
                onResult(null)  // Exception occurred, return null
            }
        }
    }
    fun deleteAccount(userId: Int, token: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.deleteDoctorAccount(userId, token)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Failed to delete account: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Exception: ${e.localizedMessage}")
            }
        }
    }


}
