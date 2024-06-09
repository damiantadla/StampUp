package com.example.stempup

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalTextComponent(value: String){
    Text(
        text = value,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = primaryColor
    )
}

@Composable
fun HeadingTextComponent(value: String){
    Text(
        text = value,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MyTextField(labelValue: String, painterResource: Painter, initialValue: String, onTextChanged: (String) -> Unit){

    val textValue = remember {
        mutableStateOf(initialValue)
    }

    OutlinedTextField(
        label = { Text(text = labelValue) },
        value = textValue.value,
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
                        },
        leadingIcon = { Icon(painter = painterResource, contentDescription ="" )}


    )
}


@Composable
fun EmailTextField(labelValue: String, painterResource: Painter, initialEmail: String, onEmailChanged: (String) -> Unit){

    val email = remember {
        mutableStateOf(initialEmail)
    }

    OutlinedTextField(
        label = { Text(text = labelValue) },
        value = email.value,
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            email.value = it
            onEmailChanged(it)
                        },
        leadingIcon = { Icon(painter = painterResource, contentDescription ="" )}


    )
}

@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter,    initialPassword: String,    onPasswordChanged: (String) -> Unit){

    val password = remember {
        mutableStateOf(initialPassword)
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        label = { Text(text = labelValue) },
        value = password.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            password.value = it
            onPasswordChanged(it)
        },
        leadingIcon = { Icon(painter = painterResource, contentDescription ="" )},
        trailingIcon = {

            val iconImage = if (passwordVisible.value){
                Icons.Filled.Favorite //TODO Zmienic kurwa ikonke
            } else{
                Icons.Filled.FavoriteBorder //TODO Zmienic ikonke bo nie umiem
            }

            var description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            }else{
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = description
                )

            }
        },

        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()


    )
}


@Composable
fun MyButton(labelValue: String){
    Button(
        modifier = Modifier.size(150.dp, 55.dp),
        onClick = {}){
        Text(
            fontSize = 16.sp,
            text = labelValue )
    }
}
