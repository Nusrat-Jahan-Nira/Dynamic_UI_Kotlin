package com.example.dynamicui.models

data class ComponentApiResponse(
    val statusCode: String,
    val statusMessage: String,
    val components: List<Component>
)