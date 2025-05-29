package com.dr.expensesapp.ui.components

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun ToastExample(context: Context) {
    // Creating a Box layout to display a Button
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Creating a Button and calling the Toast function when clicked
        Button(
            onClick = { displayToast(context) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))
        ) {
            Text(text = "Click", color = Color.White)
        }
    }
}

private fun displayToast(context: Context) {
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}
