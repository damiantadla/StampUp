package com.example.stempup

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavHostController
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        setContent {
            navController = rememberNavController()
            Surface {
                NavHost(navController = navController, startDestination = "login") {
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
                    composable("userScreen") { UserScreen(OnSignOut = { signOut(navController) }) }
                    composable("ownerScreen") { OwnerScreen(navController) }
                }
            }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                Log.d("MainActivity", "User is signed in: ${currentUser.uid}")
                reload()
            } else {
                Log.d("MainActivity", "No user is signed in")
            }
        }
    }

    private fun reload() {
        Log.d("MainActivity", "Reloading user data")
        var user = auth.currentUser
        user?.let {val uid = it.uid}
        val docRef = db.collection("users").document(user?.uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    document.getString("role")?.let {
                        if (document.getString("role") == "user") {
                            navController.navigate("userScreen")
                        } else if (document.getString("role") == "owner") {
                            navController.navigate("ownerScreen")
                        } else {
                            navController.navigate("userScreen")
                        }
                    }
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    data class User(
        val displayName: String? = "Johny Smith",
        val email: String? = "example@example.com",
        val numberOfStamps: Int = 0,
        val history: List<String>? = null,
        val role: String? = "user"
    )

    private fun signIn(email: String, password: String, navController: NavHostController) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("Login", "Email or password is empty")
            Toast.makeText(this, "Email or password is empty", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        if (user.isEmailVerified) {
                            Log.d("Login", "signInWithEmail:success")
                            Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show()
                            val docRef = db.collection("users").document(user.uid)
                            docRef.get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        document.getString("role")?.let {
                                            if (document.getString("role") == "user") {
                                                navController.navigate("userScreen")
                                            } else if (document.getString("role") == "owner") {
                                                navController.navigate("ownerScreen")
                                            } else {
                                                navController.navigate("userScreen")
                                            }
                                        }
                                    } else {
                                        Log.d("TAG", "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("TAG", "get failed with ", exception)
                                }
                            navController.navigate("userScreen")
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

    private fun signOut(navController: NavHostController) {
        auth.signOut()
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
        navController.navigate("login")
    }

    private fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        navController: NavHostController
    ) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Fields is empty", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Email or password is empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
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
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
    }
}
