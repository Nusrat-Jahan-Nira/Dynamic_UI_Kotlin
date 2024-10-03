package com.example.dynamicui.models

//data class Component(
//    val type: String,
//    val label: String,
//    val placeholder: String?,
//    val actionDetails: ActionDetails?,
//    val options: List<String>?
//)

data class Component(
    val type: String,
    val label: String,
    val placeholder: String?,
    val value: String?,                  // Added to hold input value
    val actionDetails: ActionDetails?,   // Holds action details for buttons
    val options: List<String>? = null,   // List of options for dropdowns or radio buttons
    val children: List<Component>? = null // Added for nested components like Labels inside Cards
)
