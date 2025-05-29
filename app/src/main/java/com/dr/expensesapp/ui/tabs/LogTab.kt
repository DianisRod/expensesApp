package com.dr.expensesapp.ui.tabs


import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.dr.expensesapp.data.model.LogExpenseRequest
import com.dr.expensesapp.data.model.LogExpenseResponse
import com.dr.expensesapp.data.repository.ExpenseRepository
import com.dr.expensesapp.data.repository.NetworkModule
import com.dr.expensesapp.ui.components.CalendarField
import com.dr.expensesapp.ui.components.CurrencyTextField
import com.dr.expensesapp.ui.components.ExposedDropdownMenuBox
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LogTab() {
    var amount = remember { mutableStateOf(0.0) }
    val reasonFieldValue = remember { mutableStateOf("") }

    var showCalendar by remember { mutableStateOf(false) }
    SimpleDateFormat("yyyy-MM-dd")
    var selectedDate by remember { mutableStateOf(Date().time) }
    var expanded by remember { mutableStateOf(false) }
    val categoryValues : Map<String, Int> = mapOf (
        "groceries" to 1,
        "cosmetics" to 2,
        "fun" to 3,
        "misc" to 4,
        "purchases" to 5,
        "presents" to 6,
        "travelling" to 7,
        "coffee" to 8
    )
    val categories =categoryValues.keys.toList()
    var selectedOptionText by remember { mutableStateOf(categories[0]) }
    val categoryFieldState = rememberTextFieldState(categories[0])

    val modifier = Modifier
        .padding(horizontal = 20.dp)
        .height(80.dp)
        .wrapContentSize(align = Alignment.CenterStart)
        .fillMaxWidth()
    CurrencyTextField(
        onChange = {
            var amountVal = 0.0
            if(!it.isNullOrBlank()){
                if(it.startsWith("€")){
                    amountVal=it.substring(1).toDouble()
                }
                try {if(!it.isNullOrBlank()){
                    it.substring(1).toDouble()}
                }catch (e: NumberFormatException) {
                    Log.e("CurrencyTextField", "Invalid amount format: ${e.message}")
                }
            }
            amount.value = amountVal
        },
        currencySymbol = "€",
        //limit = 10000,
        errorColor = Color.Red,
        locale = Locale.getDefault(),
        initialText = "",
        maxNoOfDecimal = 2,
        errorText = "Sample error text",
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("amount") },
        modifier = modifier
    )

    OutlinedTextField(
        value = reasonFieldValue.value,
        onValueChange = { reasonFieldValue.value = it },
        modifier = modifier,
        label = { Text("reason") },
    )
    ExposedDropdownMenuBox(
        onExpandedChange = { expanded = it },
        modifier = modifier,
        options = categories,
        selectedOption = selectedOptionText,
        onOptionSelected = {
            selectedOptionText = it
                           },
        textFieldState = categoryFieldState,
        expanded = expanded
    )
    ShowDatePicker(
        showCalendar = showCalendar,
        onDismiss = { showCalendar = false },
        onDateSelected = { millis ->
            processSelectedDate(millis) { selectedDate = it }
        })
    CalendarField(
        onClick = { showCalendar = !showCalendar },
        labelText = "Date",
        value = convertTimestampToDate(selectedDate),
        modifier = modifier
    )

    val repository = remember { ExpenseRepository(NetworkModule.ktorfit) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    OutlinedButton(
        onClick = {
            if (amount.value.toString().isBlank() || amount.value <= 0) {
                Toast.makeText(context, "Please enter an amount", Toast.LENGTH_LONG).show()
                return@OutlinedButton
            }
            if (selectedDate.toString().isBlank()) {
                Toast.makeText(context, "Please select a date", Toast.LENGTH_LONG).show()
                return@OutlinedButton
            }

            val category : Int =categoryValues.getValue(selectedOptionText)
            coroutineScope.launch {
                val request = LogExpenseRequest(
                    amount = amount.value.toString(),
                    reason = reasonFieldValue.value,
                    category = category.toString(),
                    date = selectedDate.toString()
                )
                val response : Pair<Int, LogExpenseResponse?>?  = repository.logExpense(request)
                println("Token: ${response?.second?.reason}")
                Toast.makeText(context, "Expense logged!", Toast.LENGTH_LONG).show()
            }
        },
        modifier = modifier
    ) {
        Text("Log expense")
    }
}

@Composable
private fun ShowDatePicker(
    showCalendar: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    if (showCalendar) {
        Dialog(onDismissRequest = { onDismiss() }) {
            AndroidView(
                { CalendarView(it) },
                modifier = Modifier
                    .wrapContentWidth()
                    .background(Color.White),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val calendar = java.util.Calendar.getInstance()
                        calendar.set(year, month, dayOfMonth)
                        Log.d("DatePicker", "Selected date: $dayOfMonth/${month + 1}/$year")
                        onDateSelected(calendar.timeInMillis)
                        onDismiss()
                    }
                }
            )
        }
    }
}

private fun convertTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

private fun processSelectedDate(selectedMillis: Long, setSelectedDate: (Long) -> Unit): String {
    setSelectedDate(selectedMillis)
    val date = Date(selectedMillis)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}