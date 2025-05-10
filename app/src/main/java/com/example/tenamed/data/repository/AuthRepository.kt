package com.example.tenamed.data.repository


import com.example.tenamed.data.model.BookAppointmentRequest
import com.example.tenamed.data.model.BookAppointmentResponse
import com.example.tenamed.data.model.DoctorGetResponse
import com.example.tenamed.data.model.LoginRequest
import com.example.tenamed.data.model.LoginResponse
import com.example.tenamed.data.model.SignupRequest
import com.example.tenamed.data.model.SignupResponse
import com.example.tenamed.data.network.RetrofitInstance
import retrofit2.Response


class AuthRepository {


    suspend fun registerDoctor(request: SignupRequest): Response<SignupResponse> {
        return RetrofitInstance.api.registerDoctor(request)
    }

    // Login doctor
    suspend fun loginDoctor(request: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.api.loginDoctor(request)
    }

    suspend fun loginPatient(request: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.api.loginPatient(request)
    }

    suspend fun registerPatient(request: SignupRequest): Response<SignupResponse> {
        return RetrofitInstance.api.registerPatient(request)
    }

    suspend fun getDoctorById(id: Int): Response<DoctorGetResponse> {
        return RetrofitInstance.api.getDoctorById(id)
    }

    suspend fun getAllDoctors(): Response<List<DoctorGetResponse>> {
        return RetrofitInstance.api.getAllDoctors()
    }
    suspend fun bookAppointment(request: BookAppointmentRequest): Response<BookAppointmentResponse> {
        return RetrofitInstance.api.bookAppointment(request)

    }
    suspend fun deleteDoctorAccount(userId: Int, token: String): Response<Void> {
        return RetrofitInstance.api.deleteDoctorAccount(userId, "Bearer $token")
    }

}



