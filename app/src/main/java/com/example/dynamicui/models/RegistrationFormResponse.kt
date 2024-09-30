package com.example.dynamicui.models

data class RegistrationFormResponse(
    val statusCode: String,
    val statusMessage: String,
    val components: List<Component>
)