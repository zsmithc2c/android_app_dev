# Lesson 8: User Profile

## Overview

In Lesson 8, you will create a User Profile section for your app, set up navigation using an options menu, and integrate Firebase Realtime Database to prepare for storing user data. By the end of this lesson, your app will have a profile page accessible from a menu in the action bar, and a working connection to Firebase's Realtime Database.

## Learning Objectives

- Create a user interface for the user profile section
- Add a navigation menu to the action bar
- Understand the `override` keyword, `onCreateOptionsMenu`, and `onOptionsItemSelected`
- Set up Firebase Realtime Database
- Write a test message to the database to verify connectivity

## Prerequisites

- Completed Lesson 7 (styled home page with auth)
- Working Firebase project with Authentication enabled
- Your `google-services.json` file configured in your project

---

## Step 1: Create the ProfileActivity

First, we need a new Activity for the user profile page.

### 1.1 Create the Layout File

Create a new file at `app/src/main/res/layout/activity_profile.xml` with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/profileLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="24dp">

    <TextView
        android:id="@+id/profileTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="My Profile"
        android:textSize="28sp"
        android:textStyle="bold"
        android:textColor="#333333"
        android:fontFamily="sans-serif-medium"
        android:layout_marginTop="24dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <ImageView
        android:id="@+id/profileImageView"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="24dp"
        android:src="@mipmap/ic_launcher_round"
        android:contentDescription="Profile Picture"
        app:layout_constraintTop_toBottomOf="@id/profileTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextFullName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Full Name"
        android:inputType="textPersonName"
        android:padding="16dp"
        android:textSize="16sp"
        android:layout_marginTop="24dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/profileImageView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextProfileUsername"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Username"
        android:inputType="text"
        android:padding="16dp"
        android:textSize="16sp"
        android:layout_marginTop="16dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/editTextFullName"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <EditText
        android:id="@+id/editTextProfileEmail"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:inputType="textEmailAddress"
        android:padding="16dp"
        android:textSize="16sp"
        android:layout_marginTop="16dp"
        android:background="@android:drawable/edit_text"
        app:layout_constraintTop_toBottomOf="@id/editTextProfileUsername"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 1.2 Understanding the UI Elements

| Element | ID | Purpose |
|---------|-----|---------|
| `TextView` | `profileTitle` | Page title "My Profile" |
| `ImageView` | `profileImageView` | Placeholder for profile picture (uses the app icon for now) |
| `EditText` | `editTextFullName` | Input field for the user's full name |
| `EditText` | `editTextProfileUsername` | Input field for the username |
| `EditText` | `editTextProfileEmail` | Input field for the email (auto-filled from Firebase Auth) |

### 1.3 Create the ProfileActivity.kt

Create a new file at `app/src/main/java/com/example/lesson3/ProfileActivity.kt` (use your package name):

```kotlin
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
```

### 1.4 Understanding the Code

**Firebase Realtime Database setup:**
```kotlin
database = FirebaseDatabase.getInstance()
myRef = database.getReference("message")
myRef.setValue("Hello, World!")
```
- `FirebaseDatabase.getInstance()` - Gets a reference to the Firebase Realtime Database
- `database.getReference("message")` - Points to a specific location (node) in the database called "message"
- `myRef.setValue("Hello, World!")` - Writes the string to that location. This is our test to verify the database connection works.

**Auto-filling email:**
```kotlin
val user = firebaseAuth.currentUser
if (user != null) {
    emailInput.setText(user.email)
}
```
- Gets the currently signed-in user from Firebase Auth
- Pre-fills the email field with their email address

### 1.5 Register ProfileActivity in AndroidManifest.xml

Open `app/src/main/AndroidManifest.xml` and add the ProfileActivity entry:

```xml
<activity
    android:name=".ProfileActivity"
    android:exported="false" />
```

Add this inside the `<application>` tag, alongside the other activity entries.

---

## Step 2: Add a Navigation Menu

Now we need a way to get to the Profile page. We'll add an options menu to the action bar on the home page.

### 2.1 Enable the Action Bar

The menu requires the action bar to be visible. Open your theme files and remove `.NoActionBar` from the parent theme.

**`app/src/main/res/values/themes.xml`** - Change:
```xml
<style name="Base.Theme.Lesson3" parent="Theme.Material3.DayNight.NoActionBar">
```
To:
```xml
<style name="Base.Theme.Lesson3" parent="Theme.Material3.DayNight">
```

**`app/src/main/res/values-night/themes.xml`** - Make the same change:
```xml
<style name="Base.Theme.Lesson3" parent="Theme.Material3.DayNight">
```

> **Why?** The `NoActionBar` theme hides the action bar at the top of the screen. Since our menu items live in the action bar, we need it visible. After this change, you'll see a toolbar at the top of your app with your app's name.

### 2.2 Create the Menu Resource

Create a new directory `app/src/main/res/menu/` and add a file called `menu_main.xml`:

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/menu_profile"
        android:title="Profile" />
</menu>
```

This defines a single menu item labeled "Profile" that will appear in the action bar's overflow menu (the three dots).

### 2.3 Inflate the Menu in MainActivity

Add two new methods to `MainActivity.kt` after the `onCreate` method:

```kotlin
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.menu_profile -> {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
```

You'll also need to add these imports at the top of `MainActivity.kt`:
```kotlin
import android.view.Menu
import android.view.MenuItem
```

### 2.4 Understanding the Key Concepts

**The `override` keyword:**
In Kotlin, `override` is used when you want to change or extend the behavior of a method inherited from a superclass. `AppCompatActivity` (the superclass) already has `onCreateOptionsMenu` and `onOptionsItemSelected` methods - we're overriding them with our own behavior.

**`onCreateOptionsMenu`:**
- Called automatically by Android when the activity is created
- "Inflating" the menu means loading the XML file and turning it into actual menu items on screen
- `menuInflater.inflate(R.menu.menu_main, menu)` loads our `menu_main.xml`
- Returns `true` to display the menu

**`onOptionsItemSelected`:**
- Called when a user taps a menu item
- Uses Kotlin's `when` expression (similar to switch/case) to check which item was tapped
- `R.id.menu_profile` matches the ID we set in `menu_main.xml`
- Creates an Intent to navigate to `ProfileActivity`
- `else -> super.onOptionsItemSelected(item)` passes unhandled items to the parent class

---

## Step 3: Set Up Firebase Realtime Database

### 3.1 Create the Database in Firebase Console

1. Go to [Firebase Console](https://console.firebase.google.com/) and open your project
2. In the left sidebar, click **Build** > **Realtime Database**
3. Click **Create Database**
4. Choose your database location (use the default)
5. Select **Start in test mode** and click **Enable**

> **Important:** Test mode allows anyone to read/write to your database. This is fine for development, but for a production app you would need proper security rules.

### 3.2 Add the Realtime Database SDK

You can add the SDK using the **Firebase Assistant** in Android Studio:
1. Click **Tools** > **Firebase** in the menu bar
2. Find **Realtime Database** in the panel
3. Click **Add the Realtime Database SDK to your app**
4. Follow the prompts to add dependencies

**Or manually** add the dependency to your build files:

**`gradle/libs.versions.toml`** - Add in the `[versions]` section:
```toml
firebaseDatabase = "21.0.0"
```

Add in the `[libraries]` section:
```toml
firebase-database = { group = "com.google.firebase", name = "firebase-database" }
```

**`app/build.gradle.kts`** - Add in the `dependencies` block:
```kotlin
implementation(libs.firebase.database)
```

After adding, click **Sync Now** in Android Studio.

### 3.3 Understanding the Data Structure

Firebase Realtime Database stores data as JSON. For user profiles, the data will be organized like this:

```json
{
  "users": {
    "userID_1": {
      "username": "user1",
      "email": "user1@example.com",
      "fullName": "User One"
    },
    "userID_2": {
      "username": "user2",
      "email": "user2@example.com",
      "fullName": "User Two"
    }
  }
}
```

Each user's data is stored under their unique Firebase Auth user ID. This keeps data organized and makes it easy to look up any user's profile.

### 3.4 Verify the "Hello, World!" Test

After running your app and navigating to the Profile page:

1. Open your **Firebase Console** > **Realtime Database**
2. You should see a new entry: `message: "Hello, World!"`
3. This confirms your app can communicate with the database

> **Note:** The "Hello, World!" write is just a test. In the next lesson, we'll replace this with actual user profile data storage and retrieval.

---

## Step 4: Test Your App

Run your app and test the complete flow:

1. **Log in** to your app
2. **Tap the three-dot menu** (overflow menu) in the top-right corner of the action bar
3. **Tap "Profile"** to navigate to the Profile page
4. **Verify** you see the profile fields (Full Name, Username, Email)
5. **Check** that the Email field is auto-filled with your login email
6. **Open Firebase Console** > **Realtime Database** and verify the "Hello, World!" message appears
7. **Press Back** to return to the home page

### Troubleshooting

| Issue | Solution |
|-------|----------|
| Menu doesn't appear | Make sure you removed `.NoActionBar` from **both** `themes.xml` files (day and night) |
| Profile page crashes | Verify `ProfileActivity` is registered in `AndroidManifest.xml` |
| "Hello, World!" not in database | Check that you created the Realtime Database in Firebase Console and selected "test mode" |
| Database write fails | Ensure `firebase-database` is in your dependencies and you've synced Gradle |
| Action bar shows wrong title | The title comes from `android:label` in your manifest - it defaults to your app name |

---

## Step 5: Looking Ahead

In the next lesson, we'll build on this foundation to:
- **Save user profile data** (full name, username, email) to Firebase Realtime Database under the user's ID
- **Load profile data** from the database when the user opens their profile
- **Update profile data** when the user edits their information

The key building blocks are now in place:
- Profile UI with input fields
- Navigation menu to access the profile
- Firebase Realtime Database connected and working

---

## Summary of Changes

### New Files
| File | Purpose |
|------|---------|
| `ProfileActivity.kt` | New activity for the user profile page with Firebase RTDB test write |
| `activity_profile.xml` | Layout with profile picture placeholder, full name, username, and email fields |
| `res/menu/menu_main.xml` | Menu resource with "Profile" item for the action bar |

### Modified Files
| File | What Changed |
|------|-------------|
| `MainActivity.kt` | Added `onCreateOptionsMenu` and `onOptionsItemSelected` for menu navigation to Profile |
| `AndroidManifest.xml` | Registered `ProfileActivity` |
| `themes.xml` (day) | Changed parent from `Theme.Material3.DayNight.NoActionBar` to `Theme.Material3.DayNight` |
| `themes.xml` (night) | Same theme change as day version |
| `libs.versions.toml` | Added `firebaseDatabase` version and `firebase-database` library |
| `app/build.gradle.kts` | Added `implementation(libs.firebase.database)` dependency |

### Key Concepts Covered
- **Options Menu** - `onCreateOptionsMenu` and `onOptionsItemSelected` for action bar menus
- **Menu XML resources** - Defining menu items in `res/menu/`
- **Override keyword** - Extending superclass behavior in Kotlin
- **Firebase Realtime Database** - Cloud-hosted NoSQL database with real-time sync
- **DatabaseReference** - Pointing to specific locations in the database
- **setValue()** - Writing data to the database
- **Action Bar theme** - Enabling the action bar by removing `NoActionBar` from the theme
