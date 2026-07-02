package com.expenseflow.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.expenseflow.app.data.entity.ExpenseEntity
import com.expenseflow.app.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

    private val _pendingTotal = MutableStateFlow(0.0)
    val pendingTotal: StateFlow<Double> = _pendingTotal.asStateFlow()

    private val _paidTotal = MutableStateFlow(0.0)
    val paidTotal: StateFlow<Double> = _paidTotal.asStateFlow()

    init {
        loadExpenses()
        loadTotals()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            repository.getAllExpenses().collect { expenseList ->
                _expenses.value = expenseList
            }
        }
    }

    private fun loadTotals() {
        viewModelScope.launch {
            repository.getPendingTotal().collect { total ->
                _pendingTotal.value = total ?: 0.0
            }
        }
        viewModelScope.launch {
            repository.getPaidTotal().collect { total ->
                _paidTotal.value = total ?: 0.0
            }
        }
    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }

    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun markAsPaid(expense: ExpenseEntity) {
        viewModelScope.launch {
            val updatedExpense = expense.copy(isPaid = true, updatedAt = System.currentTimeMillis())
            repository.updateExpense(updatedExpense)
        }
    }

    fun searchExpenses(query: String) {
        viewModelScope.launch {
            repository.searchExpenses(query).collect { results ->
                _expenses.value = results
            }
        }
    }
}

class ExpenseViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ExpenseViewModel(repository) as T
    }
}
