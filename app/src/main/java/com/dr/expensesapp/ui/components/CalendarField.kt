package com.dr.expensesapp.ui.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarField(labelText: String, onClick: () -> Unit, modifier: Modifier, value: String) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                Log.d("CalendarField", "Text changed: $it")
            },
            //modifier = modifier,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                Log.d("CalendarField", "OutlinedTextField clicked")
                                onClick()
                            }
                        }
                    }
                },
            label = { Text(labelText) },
            //text = { Text(labelText) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "calendarIcon"
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
