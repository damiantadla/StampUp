package com.example.stempup

import UserViewModel
import android.graphics.Bitmap
//import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontSynthesis.Companion.Weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun UserScreen(navController: NavHostController, onSignOut: (Any?) -> Unit){
    var countSticker by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser
    user?.let {
        // Name, email address, and profile photo Url
        val name = it.displayName
        val email = it.email
        val photoUrl = it.photoUrl

        // Check if user's email is verified
        val emailVerified = it.isEmailVerified

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getIdToken() instead.
        val uid = it.uid

    }
    val db = Firebase.firestore

    val docRef = user?.uid?.let { db.collection("users").document(it) }
    docRef?.get()
        ?.addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                countSticker = (document.data?.get("numberOfStamps") as Long).toInt()
            } else {
                Log.d("TAG", "No such document")
            }
        }
        ?.addOnFailureListener { exception ->
            Log.d("TAG", "get failed with ", exception)
        }

    val userId = "peH1UYgrezcJf0K2kQ5jErZaRfE2\n"
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val dialogState = remember { mutableStateOf(false) }
    val stickerGoal = 12
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Row(

            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text="Hello, ${user?.displayName}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
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
                    modifier = Modifier.size(50.dp),
                    tint =  Color(0XFF6750A4)
                )
            }
        }


        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                StickerGrid(countSticker = countSticker, stickerGoal = stickerGoal)
            }
        }

        Text(
            fontSize = 24.sp,
            text = "Your gifts",
            fontWeight = FontWeight.Bold)


        Column(
            modifier = Modifier
                .height(234.dp)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()) // Dodajemy pionowy scroll
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cappuccino", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "12-01-2023")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Flat white", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "26-11-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Latte macchiato", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "22-09-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Flat white", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "15-08-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cappuccino", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "13-07-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Espresso", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "12-05-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Latte macchiato", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "12-04-2022")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Espresso", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "30-03-2021")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cappuccino", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "23-03-2021")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_cafe_24),
                        contentDescription = "Cafe",
                        tint =  Color(0XFF6750A4),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Espresso", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "12-02-2020")
                }
            }
        }


        Spacer(modifier = Modifier.weight(1f)) // allign this fucking button to bottom.

        Button(shape = CircleShape, // Apply CircleShape to make the button circular
            modifier = Modifier.size(80.dp) ,onClick = {
                user?.uid?.let {
                    qrBitmap = generateQRCode(it)
                    dialogState.value = true
                }
            }) {
            Image(painter = painterResource(id = R.drawable.qrc), contentDescription = "Icon qr code",
                modifier = Modifier.size(80.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (dialogState.value) {
            Dialog(
                onDismissRequest = { dialogState.value = false },
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
                content = {
                    qrBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(400.dp)
                        )
                    }
                }
            )
        }



    }
}

fun generateQRCode(text: String): Bitmap? {
    val writer = QRCodeWriter()
    return try {
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bmp
    } catch (e: WriterException) {
        Log.e("QRCodeGeneration", "Error generating QR code", e) //czy jest blad przy geneorwaniu QRCODE //TODO Do usuniecia, LOG
        null
    }
}



@Composable
fun StickerGrid(countSticker: Int, stickerGoal: Int) {
    val columns = 4
    val rows = (stickerGoal / columns) + if (stickerGoal % columns != 0) 1 else 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(columns) { columnIndex ->
                    val stickerNumber = (rowIndex * columns) + columnIndex + 1
                    if (stickerNumber <= stickerGoal) {
                        val stickerCollected = stickerNumber <= countSticker
                        StickerIcon(
                            stickerCollected = stickerCollected,
                            stickerNumber = stickerNumber
                        )
                    } else {
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StickerIcon(stickerCollected: Boolean, stickerNumber: Int) {
    val stickerImage =  R.drawable.baseline_local_cafe_24

    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                color = if (stickerCollected) Color(0XFF6750A4) else Color(0xFFFFFFFF),
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = stickerImage),
            contentDescription = "Sticker $stickerNumber",
            modifier = Modifier.align(Alignment.Center),
            tint = if (stickerCollected) Color(0XFFFFFFFF) else Color(0XFF6750A4),
        )
    }

}