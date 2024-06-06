package com.example.stempup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.format.TextStyle

@Composable
fun RegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Hey There", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = primaryColor)
        Text(text = "Create an Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))


        var first_name by remember {
            mutableStateOf("")
        }


        OutlinedTextField(value = first_name, onValueChange = {first_name = it}, label = {
            Text(text = "First Name")
        })
        Spacer(modifier = Modifier.height(8.dp))


        var last_name by remember {
            mutableStateOf("")
        }

        OutlinedTextField(value = last_name, onValueChange = {last_name=it}, label = {
            Text(text = "Last Name")
        })
        Spacer(modifier = Modifier.height(24.dp))

        var email by remember {
            mutableStateOf("")
        }

        OutlinedTextField(value = email, onValueChange = {email=it}, label = {
            Text(text = "Email")
        })
        Spacer(modifier = Modifier.height(8.dp))


        //zmienna do hasła
        var password by remember { mutableStateOf("") }

        // Zmeinna Zapisyuwanie stanu czy visible czy nie
        var passwordVisible by remember { mutableStateOf(false) }

        // Tworzenie Samego Pola
        OutlinedTextField(
            value = password,
            onValueChange = {password = it },
            label = { Text("Password") },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Favorite // TODO Tutaj ikonki sa do zmiany bo wjebalem na szybko !!!
                else Icons.Filled.Face // TODO Tutaj ikonka jest do zmiany bo wjebalem na szybko !!!

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.size(150.dp, 55.dp),
            onClick = {}){
            Text(
                fontSize = 16.sp,
                text = "Register ")
        }
        Spacer(modifier = Modifier.height(100.dp))


        Row(){
            Text(text="Already have an account? ", fontSize = 16.sp)
            ClickableText(
                onClick = {navController.navigate("login")},
                text= AnnotatedString(text = "Login" ),
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = primaryColor
                )
            )
        }


    }
}