package com.example.stempup

import UserViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun UserScreen(navController: NavHostController){
    val userViewModel: UserViewModel = viewModel()
    val user = userViewModel.currentUser
    val displayName = user?.email ?: "Unknown"
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(

            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
          ){
            Text(text=displayName)
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = alfaColor
                ),
                border = null,
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Logout",
                    modifier = Modifier.size(60.dp),
                    tint =  primaryColor
                )
            }
        }
        Button(onClick = {       Log.i("Sp", userViewModel.number.toString())}) {

        }
    }
}