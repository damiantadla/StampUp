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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
        Text(text="Welcome back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text ="StempUp", fontSize = 20.sp, fontWeight = FontWeight.Medium, color= primaryColor)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(value="", onValueChange = {}, label = {
            Text(text = "Email")
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value="", onValueChange = {}, label = {
            Text(text = "Password")
        })

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.size(150.dp, 55.dp),
            onClick = {}){
            Text(
                fontSize = 16.sp,
                text = "Login")
        }
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