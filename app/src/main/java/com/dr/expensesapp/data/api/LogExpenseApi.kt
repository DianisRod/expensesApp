package com.dr.expensesapp.data.api

import com.dr.expensesapp.data.model.LogExpenseRequest
import com.dr.expensesapp.data.model.LogExpenseResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

interface LogExpenseApi {

    @POST("expenses")
    suspend fun logExpense(@Body request: LogExpenseRequest): HttpResponse

    @GET("expenses")
    suspend fun getExpenses(): HttpResponse
}