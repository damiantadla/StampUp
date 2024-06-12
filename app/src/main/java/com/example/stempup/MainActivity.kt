package com.example.stempup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var isLoggedIn: Boolean = false
}

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var barcodeLauncher: ActivityResultLauncher<ScanOptions>
    private var isLoggedIn = false
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                val documentId = result.contents
                val docRef = db.collection("users").document(documentId)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val displayName = document.getString("displayName")
                            val numberOfStamps = document.getLong("numberOfStamps")?.toInt() ?: 0
                            if (numberOfStamps < 10) {
                                val newNumberOfStamps = numberOfStamps + 1
                                docRef.update("numberOfStamps", newNumberOfStamps)
                                    .addOnSuccessListener {
                                        Log.d("TAG", "DocumentSnapshot successfully updated!")
                                        Toast.makeText(this, "Added stamp for: $displayName", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("TAG", "Error updating document", e)
                                    }
                            } else {
                                Toast.makeText(this, "$displayName already has 10 stamps", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.d("TAG", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "get failed with ", exception)
                    }
            }
        }

        isLoggedIn = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)

        setContent {
            var navControllerKey by remember { mutableStateOf(0) }
            val navController = rememberNavController()

            Surface {
                NavHost(navController = navController, startDestination = if (isLoggedIn) "userScreen" else "login") {
                    composable("login") {
                        LoginScreen(
                            navController,
                            auth,
                            onSignInClick = { email, password ->
                                signIn(email.trim(), password.trim(), navController)
                            },
                            this@MainActivity
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            OnSignUpClick = { email, password, firstName, lastName ->
                                signUp(
                                    email.trim(),
                                    password.trim(),
                                    firstName.trim(),
                                    lastName.trim(),
                                    navController
                                )
                            },
                            navController
                        )
                    }
                    composable("userScreen") {
                        UserScreen(
                            navController = navController,
                            onSignOut = {
                                signOut(navController) {
                                    navController.navigate("login") {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    }
                                }
                            }
                        )
                    }
                    composable("ownerScreen") {
                        OwnerScreen(
                            navController = navController,
                            onSignOut = {
                                signOut(navController) {
                                    navController.navigate("login") {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    }
                                }
                            }
                        )
                    }
                }
            }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                Log.d("MainActivity", "User is signed in: ${currentUser.uid}")
                reload(navController)
            } else {
                Log.d("MainActivity", "No user is signed in")
            }
        }
    }

    fun scanCode() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Scan a QR Code")
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(false)
        }
        barcodeLauncher.launch(options)
    }

    data class User(
        val displayName: String? = "Johny Smith",
        val email: String? = "example@example.com",
        val numberOfStamps: Int = 0,
        val history: List<String>? = null,
        val role: String? = "user"
    )

    private fun reload(navController: NavHostController) {
        Log.d("MainActivity", "Reloading user data")
        val user = auth.currentUser
        user?.let {
            val uid = it.uid
            val docRef = db.collection("users").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        document.getString("role")?.let { role ->
                            navController.navigate(if (role == "user") "userScreen" else "ownerScreen")
                        }
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
        }
    }

    private fun signIn(email: String, password: String, navController: NavHostController) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("Login", "Email or password is empty")
            Toast.makeText(this, "Email or password is empty", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    isLoggedIn = true
                    val user = auth.currentUser
                    if (user != null) {
                        if (user.isEmailVerified) {
                            Log.d("Login", "signInWithEmail:success")
                            Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show()
                            val docRef = db.collection("users").document(user.uid)
                            docRef.get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        document.getString("role")?.let { role ->
                                            navController.navigate(if (role == "user") "userScreen" else "ownerScreen")
                                        }
                                    } else {
                                        Log.d("TAG", "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("TAG", "get failed with ", exception)
                                }
                        } else {
                            Log.d("Login", "Email not verified")
                            Toast.makeText(this, "Email not verified", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signOut(navController: NavHostController, onSignedOut: () -> Unit) {
        Firebase.auth.signOut()
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
        isLoggedIn = false
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
        onSignedOut()
    }

    private fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        navController: NavHostController
    ) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Email or password is empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    user!!.updateProfile(userProfileChangeRequest {
                        displayName = "$firstName $lastName"
                    })
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User profile updated.")
                            }
                        }
                    user.sendEmailVerification()
                        .addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Log.d("TAG", "Email verification sent.")
                            } else {
                                Log.e(
                                    "TAG",
                                    "Failed to send email verification.",
                                    verificationTask.exception
                                )
                            }
                        }
                    val city = User(
                        "$firstName $lastName",
                        user.email,
                    )
                    db.collection("users").document(user.uid).set(city)

                    navController.navigate("login")
                    Toast.makeText(
                        baseContext,
                        "Email verification sent.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
