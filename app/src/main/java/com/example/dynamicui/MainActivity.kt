package com.example.dynamicui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dynamicui.api.RetrofitClient
import com.example.dynamicui.models.Component
import com.example.dynamicui.models.ComponentApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container = LinearLayout(this)
        container.orientation = LinearLayout.VERTICAL
        container.setPadding(5,5,5,5)

        setContentView(container)

        fetchRegistrationForm()
    }

    private fun fetchRegistrationForm() {
        RetrofitClient.instance.fetchRegistrationForm().enqueue(object : Callback<ComponentApiResponse> {
            override fun onResponse(call: Call<ComponentApiResponse>, response: Response<ComponentApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.components?.forEach { component ->
                        createUIComponent(component)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ComponentApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createUIComponent(component: Component) {
        when (component.type) {
            "text" -> {
                val editText = EditText(this).apply {
                    hint = component.placeholder ?: component.label
                }
                container.addView(editText)
            }
            "password" -> {
                val editText = EditText(this).apply {
                    hint = component.placeholder ?: component.label
                    inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                container.addView(editText)
            }
            "dropdown" -> {
                val spinner = Spinner(this)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, component.options ?: emptyList())
                spinner.adapter = adapter
                container.addView(spinner)
            }
            "radio" -> {
                val radioGroup = RadioGroup(this)
                component.options?.forEach { option ->
                    val radioButton = RadioButton(this).apply {
                        text = option
                    }
                    radioGroup.addView(radioButton)
                }
                container.addView(radioGroup)
            }
            "checkbox" -> {
                val checkBox = CheckBox(this).apply {
                    text = component.label
                }
                container.addView(checkBox)
            }
            "switch" -> {
                val switch = Switch(this).apply {
                    text = component.label
                }
                container.addView(switch)
            }
            "elevated_button", "text_button" -> {
                val button = Button(this).apply {
                    text = component.label
                    setOnClickListener {
                        // Handle button click
                        
                        Toast.makeText(this@MainActivity, "Button clicked!", Toast.LENGTH_SHORT).show()
                    }
                }
                container.addView(button)
            }
        }
    }
}
