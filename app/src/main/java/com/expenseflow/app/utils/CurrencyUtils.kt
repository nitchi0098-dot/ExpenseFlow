package com.expenseflow.app.utils

obj CurrencyUtils {
    fun formatAmount(amount: Double): String {
        return "₹%.2f".format(amount)
    }

    fun parseAmount(text: String): Double {
        return text.replace("₹", "").trim().toDoubleOrNull() ?: 0.0
    }
}
