package com.example.stempup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun OwnerScreen(navController: NavHostController, onSignOut: (Any?) -> Unit) {
    val context = LocalContext.current
    val user = Firebase.auth.currentUser
    user?.let {
        val name = it.displayName
        val email = it.email
        val photoUrl = it.photoUrl
        val emailVerified = it.isEmailVerified
        val uid = it.uid
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Hello, ${user?.displayName}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = alfaColor
                ),
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Logout",
                    modifier = Modifier.size(60.dp),
                    tint = Color(0XFF6750A4)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            fontSize = 24.sp,
            text = "Last stamps",
            fontWeight = FontWeight.Bold
        )
        UserList(
            users = listOf(
                Pair("jane.smith@example.net", "26-04-2024"),
                Pair("sample.email@domain.com", "01-11-2024"),
                Pair("testaccount@hotmail.com", "12-12-2022"),
                Pair("sample.email@domain.com", "04-11-2022"),
                Pair("sample.email@domain.com", "26-01-2022"),
                Pair("jane.smith@example.net", "29-11-2021"),
                Pair("sample.email@domain.com", "26-10-2021"),
                Pair("jane.smith@example.net", "26-11-2021")
            ),
            iconType = "stamp"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            fontSize = 24.sp,
            text = "New users",
            fontWeight = FontWeight.Bold
        )
        UserList(
            users = listOf(
                Pair("jane.smith@example.net", "26-04-2024"),
                Pair("john.doe@example.com", "15-03-2024"),
                Pair("user123@yahoo.com", "08-02-2024"),
                Pair("testaccount@hotmail.com", "30-01-2024"),
                Pair("sample.email@domain.com", "12-01-2024")
            ),
            iconType = "user"
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            onClick = {
                scanCodeHandler(context)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.qrc),
                contentDescription = "Icon google",
                modifier = Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun UserList(users: List<Pair<String, String>>, iconType: String) {
    Column(
        modifier = Modifier
            .height(230.dp)
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        users.forEach { user ->
            UserRow(user.first, user.second, iconType)
        }
    }
}

@Composable
fun UserRow(email: String, date: String, iconType: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 50.dp)
        ) {
            when (iconType) {
                "stamp" -> {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint = Color(0XFF6750A4)
                    )
                }
                "user" -> {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "User",
                        tint = Color(0XFF6750A4)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = email, fontWeight = FontWeight.Bold)
                Text(text = date)
            }
        }
    }
}

fun scanCodeHandler(context: Context) {
    if (context is MainActivity) {
        context.scanCode()
    } else {
        Toast.makeText(context, "Error: Unable to access MainActivity", Toast.LENGTH_SHORT).show()
    }
}
