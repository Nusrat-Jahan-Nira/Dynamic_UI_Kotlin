package com.example.dynamicui.models

data class Component(
    val type: String,
    val label: String,
    val placeholder: String?,
    val actionDetails: ActionDetails?,
    val options: List<String>?
)