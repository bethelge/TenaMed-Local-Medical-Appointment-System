package com.example.tena.data.network

import com.example.tenamed.data.model.BookAppointmentRequest
import com.example.tenamed.data.model.BookAppointmentResponse
import com.example.tenamed.data.model.DoctorGetResponse
import com.example.tenamed.data.model.DoctorProfileRequest
import com.example.tenamed.data.model.DoctorProfileResponse
import com.example.tenamed.data.model.LoginRequest
import com.example.tenamed.data.model.LoginResponse
import com.example.tenamed.data.model.SignupRequest
import com.example.tenamed.data.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

import retrofit2.http.DELETE
import retrofit2.http.Header


interface ApiService {


    @POST("api/doctors/register")
    suspend fun registerDoctor(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/doctors/login")
    suspend fun loginDoctor(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/patients/login")
    suspend fun loginPatient(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/patients/register")
    suspend fun registerPatient(@Body request: SignupRequest): Response<SignupResponse>
    @PUT("api/doctors/doctor/{id}")
    suspend fun updateDoctorProfile(
        @Path("id") doctorId: Int,
        @Body updateRequest: DoctorProfileRequest
    ): Response<DoctorProfileResponse>
    @GET("api/doctors/{id}")
    suspend fun getDoctorById(@Path("id") id: Int): Response<DoctorGetResponse>

    @GET("api/doctors")
    suspend fun getAllDoctors(): Response<List<DoctorGetResponse>>

    @POST("api/appointments/book")
    suspend fun bookAppointment(
        @Body request: BookAppointmentRequest
    ): Response<BookAppointmentResponse>

    @DELETE("api/doctors/{id}")
    suspend fun deleteDoctorAccount(
        @Path("id") doctorId: Int,
        @Header("Authorization") token: String
    ): Response<Void>



}
