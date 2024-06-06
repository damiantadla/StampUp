package com.example.stempup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(painter = painterResource(id = R.drawable.icon), contentDescription = "Icon image",
            modifier = Modifier.size(200.dp)
        )
        HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
        NormalTextComponent(value = stringResource(id = R.string.app_name))
        Spacer(modifier = Modifier.height(24.dp))


        MyTextField(
            labelValue = stringResource(id = R.string.email),
            painterResource = painterResource(id = R.drawable.baseline_mail_24)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            labelValue = stringResource(id = R.string.password) ,
            painterResource = painterResource(id = R.drawable.baseline_lock_24) )

        Spacer(modifier = Modifier.height(32.dp))

        MyButton(labelValue = stringResource(id = R.string.login))

        Spacer(modifier = Modifier.height(24.dp))
        Row(){
            Text(text="Don't have an account? ", fontSize = 16.sp)
            ClickableText(
                onClick = {navController.navigate("register")},
                text= AnnotatedString(text = "Create Account" ),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = primaryColor
                )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(){
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = alfaColor
                ),
                border = null,
                onClick = {
                }
            ) {
                Image(painter = painterResource(id = R.drawable.google), contentDescription = "Icon google",
                    modifier = Modifier.size(40.dp)
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = alfaColor
                ),
                border = null,
                onClick = {
                    // Handle the button click
                }
            ) {
                Image(painter = painterResource(id = R.drawable.linkedin), contentDescription = "Icon facebook",
                    modifier = Modifier.size(40.dp)
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = alfaColor
                ),
                border = null,
                onClick = {
                    // Handle the button click
                }
            ) {
                Image(painter = painterResource(id = R.drawable.github), contentDescription = "Icon github",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}