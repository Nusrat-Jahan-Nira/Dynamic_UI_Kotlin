package com.example.dynamicui.api

import com.example.dynamicui.models.RegistrationFormResponse
import com.example.dynamicui.models.RegistrationRequest
import com.example.dynamicui.models.RegistrationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("/registration-form")
    fun fetchRegistrationForm(): Call<RegistrationFormResponse>

    @POST("/register")
    fun registerUser(@Body registrationRequest: RegistrationRequest): Call<RegistrationResponse>
}