package com.example.stempup

import UserViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this)

        // Initialize Firebase Auth
        auth = Firebase.auth
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        setContent {
            val navController = rememberNavController()
            Surface {
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController, auth,
                        onSignInClick = { email, password ->
                            signIn(email, password, navController)
                        }, this@MainActivity)}
                    composable("register") { RegisterScreen(navController) }
                    composable("userScreen") {UserScreen(navController) }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("MainActivity", "User is signed in: ${currentUser.uid}")
            reload()
        } else {
            Log.d("MainActivity", "No user is signed in")
        }
    }

    private fun reload() {
        Log.d("MainActivity", "Reloading user data")
        // Implement your reload logic here
    }

    private fun signIn(email: String, password: String, navController: NavHostController) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("Login", "Email or password is empty")
            return
        }

        // Proceed with sign in
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "signInWithEmail:success")
                    val user = auth.currentUser
                    Log.i("User", user?.email ?: "User email is null")
                    Log.i("User", user?.email ?: "User email is null")
                    userViewModel.setUser(auth.currentUser)
                    Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show()
                    navController.navigate("userScreen")
                } else {
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}
