package com.example.tenamed.data.network


import com.example.tenamed.data.model.LoginRequest
import com.example.tenamed.data.model.LoginResponse
import com.example.tenamed.data.model.SignupRequest
import com.example.tenamed.data.model.SignupResponse

import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST





interface ApiService {


    @POST("api/doctors/register")
    suspend fun registerDoctor(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/doctors/login")
    suspend fun loginDoctor(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/patients/login")
    suspend fun loginPatient(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/patients/register")
    suspend fun registerPatient(@Body request: SignupRequest): Response<SignupResponse>






}
