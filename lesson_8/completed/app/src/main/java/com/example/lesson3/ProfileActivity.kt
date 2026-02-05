package com.example.lesson3

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        // Obtain an instance of the Firebase Realtime Database
        database = FirebaseDatabase.getInstance()
        // Reference a specific location in the database
        myRef = database.getReference("message")

        // Write a test message to the database
        myRef.setValue("Hello, World!")

        // Profile fields
        val fullNameInput = findViewById<EditText>(R.id.editTextFullName)
        val usernameInput = findViewById<EditText>(R.id.editTextProfileUsername)
        val emailInput = findViewById<EditText>(R.id.editTextProfileEmail)
        val profileImage = findViewById<ImageView>(R.id.profileImageView)

        // Auto-fill email from Firebase Auth if available
        val user = firebaseAuth.currentUser
        if (user != null) {
            emailInput.setText(user.email)
        }
    }
}
