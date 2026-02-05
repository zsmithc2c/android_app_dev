# Lesson 5: Building Login and Sign-Up Pages

## Lesson Overview

In this lesson you will build login and sign-up pages for your Android app. You will focus on creating the XML layouts for styling, setting up input fields and buttons, and wiring up navigation between the two pages using Intents. **The sign-up and login will not be functional yet** -- that comes in a future lesson. Today is all about the UI.

## Learning Objectives

- Create simple sign-up and login pages in Android Studio
- Use XML layout files to style sign-up and login pages
- Navigate between activities using Intents

## What You Need Before Starting

You should have a working Android project from Lesson 3 with:
- `MainActivity` with a button that navigates to `SettingsActivity`
- `SettingsActivity` with a back button
- `MyFragment` integrated into your layouts
- Interactive elements (EditText + Submit button)

The **starter/** folder contains this exact starting point. If your project got out of sync, you can copy the starter code into Android Studio and work from there.

The **completed/** folder contains the finished state after all steps below are done. Use it as a reference if you get stuck.

> **Note:** The starter and completed code use the package name `com.example.lesson3`. Your project may use a different package name (e.g., `com.example.myapp`). Wherever you see `com.example.lesson3` in the instructions below, substitute your own package name.

---

## Step-by-Step Guide

### Step 1: Create the SignUpActivity

We need a new Activity for our sign-up page.

1. In Android Studio, go to **File > New > Activity > Empty Activity**.
2. Name it **SignUpActivity**.
3. Make sure the **Generate Layout File** checkbox is checked (it should auto-name the layout `activity_sign_up`).
4. Make sure the **Launcher Activity** checkbox is **unchecked**.
5. Click **Finish**.

Android Studio will create two files for you:
- `SignUpActivity.kt` in your `java/com/example/<your_package>/` folder
- `activity_sign_up.xml` in your `res/layout/` folder

It will also automatically register the activity in your `AndroidManifest.xml`.

---

### Step 2: Create the LoginActivity

Repeat the same process for the login page.

1. Go to **File > New > Activity > Empty Activity**.
2. Name it **LoginActivity**.
3. Make sure **Generate Layout File** is checked (layout will be `activity_login`).
4. Make sure **Launcher Activity** is **unchecked**.
5. Click **Finish**.

You should now have:
- `LoginActivity.kt` in your Kotlin source folder
- `activity_login.xml` in your `res/layout/` folder

---

### Step 3: Build the Sign-Up Page Layout

Open `res/layout/activity_sign_up.xml` and switch to the **Code** or **Split** view. Replace the entire contents with the following XML:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/signUpLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="24dp">

    <TextView
        android:id="@+id/signUpTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Create Account"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:layout_marginTop="48dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/signUpSubtitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sign up to get started"
        android:textSize="16sp"
        android:textColor="#666666"
        android:layout_marginTop="8dp"
        app:layout_constraintTop_toBottomOf="@id/signUpTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextUsername"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Username"
        android:inputType="text"
        android:padding="16dp"
        android:layout_marginTop="32dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/signUpSubtitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextEmail"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:inputType="textEmailAddress"
        android:padding="16dp"
        android:layout_marginTop="16dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/editTextUsername"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextPassword"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword"
        android:padding="16dp"
        android:layout_marginTop="16dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/editTextEmail"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/signUpButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Sign Up"
        android:textSize="16sp"
        android:padding="12dp"
        android:layout_marginTop="24dp"
        android:backgroundTint="#6200EE"
        app:layout_constraintTop_toBottomOf="@id/editTextPassword"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/loginLink"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Already have an account? Log in"
        android:textSize="14sp"
        android:textColor="#6200EE"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/signUpButton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### What each element does:

| Element | Purpose |
|---|---|
| `signUpTitle` (TextView) | Large bold heading that says "Create Account" |
| `signUpSubtitle` (TextView) | Smaller subtitle text below the heading |
| `editTextUsername` (EditText) | Input field for the user's username. `inputType="text"` shows a normal keyboard. |
| `editTextEmail` (EditText) | Input field for email. `inputType="textEmailAddress"` shows a keyboard optimized for email (with `@`). |
| `editTextPassword` (EditText) | Input field for password. `inputType="textPassword"` hides the characters as the user types. |
| `signUpButton` (Button) | The main sign-up button. Styled with a purple tint (`#6200EE`). |
| `loginLink` (TextView) | A clickable text link to navigate to the login page. Colored purple to look like a link. |

#### Key XML styling concepts used:

- **`android:background="#F5F5F5"`** -- Sets a light gray background on the entire page.
- **`android:textSize="28sp"`** -- Sets font size. `sp` (scale-independent pixels) respects the user's font size preferences.
- **`android:textStyle="bold"`** -- Makes text bold.
- **`android:textColor="#333333"`** -- Sets text color using a hex color code.
- **`android:inputType="textPassword"`** -- Tells Android to mask the input with dots.
- **`android:inputType="textEmailAddress"`** -- Shows an email-optimized keyboard.
- **`android:backgroundTint="#6200EE"`** -- Changes the button's background color.
- **`android:padding="24dp"`** -- Adds space inside the element. `dp` (density-independent pixels) ensures consistent sizing across screen densities.
- **`android:layout_marginTop="16dp"`** -- Adds space above the element.

---

### Step 4: Add Kotlin Code to SignUpActivity

Open `SignUpActivity.kt` and replace its contents with:

```kotlin
package com.example.lesson3  // <-- Use YOUR package name here

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signUpLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameInput = findViewById<EditText>(R.id.editTextUsername)
        val emailInput = findViewById<EditText>(R.id.editTextEmail)
        val passwordInput = findViewById<EditText>(R.id.editTextPassword)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        signUpButton.setOnClickListener {
            // Sign-up logic will be added in a future lesson
        }

        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
```

#### What this code does:

1. **`setContentView(R.layout.activity_sign_up)`** -- Connects this Activity to the XML layout we just created.
2. **`findViewById<EditText>(R.id.editTextUsername)`** -- Finds the username input field by its ID so we can use it in code. We do the same for email, password, the button, and the link.
3. **`signUpButton.setOnClickListener { ... }`** -- Sets up a click listener on the sign-up button. For now it does nothing -- we will add real sign-up logic in a future lesson.
4. **`loginLink.setOnClickListener { ... }`** -- When the user taps "Already have an account? Log in", this creates an **Intent** to navigate to `LoginActivity` and starts that activity.

#### Understanding Intents (review from Lesson 3):

An **Intent** is how Android navigates between activities. The pattern is always:
```kotlin
val intent = Intent(this, TargetActivity::class.java)
startActivity(intent)
```
- `this` refers to the current activity (where we are now).
- `TargetActivity::class.java` is the activity we want to go to.
- `startActivity(intent)` performs the navigation.

---

### Step 5: Build the Login Page Layout

Open `res/layout/activity_login.xml` and replace its contents with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/loginLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="24dp">

    <TextView
        android:id="@+id/loginTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome Back"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:layout_marginTop="48dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/loginSubtitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Log in to your account"
        android:textSize="16sp"
        android:textColor="#666666"
        android:layout_marginTop="8dp"
        app:layout_constraintTop_toBottomOf="@id/loginTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextLoginUsername"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Username"
        android:inputType="text"
        android:padding="16dp"
        android:layout_marginTop="32dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/loginSubtitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextLoginPassword"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword"
        android:padding="16dp"
        android:layout_marginTop="16dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/editTextLoginUsername"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/loginButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Log In"
        android:textSize="16sp"
        android:padding="12dp"
        android:layout_marginTop="24dp"
        android:backgroundTint="#6200EE"
        app:layout_constraintTop_toBottomOf="@id/editTextLoginPassword"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/signUpLink"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Don't have an account? Sign up"
        android:textSize="14sp"
        android:textColor="#6200EE"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/loginButton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### How the login page differs from sign-up:

- **No email field** -- The login page only needs username and password (the user already provided their email when signing up).
- **Different heading** -- "Welcome Back" instead of "Create Account".
- **Navigation link goes the other direction** -- "Don't have an account? Sign up" links back to `SignUpActivity`.

Everything else (styling, layout structure, ConstraintLayout usage) follows the same patterns as the sign-up page.

---

### Step 6: Add Kotlin Code to LoginActivity

Open `LoginActivity.kt` and replace its contents with:

```kotlin
package com.example.lesson3  // <-- Use YOUR package name here

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameInput = findViewById<EditText>(R.id.editTextLoginUsername)
        val passwordInput = findViewById<EditText>(R.id.editTextLoginPassword)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpLink = findViewById<TextView>(R.id.signUpLink)

        loginButton.setOnClickListener {
            // Login logic will be added in a future lesson
        }

        signUpLink.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
```

This follows the exact same pattern as `SignUpActivity`:
- Connect to the layout with `setContentView`
- Find each UI element with `findViewById`
- Set click listeners for the button and navigation link
- The login button does nothing for now (placeholder for future lesson)
- The "Sign up" link navigates to `SignUpActivity`

---

### Step 7: Verify the AndroidManifest

Open `app/src/main/AndroidManifest.xml` and make sure both new activities are registered. If you used **File > New > Activity**, Android Studio should have added them automatically. Your manifest should look like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Lesson3">
        <activity
            android:name=".LoginActivity"
            android:exported="false" />
        <activity
            android:name=".SignUpActivity"
            android:exported="false" />
        <activity
            android:name=".SettingsActivity"
            android:exported="false" />
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

**Important:** If you do NOT see `LoginActivity` and `SignUpActivity` listed, you need to add them manually. Add these lines inside the `<application>` tag (before the `MainActivity` entry):

```xml
<activity
    android:name=".LoginActivity"
    android:exported="false" />
<activity
    android:name=".SignUpActivity"
    android:exported="false" />
```

#### What does `android:exported="false"` mean?

- `exported="true"` means other apps can launch this activity (used for `MainActivity` because the Android launcher needs to open it).
- `exported="false"` means only your own app can navigate to this activity. Sign-up and login pages should not be launchable by other apps.

---

### Step 8: Add a Navigation Button on MainActivity

To let users reach the sign-up page from the main screen, we need to add a button to `MainActivity`.

#### 8a. Add the button to the layout

Open `res/layout/activity_main.xml` and add this button **before** the closing `</androidx.constraintlayout.widget.ConstraintLayout>` tag. Make sure the `layout_constraintTop_toBottomOf` points to whatever element is currently last in your layout (in the starter code, that is `outputText`):

```xml
<Button
    android:id="@+id/signUpNavButton"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:text="Go to Sign Up"
    android:layout_margin="24dp"
    app:layout_constraintTop_toBottomOf="@id/outputText"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"/>
```

#### 8b. Wire up the button in Kotlin

Open `MainActivity.kt` and add the following code **inside** `onCreate()`, after the existing code:

```kotlin
// Lesson 5: Navigate to Sign Up page
val signUpButton = findViewById<Button>(R.id.signUpNavButton)
signUpButton.setOnClickListener {
    val intent = Intent(this, SignUpActivity::class.java)
    startActivity(intent)
}
```

This uses the same Intent pattern you learned in Lesson 3 -- find the button, set a click listener, create an Intent, and start the new activity.

---

### Step 9: Style Your Pages (Independent Work)

Now that the basic structure is in place, customize the look and feel of your sign-up and login pages. Here are some ideas:

#### Changing background colors

On any element, you can set:
```xml
android:background="#yourHexColor"
```

Some colors to try:
- `#FFFFFF` -- White
- `#F5F5F5` -- Light gray (current)
- `#E3F2FD` -- Light blue
- `#FFF3E0` -- Light orange
- `#E8F5E9` -- Light green

#### Changing button colors

```xml
android:backgroundTint="#yourHexColor"
```

Some button colors to try:
- `#6200EE` -- Purple (current)
- `#03DAC5` -- Teal
- `#FF5722` -- Deep orange
- `#4CAF50` -- Green

#### Changing text size and style

```xml
android:textSize="20sp"
android:textStyle="bold"
android:textColor="#yourHexColor"
```

#### Adding padding and margins

- **Padding** adds space *inside* an element (between the border and the content).
- **Margin** adds space *outside* an element (between this element and its neighbors).

```xml
android:padding="16dp"
android:layout_margin="24dp"
android:layout_marginTop="16dp"
```

#### Experiment!

Try changing:
- The background color of the sign-up and login pages
- The button colors and text sizes
- The spacing between elements
- Adding a background image (advanced)

---

### Step 10: Run and Test Your App

1. Click the **Run** button (green play icon) in Android Studio, or press **Shift + F10**.
2. When the app opens on your emulator or device:
   - You should see your `MainActivity` with all the elements from Lesson 3, plus a new "Go to Sign Up" button.
   - Tap **"Go to Sign Up"** -- you should see the sign-up page with username, email, and password fields.
   - Tap **"Already have an account? Log in"** on the sign-up page -- you should navigate to the login page.
   - Tap **"Don't have an account? Sign up"** on the login page -- you should navigate back to the sign-up page.
   - Press the **back button** on your device/emulator to go back through the activity stack.

If something doesn't work, check:
- Are both activities registered in `AndroidManifest.xml`?
- Do the `android:id` values in your XML match the `R.id.*` values in your Kotlin code?
- Is your package name correct in all Kotlin files?
- Did you save all files before running?

---

## Summary of Files Changed

| File | What Changed |
|---|---|
| `SignUpActivity.kt` | **New file** -- Kotlin activity for the sign-up page |
| `LoginActivity.kt` | **New file** -- Kotlin activity for the login page |
| `activity_sign_up.xml` | **New file** -- Layout with username, email, password fields + sign-up button + login link |
| `activity_login.xml` | **New file** -- Layout with username, password fields + login button + sign-up link |
| `AndroidManifest.xml` | **Modified** -- Added `SignUpActivity` and `LoginActivity` entries |
| `activity_main.xml` | **Modified** -- Added "Go to Sign Up" button |
| `MainActivity.kt` | **Modified** -- Added click listener to navigate to `SignUpActivity` |

## Key Concepts Learned

- **EditText input types:** `text`, `textEmailAddress`, `textPassword` -- each shows a different keyboard and behavior
- **XML styling:** background colors, text sizes, font styles, padding, margins, and button tints
- **ConstraintLayout positioning:** Using `layout_constraintTop_toBottomOf` to chain elements vertically
- **Intent navigation:** Creating Intents to move between Activities
- **AndroidManifest:** Every Activity must be registered in the manifest to be usable

## What's Next

In the next lesson, we will make the sign-up and login pages **functional** by connecting them to user authentication. The UI you built today is the foundation for that!
