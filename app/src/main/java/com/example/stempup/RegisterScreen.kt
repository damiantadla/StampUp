package com.example.stempup

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        NormalTextComponent(value = stringResource(id = R.string.app_name))
        HeadingTextComponent(value = stringResource(id = R.string.register_text))


        Spacer(modifier = Modifier.height(24.dp))


        MyTextField(
            labelValue = stringResource(id = R.string.first_name),
            painterResource(id = R.drawable.baseline_person_24)
        )


        Spacer(modifier = Modifier.height(8.dp))

        MyTextField(
            labelValue = stringResource(id = R.string.last_name),
            painterResource = painterResource(id = R.drawable.baseline_person_24)
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            labelValue = stringResource(id = R.string.email),
            painterResource = painterResource(id = R.drawable.baseline_mail_24)
        )
        Spacer(modifier = Modifier.height(8.dp))


        PasswordTextField(
            labelValue = stringResource(id = R.string.password) ,
            painterResource = painterResource(id = R.drawable.baseline_lock_24) )


        Spacer(modifier = Modifier.height(32.dp))

        MyButton(labelValue = stringResource(id = R.string.register))

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