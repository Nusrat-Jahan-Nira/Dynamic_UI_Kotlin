//package com.example.dynamicui.ui
//
//
//import android.content.ContentValues.TAG
//import android.os.Bundle
//import android.util.Log
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import com.example.dynamicui.R
//import com.example.dynamicui.api.RetrofitClient
//import com.example.dynamicui.models.Component
//import com.example.dynamicui.models.RegistrationFormResponse
//import com.example.dynamicui.models.RegistrationRequest
//import com.example.dynamicui.models.RegistrationResponse
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class RegistrationActivity : AppCompatActivity() {
//
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var confirmPasswordEditText: EditText
//    private lateinit var dropdown: Spinner
//    private lateinit var apiDropdown: Spinner
//    private lateinit var genderRadioGroup: RadioGroup
//    private lateinit var acceptTermsCheckBox: CheckBox
//    private lateinit var subscribeNewsletterSwitch: Switch
//    private lateinit var registerButton: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)
//
//        usernameEditText = findViewById(R.id.usernameEditText)
//        passwordEditText = findViewById(R.id.passwordEditText)
//        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
//        dropdown = findViewById(R.id.dropdown)
//        apiDropdown = findViewById(R.id.apiDropdown)
//        genderRadioGroup = findViewById(R.id.genderRadioGroup)
//        acceptTermsCheckBox = findViewById(R.id.acceptTermsCheckBox)
//        subscribeNewsletterSwitch = findViewById(R.id.subscribeNewsletterSwitch)
//        registerButton = findViewById(R.id.registerButton)
//
//        fetchRegistrationForm()
//
//        registerButton.setOnClickListener {
//            registerUser()
//        }
//    }
//
//    private fun fetchRegistrationForm() {
//        RetrofitClient.instance.fetchRegistrationForm().enqueue(object : Callback<RegistrationFormResponse> {
//            override fun onResponse(call: Call<RegistrationFormResponse>, response: Response<RegistrationFormResponse>) {
//                if (response.isSuccessful) {
//                    Log.d(TAG, "Register button clicked")
//                    response.body()?.let { formResponse ->
//                        populateForm(formResponse.components)
//                    }
//                } else {
//                    Toast.makeText(this@RegistrationActivity, "Error fetching form", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<RegistrationFormResponse>, t: Throwable) {
//                Toast.makeText(this@RegistrationActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun populateForm(components: List<Component>) {
//        // Populate dropdowns and other components based on the fetched data
//        // Example for dropdown:
//        val options = components.find { it.label == "Select an Option" }?.options
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options ?: emptyList())
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        dropdown.adapter = adapter
//    }
//
//    private fun registerUser() {
//        val username = usernameEditText.text.toString()
//        val password = passwordEditText.text.toString()
//        val confirmPassword = confirmPasswordEditText.text.toString()
//        val selectedOption = "ABC"
//        val selectedAPIOption = "apiDropdown.selectedItem.toString()"
//        val gender = when (genderRadioGroup.checkedRadioButtonId) {
//            R.id.radioMale -> "Male"
//            R.id.radioFemale -> "Female"
//            else -> "Other"
//        }
//        val acceptTerms = acceptTermsCheckBox.isChecked
//        val subscribeNewsletter = subscribeNewsletterSwitch.isChecked
//
//        val registrationRequest = RegistrationRequest(
//            username, password, confirmPassword,
//            selectedOption, selectedAPIOption, gender,
//            acceptTerms, subscribeNewsletter
//        )
//
//        RetrofitClient.instance.registerUser(registrationRequest).enqueue(object : Callback<RegistrationResponse> {
//            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@RegistrationActivity, "Registration Successful!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@RegistrationActivity, "Error registering user", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
//
//                Log.e("ISSUE","${t.message}")
//                Toast.makeText(this@RegistrationActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}
