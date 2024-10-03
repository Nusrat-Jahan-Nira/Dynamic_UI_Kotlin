package com.example.dynamicui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.dynamicui.api.RetrofitClient
import com.example.dynamicui.models.ActionDetails
import com.example.dynamicui.models.Component
import com.example.dynamicui.models.ComponentApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PayoneerActivity : AppCompatActivity() {
    private lateinit var container: LinearLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payoneer)


        // Initialize the Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set the initial title
        setToolbarTitle("Payoneer")
        // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Reference the container defined in the XML layout
        container = findViewById(R.id.container)
        container.setPadding(30,15,30,15)

        fetchPayoneerComponents()

    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun fetchPayoneerComponents() {
        RetrofitClient.instance.fetchPayoneerComponents().enqueue(object :
            Callback<ComponentApiResponse> {
            override fun onResponse(
                call: Call<ComponentApiResponse>,
                response: Response<ComponentApiResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.components?.forEach { component ->
                        createUIComponent(component)
                    }
                } else {
                    Toast.makeText(
                        this@PayoneerActivity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ComponentApiResponse>, t: Throwable) {
                Toast.makeText(this@PayoneerActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun createUIComponent(component: Component) {
        when (component.type) {
            "Card" -> {
                // Create a Card layout
                val cardLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    // Add some padding or margin if needed
                    setPadding(16, 16, 16, 16)
                    val roundedDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 16f // Set corner radius for rounded edges
                        setStroke(5, Color.DKGRAY) // Set the border color and width
                        //setColor(Color.GRAY)
                        setColor(Color.parseColor("#f6f6f6"))// Set background color to blue
                    }

                    // Set the background of the card layout to the rounded drawable
                    background = roundedDrawable
                    // Add margin around the card
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 16, 0, 16) // Margin around the card
                    }


                }

                // Add children components to the card
                component.children?.forEach { child ->
                    createUIComponent(child, cardLayout)
                }

                container.addView(cardLayout)

            }

            "Label" -> {
                // Create a TextView for label
                val labelTextView = TextView(this).apply {
                    text = component.label
                    // Optionally, set styling such as text size or color
                    textSize = 16f
                    setPadding(0, 0, 0, 8) // Add some bottom padding
                }
                (container as LinearLayout).addView(labelTextView)
            }


//            "TextField" -> {
//                // Create EditText for text fields
//                val editText = EditText(this).apply {
//                    hint = component.placeholder ?: component.label // Set hint text
//                    setText(component.value) // Set initial text from the component value
//                    inputType = android.text.InputType.TYPE_CLASS_TEXT // Set input type to text
//
//                    // Optional: Set text size and padding
//                    textSize = 14f // Equivalent to the Flutter TextStyle fontSize
//                    setPadding(16, 16, 16, 16) // Equivalent to the Flutter contentPadding
//
//                    // Create an outline drawable for the EditText background
//                    val drawable = GradientDrawable().apply {
//                        shape = GradientDrawable.RECTANGLE
//                        cornerRadius = 4f // Set corner radius to match Flutter's OutlineInputBorder
//                        setStroke(1, Color.GRAY) // Set the stroke color and width for the outline
//                        setColor(Color.WHITE) // Set background color
//                    }
//
//                    // Set the background of the EditText to the drawable
//                    background = drawable
//                }
//
//                // Add EditText to the container
//                container.addView(editText)
//            }

            "TextField" -> {
                // Create a vertical LinearLayout to hold the label and the EditText
                val fieldLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(10,10,10,10)
                }

                // Create the EditText for the input field
                val editText = EditText(this).apply {
                    hint = component.placeholder ?: component.label // Set hint text based on placeholder or label
                    setText(component.value) // Set initial text from the component value
                    inputType = android.text.InputType.TYPE_CLASS_TEXT // Set input type to text

                    // Optional: Set text size and padding
                    textSize = 14f // Equivalent to the Flutter TextStyle fontSize
                    setPadding(50, 50, 50, 50) // Equivalent to the Flutter contentPadding

                    // Create an outline drawable for the EditText background
                    val drawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 8f // Set corner radius to match Flutter's OutlineInputBorder
                        setStroke(2, Color.GRAY) // Set the stroke color and width for the outline
                        //setColor(Color.WHITE) // Set background color
                    }

                    // Set the background of the EditText to the drawable
                    background = drawable

                    // Set hint text color (optional)
                    setHintTextColor(Color.GRAY) // Adjust the hint color as needed
                }

                fieldLayout.addView(editText) // Then add the EditText

                // Optional: Add margins to the entire field layout
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8) // Margin around the entire field layout
                }
                fieldLayout.layoutParams = params

                // Add the entire fieldLayout to the parent container (assuming 'container' is the layout for components)
                container.addView(fieldLayout)
            }


            "Checkbox" -> {
                val checkBox = CheckBox(this).apply {
                    text = component.label
                    // Optionally set checked state based on value
                    isChecked = component.value?.toBoolean() ?: false

                }

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 15, 0, 15) // Margin around the entire field layout
                }
                checkBox.layoutParams = params

                container.addView(checkBox)
            }

            "Button" -> {
                val button = Button(this).apply {

                    text = component.label

                    setOnClickListener {
                        // Handle button click and make API call
                        submitForm(component.actionDetails)
                    }
                    isAllCaps = false
                    // Set padding inside the button to make it take up less space
                    setPadding(10, 10, 10, 10)

                    // Create a drawable with rounded edges and custom background color
                    val roundedDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 60f // Set corner radius for rounded edges // Set the border color and width
                        setColor(Color.parseColor("#FF4CAF50")) // Set background color to "bottle green"
                    }

                    // Set the background of the button to the rounded drawable
                    background = roundedDrawable
                }

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 10, 0, 10) // Reduce margins for more compact spacing
                }

                button.layoutParams = params

                container.addView(button)
            }

            else -> {
                // Handle any other component types if necessary
            }


//            "Button" -> {
//                val button = Button(this).apply {
//
//                    text = component.label
//
//                    setOnClickListener {
//                        // Handle button click and make API call
//                        submitForm(component.actionDetails)
//                    }
//
//                    setPadding(15,15,15,15)
//                    val roundedDrawable = GradientDrawable().apply {
//                        shape = GradientDrawable.RECTANGLE
//                        cornerRadius = 60f // Set corner radius for rounded edges
//                        setStroke(2, Color.DKGRAY) // Set the border color and width
//                        setColor(Color.GREEN.plus(1)) // Set background color to blue
//                    }
//
//                    // Set the background of the card layout to the rounded drawable
//                    background = roundedDrawable
//
//                }
//
//                val params = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                ).apply {
//                    setMargins(0, 15, 0, 15) // Margin around the entire field layout
//                }
//                button.layoutParams = params
//
//                container.addView(button)
//            }
//
//            else -> {
//                // Handle any other component types if necessary
//            }
        }
    }

    // Overloaded createUIComponent method to handle children
//    private fun createUIComponent(component: Component, parentLayout: LinearLayout) {
//        when (component.type) {
//            "Label" -> {
//                val labelTextView = TextView(this).apply {
//                    text = component.label
//                    textSize = 16f
//                    setPadding(0, 0, 0, 8)
//                }
//                parentLayout.addView(labelTextView)
//            }
//            // You can add more cases here for other child types if needed
//        }
//    }

    private fun createUIComponent(component: Component, parentLayout: LinearLayout) {
        when (component.type) {
            "Label" -> {
                // Create a horizontal LinearLayout to hold the label and value
                val rowLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(15,0,15,0)

                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                }

                // Create the TextView for the label
                val labelTextView = TextView(this).apply {
                    text = component.label
                    textSize = 16f
                    setPadding(0, 0, 8, 8) // Add some padding to the right of the label
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f // Weight for label
                    )
                }

                // Create the TextView for the value
                val valueTextView = TextView(this).apply {
                    text = component.value // Assuming `component` has a value property
                    textSize = 16f
                    setPadding(8, 0, 0, 8) // Add padding to the left of the value
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f // Weight for value
                    )
                }

                // Add both TextViews to the horizontal layout
                rowLayout.addView(labelTextView)
                rowLayout.addView(valueTextView)

                // Add the row layout to the parent layout
                parentLayout.addView(rowLayout)

                // Create and add the divider after the row
                val dividerView = View(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        5 // Height of the divider (adjust as needed)
                    ).apply {
                        setMargins(15, 0, 15, 0) // Add margins for spacing
                    }
                    setBackgroundColor(Color.GRAY) // Set the color of the divider
                }

                // Add the divider to the parent layout
                parentLayout.addView(dividerView)

            }
            // Add more cases here for other component types if needed
        }
    }


    private fun submitForm(actionDetails: ActionDetails?) {
        actionDetails?.let {
            // Perform the network call based on the actionDetails (url, method, etc.)
            // Display success or error message based on response
            Toast.makeText(this, it.successMessage, Toast.LENGTH_SHORT).show()
        }
    }
}