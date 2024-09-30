package com.example.dynamicui.models

data class RegistrationRequest(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val selectedOption: String,
    val selectedAPIOption: String,
    val gender: String,
    val acceptTerms: Boolean,
    val subscribeNewsletter: Boolean
)