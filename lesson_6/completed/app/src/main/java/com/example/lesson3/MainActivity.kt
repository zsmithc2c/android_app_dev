package com.example.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lesson 2 content
        val name: String = "Alice"
        var age: Int = 25

        fun greet(person: String): String {
            return "Hello, $person!"
        }

        val adultStatus = if (age > 18) "Adult" else "Not an adult"

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            textView.text = "${greet(name)} - $adultStatus"
        }

        // Lesson 3: Navigation
        val navigateButton = findViewById<Button>(R.id.navigateButton)
        navigateButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Lesson 3: Interactive elements
        val userInput = findViewById<EditText>(R.id.userInput)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val outputText = findViewById<TextView>(R.id.outputText)

        submitButton.setOnClickListener {
            outputText.text = userInput.text.toString()
        }

        // Lesson 5: Navigate to Sign Up page
        val signUpButton = findViewById<Button>(R.id.signUpNavButton)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
