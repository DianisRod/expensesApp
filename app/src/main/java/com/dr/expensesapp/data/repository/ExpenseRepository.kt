package com.dr.expensesapp.data.repository

import android.util.Log
import com.dr.expensesapp.data.api.createLogExpenseApi
import com.dr.expensesapp.data.model.LogExpenseRequest
import com.dr.expensesapp.data.model.LogExpenseResponse
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

class ExpenseRepository(
    private val ktorfit: Ktorfit
) {
    private val logExpenseApi = ktorfit.createLogExpenseApi()

    suspend fun logExpense(request: LogExpenseRequest): Pair<Int, LogExpenseResponse?>? {
        return try {
            val response : HttpResponse =  logExpenseApi.logExpense(request)
            val statusCode = response.status.value
            val logExpenseResponse : LogExpenseResponse = response.body<LogExpenseResponse>()
            Pair(statusCode, logExpenseResponse)
        } catch (e: Exception) {
            Pair(-1, null)
        }
    }

    suspend fun getExpenses(): Pair<Int, List<LogExpenseResponse>?>?  {
        return try {
            val response =  logExpenseApi.getExpenses()
            val statusCode = response.status.value
            val body = response.body<List<LogExpenseResponse>>()
            Pair(statusCode, body)
        } catch (e: Exception) {
            Log.d("error", "Error fetching expenses: ${e.message}")
            null
        }
    }
}