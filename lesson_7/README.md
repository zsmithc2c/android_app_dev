# Lesson 7: Continuing Our Apps

## Overview

In Lesson 7, you will consolidate the progress on your app by verifying that sign-up and login work correctly, redesigning your home page (MainActivity) as a proper landing screen, and applying consistent XML styling across all pages. By the end of this lesson, your app will have a polished, cohesive look and feel.

## Learning Objectives

- Verify and troubleshoot sign-up and login functionality
- Navigate from the sign-in page to the home page using Intents
- Redesign the home page (MainActivity) with a welcome message and navigation
- Apply consistent XML styling across all app pages
- Understand the importance of user profile sections (coming in a future lesson)

## Prerequisites

- Completed Lesson 6 (Firebase Authentication)
- Working sign-up and login with Firebase
- Your `google-services.json` file configured in your project

---

## Step 1: Verify Sign-Up and Login Functionality

Before making any changes, confirm that your existing Firebase authentication is working.

### 1.1 Test Sign-Up

1. Run your app in Android Studio
2. Navigate to the Sign Up page
3. Create a new account with a valid email and a password longer than 6 characters
4. You should see the Toast message **"Account created successfully!"** and be redirected to the Login page

### 1.2 Verify on Firebase Console

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Open your project
3. Navigate to **Authentication** > **Users**
4. Confirm that the user you just created appears in the list

### 1.3 Test Login

1. On the Login page, enter the credentials you just created
2. Tap **Log In**
3. You should be navigated to **MainActivity** (the home page)

> **Troubleshooting:** If sign-up or login fails, double-check:
> - Your `google-services.json` is in the `app/` folder
> - Email/Password authentication is enabled in Firebase Console
> - Your device/emulator has internet access
> - Your email format is valid and password is longer than 6 characters

---

## Step 2: Understanding Explicit Intents for Navigation

Your app already uses Intents to navigate between activities. Let's review how this works.

### What are Explicit Intents?

Explicit Intents in Android are used to start a specific activity within your own application. They control the navigation flow of your app's user interface.

### How They Work

When a user successfully logs in, an Intent transitions them from `LoginActivity` to `MainActivity`:

```kotlin
val intent = Intent(this, MainActivity::class.java)
startActivity(intent)
finish()
```

- `Intent(this, MainActivity::class.java)` - Creates an Intent specifying the current activity as context and `MainActivity` as the destination
- `startActivity(intent)` - Launches the target activity
- `finish()` - Closes the current activity so the user can't press "Back" to return to the login screen

This pattern is already in your `LoginActivity.kt` from Lesson 6. The same pattern is used throughout the app whenever you navigate between screens.

---

## Step 3: Redesign the Home Page Layout

Now we'll transform `activity_main.xml` from the lesson demo layout into a proper home page. The home page is the first screen users see after logging in, so it should be clean and welcoming.

### 3.1 Open `activity_main.xml`

Open `app/src/main/res/layout/activity_main.xml` in Android Studio.

### 3.2 Replace the Layout

Replace the entire contents of the file with the following styled layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="24dp">

    <TextView
        android:id="@+id/welcomeMessage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to My App"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:fontFamily="sans-serif-medium"
        android:layout_marginTop="48dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/homeSubtitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="You are signed in"
        android:textSize="16sp"
        android:textColor="#666666"
        android:layout_marginTop="8dp"
        app:layout_constraintTop_toBottomOf="@id/welcomeMessage"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/navigationButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Go to Settings"
        android:textSize="16sp"
        android:padding="12dp"
        android:layout_marginTop="48dp"
        android:backgroundTint="#6200EE"
        app:layout_constraintTop_toBottomOf="@id/homeSubtitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/logoutButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Log Out"
        android:textSize="16sp"
        android:padding="12dp"
        android:layout_marginTop="16dp"
        android:backgroundTint="#B00020"
        app:layout_constraintTop_toBottomOf="@id/navigationButton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 3.3 Understanding the New Layout

Let's break down what changed and why:

| Element | Purpose |
|---------|---------|
| `android:background="#F5F5F5"` | Light gray background matching sign-up and login pages |
| `android:padding="24dp"` | Consistent padding around all content |
| `welcomeMessage` TextView | Large, bold title welcoming the user |
| `homeSubtitle` TextView | Smaller subtitle showing sign-in status |
| `navigationButton` | Styled purple button to navigate to Settings |
| `logoutButton` | Red button for logging out |

**Key styling properties used:**
- `android:textSize` - Controls font size (28sp for titles, 16sp for body text)
- `android:textStyle="bold"` - Makes text bold
- `android:textColor` - Sets text color (#333333 dark for titles, #666666 gray for subtitles)
- `android:fontFamily="sans-serif-medium"` - Sets a clean, modern font
- `android:backgroundTint` - Colors the button background (#6200EE purple, #B00020 red)

---

## Step 4: Update MainActivity.kt

Now update the Kotlin code to match the new layout. We'll add an authentication check, display the user's email in the welcome message, and wire up the logout button.

### 4.1 Open `MainActivity.kt`

Open `app/src/main/java/com/example/lesson3/MainActivity.kt` (your package name may differ).

### 4.2 Replace the Code

Replace the entire contents with the following:

```kotlin
package com.example.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        // If user is not logged in, redirect to LoginActivity
        if (firebaseAuth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Welcome message - display the user's email
        val welcomeMessage = findViewById<TextView>(R.id.welcomeMessage)
        welcomeMessage.text = "Welcome, ${firebaseAuth.currentUser?.email}"

        // Navigation to Settings
        val navigationButton = findViewById<Button>(R.id.navigationButton)
        navigationButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Logout
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
```

### 4.3 Understanding the Code Changes

**Authentication check at the top of `onCreate`:**
```kotlin
if (firebaseAuth.currentUser == null) {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
    return
}
```
- `firebaseAuth.currentUser` returns `null` if no user is logged in
- If no user is found, we redirect to `LoginActivity` using an Intent
- `finish()` closes `MainActivity` so the user can't press "Back" to bypass login
- `return` stops the rest of `onCreate` from running

**Dynamic welcome message:**
```kotlin
val welcomeMessage = findViewById<TextView>(R.id.welcomeMessage)
welcomeMessage.text = "Welcome, ${firebaseAuth.currentUser?.email}"
```
- Gets the currently signed-in user's email from Firebase
- Sets the welcome message text to include their email address
- The `?.` (safe call operator) prevents a crash if the user is somehow null

**Logout functionality:**
```kotlin
val logoutButton = findViewById<Button>(R.id.logoutButton)
logoutButton.setOnClickListener {
    firebaseAuth.signOut()
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
}
```
- `firebaseAuth.signOut()` signs the user out of Firebase
- Then navigates back to `LoginActivity`
- `finish()` closes the home page

**Navigation to Settings:**
```kotlin
val navigationButton = findViewById<Button>(R.id.navigationButton)
navigationButton.setOnClickListener {
    val intent = Intent(this, SettingsActivity::class.java)
    startActivity(intent)
}
```
- Uses an Explicit Intent to navigate to `SettingsActivity`
- No `finish()` here because we want the user to come back to the home page

---

## Step 5: Style the Settings Page

Let's apply the same consistent styling to the Settings page.

### 5.1 Open `activity_settings.xml`

Open `app/src/main/res/layout/activity_settings.xml`.

### 5.2 Replace the Layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="24dp">

    <TextView
        android:id="@+id/settingsTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Settings"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:fontFamily="sans-serif-medium"
        android:layout_marginTop="48dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/settingsSubtitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Manage your preferences"
        android:textSize="16sp"
        android:textColor="#666666"
        android:layout_marginTop="8dp"
        app:layout_constraintTop_toBottomOf="@id/settingsTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/returnButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Back to Home"
        android:textSize="16sp"
        android:padding="12dp"
        android:layout_marginTop="48dp"
        android:backgroundTint="#6200EE"
        app:layout_constraintTop_toBottomOf="@id/settingsSubtitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

This follows the same styling pattern: light gray background, title with subtitle, and a styled button.

---

## Step 6: Review Styling Consistency Across All Pages

Your app now has four main pages. Let's review the consistent styling pattern used across all of them:

### Styling Checklist

| Property | Value | Applied To |
|----------|-------|------------|
| Background color | `#F5F5F5` (light gray) | All pages |
| Padding | `24dp` | All page root layouts |
| Title text size | `28sp` | All page titles |
| Title text color | `#333333` (dark gray) | All page titles |
| Title font | `sans-serif-medium` | All page titles |
| Title style | `bold` | All page titles |
| Subtitle text size | `16sp` | All subtitles |
| Subtitle text color | `#666666` (medium gray) | All subtitles |
| Button text size | `16sp` | All buttons |
| Button tint | `#6200EE` (purple) | Primary action buttons |
| Button tint | `#B00020` (red) | Destructive action buttons (logout) |
| Link text color | `#6200EE` (purple) | Clickable text links |
| Input padding | `16dp` | All EditText fields |
| Input text size | `16sp` | All EditText fields |

### Why Consistency Matters

- **Professional appearance** - A consistent design makes your app look polished and complete
- **User experience** - Users learn the visual patterns quickly and can navigate intuitively
- **Brand identity** - Consistent colors and fonts create a recognizable identity for your app

### Tips for Your Own Styling

- Pick **2-3 colors** for your app and use them consistently (a primary color, a text color, and a background color)
- Use the same **font family** across all pages
- Keep **spacing and margins** consistent (24dp margins, 16dp padding for inputs)
- Use **contrast** to make important elements stand out (bold titles, colored buttons)
- **Test on a device** regularly to see how your styling looks in practice

---

## Step 7: Test Your Updated App

Run your app and test the complete flow:

1. **App launch** - Since no user is logged in initially, you should be redirected to the Login page
2. **Navigate to Sign Up** - Tap "Don't have an account? Sign up" to go to the Sign Up page
3. **Create an account** - Enter a valid email and password, tap Sign Up
4. **Log in** - Enter the same credentials on the Login page
5. **Home page** - You should see "Welcome, your@email.com" with the styled home page
6. **Navigate to Settings** - Tap "Go to Settings" and verify the styled Settings page
7. **Return home** - Tap "Back to Home" on the Settings page
8. **Log out** - Tap "Log Out" and verify you're redirected to the Login page
9. **Re-launch** - Close and reopen the app; if you previously logged out, you should see the Login page

> **Note:** If you log in and then close the app without logging out, Firebase remembers your session. The next time you open the app, you'll go straight to the home page without needing to log in again.

---

## Step 8: Planning Ahead - User Profile Section

In the next lesson, we will focus on creating a **user profile section**. Start thinking about:

- **What information should the profile include?** (name, email, profile picture, bio)
- **Where should the profile be accessible from?** (settings page, navigation menu, home page)
- **What actions should users be able to take?** (edit profile, change password, delete account)

The user profile section is essential for most apps because it:
- Provides a space for **personalization and settings**
- Includes a **logout option** (which we've already implemented on the home page)
- Helps users manage their **account information**
- Creates a more **engaging and personal experience**

---

## Summary of Changes

### Files Modified (from starter to completed)
| File | What Changed |
|------|-------------|
| `activity_main.xml` | Redesigned as styled home page with welcome message, navigation button, and logout button |
| `MainActivity.kt` | Added Firebase auth check, dynamic welcome message, Settings navigation, and logout functionality |
| `activity_settings.xml` | Styled with consistent background, title, subtitle, and button |
| `activity_sign_up.xml` | Enhanced styling with `fontFamily` and `textSize` on inputs for consistency |
| `activity_login.xml` | Enhanced styling with `fontFamily`, `textSize` on inputs, and updated hint to "Email" |

### Key Concepts Covered
- **Explicit Intents** for navigating between activities
- **Firebase Auth state checking** (`firebaseAuth.currentUser`)
- **XML styling properties**: `textSize`, `textColor`, `textStyle`, `fontFamily`, `backgroundTint`, `background`, `padding`, `layout_margin`
- **Consistent design** across multiple app screens
- **Logout functionality** using `firebaseAuth.signOut()`
