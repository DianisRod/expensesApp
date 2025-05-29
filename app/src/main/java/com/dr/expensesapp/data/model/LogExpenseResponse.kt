package com.dr.expensesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LogExpenseResponse(
        val amount: String,
        val reason: String,
        val category: String,
        val date: String,
) {
        fun prettyPrint() : String {
                return "Expense: $amount,\n" +
                        "Reason: $reason,\n" +
                        "Category: $category,\n" +
                        "Date: $date"
        }
}