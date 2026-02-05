# Lesson 6: Sign-Up / Login + User Authentication with Firebase

## Lesson Overview

In this lesson you will make your sign-up and login pages **functional** by integrating Firebase Authentication. You will set up a Firebase project, connect it to your Android app, and implement real user registration and login using email and password. You will also add input validation to ensure users provide proper email formats and strong passwords.

> **Note:** This lesson is content-heavy. It is completely normal to encounter errors or get stuck during Firebase setup. The next lesson provides time to regroup and make sure everyone is caught up.

## Learning Objectives

- Understand the process and importance of using Firebase for user authentication
- Implement sign-up functionality with validation, allowing new users to register
- Implement sign-in functionality, allowing users to access their accounts securely

## What You Need Before Starting

You should have a working Android project from Lesson 5 with:
- `SignUpActivity` with username, email, and password fields + sign-up button + login link
- `LoginActivity` with username and password fields + login button + sign-up link
- Navigation between sign-up and login pages using Intents
- Both activities registered in `AndroidManifest.xml`

The **starter/** folder contains this exact starting point (Lesson 5 completed code).
The **completed/** folder contains the finished state after all steps below are done.

> **Note:** The code uses the package name `com.example.lesson3`. Your project may use a different package name. Wherever you see `com.example.lesson3`, substitute your own package name.

> **Important (GitHub users):** The `google-services.json` file is not included in this repo because it contains Firebase project credentials unique to each student. If you downloaded this code from GitHub, you must add your own `google-services.json` file to the `app/` directory before building. You can download it from your [Firebase Console](https://console.firebase.google.com/) under **Project Settings > General > Your apps > google-services.json**.

---

## Step-by-Step Guide

---

### Step 1: Create a Firebase Account and Project

Firebase is a platform developed by Google that provides backend services like user authentication, databases, and analytics. We will use it to handle user registration and login.

#### 1a. Go to the Firebase Console

1. Open your web browser and go to [https://console.firebase.google.com/](https://console.firebase.google.com/)
2. Sign in with your Google account (the same one you use for Android Studio if possible).

#### 1b. Create a New Firebase Project

1. Click **"Create a project"** (or "Add project").
2. Enter a project name (e.g., "My Android App" or your app's name).
3. You can disable Google Analytics for now (it's optional for this lesson).
4. Click **"Create project"**.
5. Wait for the project to be created, then click **"Continue"**.

You should now see your Firebase project dashboard.

---

### Step 2: Enable Email/Password Authentication

1. In the Firebase console, look at the left sidebar and click **"Build"** > **"Authentication"**.
2. Click **"Get started"** if this is your first time.
3. Click on the **"Sign-in method"** tab.
4. Find **"Email/Password"** in the list of providers.
5. Click on it, then toggle the **"Enable"** switch to ON.
6. Click **"Save"**.

This tells Firebase that your app will use email and password for authentication.

---

### Step 3: Connect Your Android App to Firebase

There are two ways to do this. We recommend using the **Android Studio Firebase Assistant** (method A), but you can also do it manually (method B).

#### Method A: Using the Firebase Assistant in Android Studio (Recommended)

1. In Android Studio, go to **Tools** > **Firebase** in the menu bar.
2. A Firebase Assistant panel will open on the right side.
3. Click **"Authentication"** to expand it.
4. Click **"Authenticate using a custom authentication system"** or **"Authenticate using email and password"**.
5. Click **"Connect to Firebase"**.
6. A browser window will open. Select your Firebase project (the one you just created).
7. Click **"Connect"**.
8. Android Studio will automatically:
   - Add the `google-services.json` file to your `app/` directory
   - Add the Google Services plugin to your Gradle files

9. Back in the Firebase Assistant, click **"Add Firebase Authentication to your app"**.
10. Accept the changes when prompted.
11. Click **"Sync Now"** when the yellow bar appears at the top of the editor.

#### Method B: Manual Setup

If the Firebase Assistant doesn't work, you can set it up manually:

##### 3b-1. Register your Android app in Firebase

1. In the Firebase console, click the **Android icon** on the project overview page.
2. Enter your **Android package name** (e.g., `com.example.lesson3`). You can find this in your `app/build.gradle.kts` file under `applicationId`.
3. (Optional) Enter a nickname for your app.
4. Click **"Register app"**.

##### 3b-2. Download `google-services.json`

1. Click **"Download google-services.json"**.
2. Move the downloaded file into your project's **`app/`** directory (not the root project directory, but inside the `app/` folder).

> **Important:** The `google-services.json` file contains your Firebase project's configuration. It is specific to YOUR project and should NOT be shared publicly or committed to a public GitHub repo.

##### 3b-3. Add the Google Services plugin

Open your **project-level** `build.gradle.kts` (the one in the root of your project, NOT in the `app/` folder) and add the Google Services plugin:

```kotlin
// Top-level build file
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false  // <-- ADD THIS LINE
}
```

##### 3b-4. Add the plugin and dependencies to the app-level build file

Open your **app-level** `app/build.gradle.kts` and make these changes:

**Add the plugin at the top:**
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)  // <-- ADD THIS LINE
}
```

**Add Firebase dependencies:**
```kotlin
dependencies {
    // ... existing dependencies ...
    implementation(platform(libs.firebase.bom))  // <-- ADD THIS
    implementation(libs.firebase.auth)            // <-- ADD THIS
}
```

##### 3b-5. Add version catalog entries

Open `gradle/libs.versions.toml` and add:

**In the `[versions]` section:**
```toml
googleServices = "4.4.2"
firebaseBom = "33.7.0"
```

**In the `[libraries]` section:**
```toml
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
```

**In the `[plugins]` section:**
```toml
google-services = { id = "com.google.gms.google-services", version.ref = "googleServices" }
```

##### 3b-6. Sync your project

Click **"Sync Now"** on the yellow bar that appears, or go to **File** > **Sync Project with Gradle Files**.

#### What is the Firebase BOM?

The **BOM** (Bill of Materials) is a special dependency that manages the versions of all Firebase libraries for you. When you use `platform(libs.firebase.bom)`, you don't need to specify a version for `firebase-auth` -- the BOM ensures all Firebase libraries use compatible versions.

---

### Step 4: Enable View Binding

View binding is a feature that generates a binding class for each XML layout file. Instead of using `findViewById()` repeatedly, you access views directly through the binding object. This is safer (no runtime crashes from wrong IDs) and more concise.

#### 4a. Enable view binding in build.gradle.kts

Open `app/build.gradle.kts` and add the `buildFeatures` block inside the `android` block:

```kotlin
android {
    // ... existing configuration ...

    buildFeatures {
        viewBinding = true   // <-- ADD THIS BLOCK
    }
}
```

Click **"Sync Now"**.

#### 4b. How view binding works

For each XML layout file, Android generates a binding class:

| Layout File | Generated Binding Class |
|---|---|
| `activity_sign_up.xml` | `ActivitySignUpBinding` |
| `activity_login.xml` | `ActivityLoginBinding` |
| `activity_main.xml` | `ActivityMainBinding` |

The field names in the binding class come from the `android:id` values in your XML, converted to camelCase:

| XML ID | Binding Field |
|---|---|
| `@+id/editTextEmail` | `binding.editTextEmail` |
| `@+id/signUpButton` | `binding.signUpButton` |
| `@+id/loginLink` | `binding.loginLink` |

#### Before (using `findViewById`):
```kotlin
setContentView(R.layout.activity_sign_up)
val emailInput = findViewById<EditText>(R.id.editTextEmail)
val signUpButton = findViewById<Button>(R.id.signUpButton)
```

#### After (using view binding):
```kotlin
binding = ActivitySignUpBinding.inflate(layoutInflater)
setContentView(binding.root)
// Now use binding.editTextEmail, binding.signUpButton, etc. directly
```

---

### Step 5: Update SignUpActivity with Firebase Authentication

Open `SignUpActivity.kt` and replace its entire contents with:

```kotlin
package com.example.lesson3  // <-- Use YOUR package name

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lesson3.databinding.ActivitySignUpBinding  // <-- Use YOUR package name
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.signUpLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (!isValidEmail(email)) {
                    Toast.makeText(this, "Invalid Email Format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!isPasswordStrong(password)) {
                    Toast.makeText(this, "Password must be more than 6 characters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
    }

    private fun isPasswordStrong(password: String): Boolean {
        return password.length > 6
    }
}
```

#### What changed from Lesson 5 and why:

**1. View binding replaces `findViewById`:**
```kotlin
// OLD: setContentView(R.layout.activity_sign_up)
// NEW:
binding = ActivitySignUpBinding.inflate(layoutInflater)
setContentView(binding.root)
```
The `inflate()` call creates the binding object. `binding.root` is the root view of the layout.

**2. `private lateinit var firebaseAuth: FirebaseAuth`:**
This declares a variable to hold the Firebase authentication instance. `lateinit` means we will initialize it later (in `onCreate`).

**3. `firebaseAuth = FirebaseAuth.getInstance()`:**
Gets the Firebase authentication instance that handles all auth operations.

**4. Validation functions:**

`isValidEmail()` uses a **regular expression** (regex) to check if the email matches a standard format like `user@domain.com`:
```kotlin
private fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
}
```
- `[a-zA-Z0-9._-]+` -- one or more letters, numbers, dots, underscores, or hyphens (the part before `@`)
- `@` -- literal `@` symbol
- `[a-z]+` -- one or more lowercase letters (the domain name)
- `\\.+` -- one or more dots (the `.` in `.com`)
- `[a-z]+` -- one or more lowercase letters (the top-level domain like `com`)

`isPasswordStrong()` checks that the password is longer than 6 characters:
```kotlin
private fun isPasswordStrong(password: String): Boolean {
    return password.length > 6
}
```

**5. `firebaseAuth.createUserWithEmailAndPassword(email, password)`:**
This is the Firebase method that actually creates a new user account. It returns a `Task` that we handle with `.addOnCompleteListener`:
- **Success:** Show a toast message, navigate to login page
- **Failure:** Show the error message from Firebase (e.g., "email already in use", "weak password")

**6. `Toast.makeText(...).show()`:**
A Toast is a small popup message that appears briefly on screen. It's used to give feedback to the user.

**7. `return@setOnClickListener`:**
This is a labeled return in Kotlin. Since we're inside a lambda (the click listener), a plain `return` would try to return from `onCreate`. The label `@setOnClickListener` tells Kotlin to return from just the lambda, like an early exit.

---

### Step 6: Update LoginActivity with Firebase Authentication

Open `LoginActivity.kt` and replace its entire contents with:

```kotlin
package com.example.lesson3  // <-- Use YOUR package name

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lesson3.databinding.ActivityLoginBinding  // <-- Use YOUR package name
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.loginLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.editTextLoginUsername.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpLink.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
```

#### What this code does:

The login activity follows the same pattern as sign-up, but uses `signInWithEmailAndPassword` instead of `createUserWithEmailAndPassword`:

1. **Initialize view binding and Firebase** -- same pattern as SignUpActivity.
2. **`firebaseAuth.signInWithEmailAndPassword(email, password)`** -- attempts to log in an existing user with the given credentials.
3. **On success:** Navigate to `MainActivity` and call `finish()` so the user can't press "back" to return to the login screen.
4. **On failure:** Show the error message (e.g., "wrong password", "user not found").
5. **Empty fields check:** Show a toast if either field is empty.

---

### Step 7: Verify Your Build Files

Make sure all your Gradle files are updated correctly. Here's a checklist:

#### `gradle/libs.versions.toml` should include:
- [ ] `googleServices = "4.4.2"` in `[versions]`
- [ ] `firebaseBom = "33.7.0"` in `[versions]`
- [ ] `firebase-bom` library in `[libraries]`
- [ ] `firebase-auth` library in `[libraries]`
- [ ] `google-services` plugin in `[plugins]`

#### Project-level `build.gradle.kts` should include:
- [ ] `alias(libs.plugins.google.services) apply false`

#### App-level `app/build.gradle.kts` should include:
- [ ] `alias(libs.plugins.google.services)` in the plugins block
- [ ] `buildFeatures { viewBinding = true }` in the android block
- [ ] `implementation(platform(libs.firebase.bom))` in dependencies
- [ ] `implementation(libs.firebase.auth)` in dependencies

#### `app/google-services.json` should exist:
- [ ] The `google-services.json` file is in your `app/` directory (NOT in the root project directory)

After verifying, click **"Sync Now"** or go to **File** > **Sync Project with Gradle Files**.

---

### Step 8: Run and Test Your App

1. Click the **Run** button (green play icon) or press **Shift + F10**.
2. Navigate to the **Sign Up** page.

#### Test Sign-Up:
1. Try signing up with an **empty email** -- you should see "Email and Password cannot be empty".
2. Try signing up with an **invalid email** (e.g., "notanemail") -- you should see "Invalid Email Format".
3. Try signing up with a **short password** (e.g., "123") -- you should see "Password must be more than 6 characters".
4. Sign up with a **valid email and password** (e.g., `test@example.com` / `password123`) -- you should see "Account created successfully!" and be navigated to the login page.

#### Test Login:
1. Try logging in with an **empty email** -- you should see "Email and Password cannot be empty".
2. Try logging in with **wrong credentials** -- you should see "Login Failed: ..." with an error message.
3. Log in with the **email and password you just registered** -- you should be navigated to `MainActivity`.

#### Verify in Firebase Console:
1. Go back to your Firebase console in the browser.
2. Click **"Authentication"** in the sidebar.
3. Click the **"Users"** tab.
4. You should see the email address you just registered!

---

### Troubleshooting Common Issues

#### "No matching client found for package name"
- Your `applicationId` in `app/build.gradle.kts` must match the package name you registered in Firebase.
- Make sure `google-services.json` is in the `app/` directory.

#### "Default FirebaseApp is not initialized"
- Make sure the `google-services` plugin is applied in both build files.
- Make sure `google-services.json` is in the `app/` directory.
- Try **Build** > **Clean Project**, then **Build** > **Rebuild Project**.

#### Gradle sync fails
- Make sure you added all entries to `libs.versions.toml` correctly.
- Check for typos in the library and plugin names.
- Try **File** > **Invalidate Caches** > **Invalidate and Restart**.

#### "The email address is badly formatted"
- Firebase has its own email validation. Make sure you are entering a properly formatted email.

#### App crashes on launch
- Check Logcat (the log panel at the bottom of Android Studio) for error messages.
- Common causes: missing `google-services.json`, wrong activity registered in manifest, or mismatched view IDs.

---

## Summary of Changes from Lesson 5

| File | What Changed |
|---|---|
| `gradle/libs.versions.toml` | Added Firebase BOM, Firebase Auth, and Google Services versions/libraries/plugins |
| `build.gradle.kts` (project) | Added `google-services` plugin with `apply false` |
| `app/build.gradle.kts` | Added `google-services` plugin, `viewBinding = true`, Firebase BOM + Auth dependencies |
| `app/google-services.json` | **New file** -- Downloaded from Firebase console (unique to each student's project) |
| `SignUpActivity.kt` | Switched to view binding, added FirebaseAuth, email validation, password validation, `createUserWithEmailAndPassword`, Toast messages |
| `LoginActivity.kt` | Switched to view binding, added FirebaseAuth, `signInWithEmailAndPassword`, Toast messages |

## Key Concepts Learned

- **Firebase** -- A Google platform providing backend services (authentication, databases, analytics) for mobile apps
- **Firebase Authentication** -- A service that handles user registration and login securely
- **`createUserWithEmailAndPassword`** -- Firebase method to register a new user
- **`signInWithEmailAndPassword`** -- Firebase method to log in an existing user
- **View Binding** -- An Android feature that generates type-safe references to views, replacing `findViewById`
- **`lateinit var`** -- A Kotlin keyword that lets you declare a variable without initializing it immediately
- **Regular Expressions (Regex)** -- Patterns used to validate text formats (like email addresses)
- **Toast** -- A small popup message for giving brief feedback to the user
- **`addOnCompleteListener`** -- A callback pattern for handling asynchronous Firebase operations
- **Firebase BOM** -- Bill of Materials that manages compatible versions of all Firebase libraries

## What's Next

You now have a working sign-up and login system! In the next lesson, you will have time to regroup, fix any issues, and polish your implementation.
