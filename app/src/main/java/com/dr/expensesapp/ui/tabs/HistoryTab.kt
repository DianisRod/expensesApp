package com.dr.expensesapp.ui.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dr.expensesapp.data.model.LogExpenseResponse
import com.dr.expensesapp.data.repository.ExpenseRepository
import com.dr.expensesapp.data.repository.NetworkModule
import org.json.JSONException

@Composable
fun HistoryTab() {
    val repository = remember { ExpenseRepository(NetworkModule.ktorfit) }
    var expenses by remember { mutableStateOf<List<LogExpenseResponse>>(emptyList()) }
    rememberCoroutineScope()
    Modifier
        .padding(horizontal = 20.dp)
        .height(80.dp)
        .wrapContentSize(align = Alignment.CenterStart)
        .fillMaxWidth()

    LaunchedEffect(Unit) {
        val result = repository.getExpenses()
        expenses = result?.second ?: emptyList()
    }

    val expensesString = prettyPrint(expenses)

    Column(modifier = Modifier
    .padding(horizontal = 20.dp)
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Text(text = expensesString)
        }
    }
}

fun prettyPrint(expenses: List<LogExpenseResponse>) : String {
    val expensesString = expenses.joinToString(separator = "\n\n") {
        try {
            it.prettyPrint() ?: ""
//            val tmp=JSONObject(it.toString())
//                tmp.toString(4) // 4 is the indent factor
        } catch (e: JSONException) {
            it.toString()
        }
    }
    return expensesString
}
