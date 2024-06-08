package com.example.stempup

import UserViewModel
import android.graphics.Bitmap
//import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource

private const val l = 0xFFFBC2EB

@Composable
fun UserScreen(navController: NavHostController){
    val userViewModel: UserViewModel = viewModel()
    val user = userViewModel.currentUser
    val displayName = user?.email ?: "Unknown"


    val userId = "Kuba123"
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val dialogState = remember { mutableStateOf(false) }
    val countSticker = 3
    val stickerGoal = 10

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
        Box(modifier = Modifier.padding(16.dp)) {
            Box(modifier =Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFBC2EB), // Pierwszy kolor gradient
                        Color(0xFFa6c1ee)  // Drugi kolor gradient
                    )
                )
            )
//                .border(2.dp, Color.Green)
                .fillMaxWidth()
                .padding(16.dp) ){


//            Text(text = "Naklejki", style = TextStyle(fontSize = 24.sp))
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "Liczba naklejek: $countSticker", style = TextStyle(fontSize = 20.sp))
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Cel naklejek: $stickerGoal", style = TextStyle(fontSize = 20.sp))
//            Spacer(modifier = Modifier.height(24.dp))

            StickerGrid(countSticker = countSticker, stickerGoal = stickerGoal)}
        }








        Spacer(modifier = Modifier.weight(1f)) // allign this fucking button to bottom.
        Button(onClick = {
            userId?.let {
                Log.d("QRCode", "Generowanie QR CODE...") //Check, QR generating //TODO Do usuniecia, LOG
                qrBitmap = generateQRCode(it)
                dialogState.value = true
                Log.d("QRCode", "QR CODE Wygenerowany ?: ${qrBitmap != null}") // QR generated?   //TODO Do usuniecia, LOG
            }
        }) {
            Text(text = "Generate your QR Code")
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (dialogState.value) {
            Dialog(
                onDismissRequest = { dialogState.value = false },
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
                content = {
                    qrBitmap?.let { bitmap ->
                        Log.d("QRCode", "Wielkosc tego bydlaka: ${bitmap.width} x ${bitmap.height}") //TODO Do usuniecia, LOG
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
    val rows = (stickerGoal / 5) + if (stickerGoal % 5 != 0) 1 else 0

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
                repeat(5) { columnIndex ->
                    val stickerNumber = (rowIndex * 5) + columnIndex + 1
                    val stickerCollected = stickerNumber <= countSticker
                    StickerIcon(
                        stickerCollected = stickerCollected,
                        stickerNumber = stickerNumber
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StickerIcon(stickerCollected: Boolean, stickerNumber: Int) {
    val stickerImage = if (stickerCollected) {
        R.drawable.baseline_local_cafe_24
    } else {
        R.drawable.baseline_sentiment_very_dissatisfied_24 //TODO: Zmień na odpowiedni obrazek
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = if (stickerCollected) Color(0xFFd4fc79) else Color(0xFFf5576c),
                shape = CircleShape
            )
    ) {
        Image(
            painter = painterResource(id = stickerImage),
            contentDescription = "Sticker $stickerNumber",
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}