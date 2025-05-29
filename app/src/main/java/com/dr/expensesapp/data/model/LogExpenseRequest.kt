package com.dr.expensesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LogExpenseRequest(
    val amount: String,
    val reason: String,
    val category: String,
    val date: String,
)