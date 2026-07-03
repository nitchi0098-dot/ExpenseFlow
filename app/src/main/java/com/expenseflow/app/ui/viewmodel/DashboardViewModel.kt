package com.expenseflow.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.expenseflow.app.data.entity.IncomeEntity
import com.expenseflow.app.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class DashboardViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _monthlyIncome = MutableStateFlow(0.0)
    val monthlyIncome: StateFlow<Double> = _monthlyIncome.asStateFlow()

    private val _pendingExpenses = MutableStateFlow(0.0)
    val pendingExpenses: StateFlow<Double> = _pendingExpenses.asStateFlow()

    private val _paidExpenses = MutableStateFlow(0.0)
    val paidExpenses: StateFlow<Double> = _paidExpenses.asStateFlow()

    private val _remainingBalance = MutableStateFlow(0.0)
    val remainingBalance: StateFlow<Double> = _remainingBalance.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        viewModelScope.launch {
            repository.getIncomeByMonth(currentMonth, currentYear).collect { income ->
                _monthlyIncome.value = income?.amount ?: 0.0
                updateBalance()
            }
        }

        viewModelScope.launch {
            repository.getPendingTotal().collect { total ->
                _pendingExpenses.value = total ?: 0.0
                updateBalance()
            }
        }

        viewModelScope.launch {
            repository.getPaidTotal().collect { total ->
                _paidExpenses.value = total ?: 0.0
                updateBalance()
            }
        }
    }

    private fun updateBalance() {
        _remainingBalance.value = _monthlyIncome.value - _paidExpenses.value
    }

    fun setMonthlyIncome(income: Double) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH) + 1
            val year = calendar.get(Calendar.YEAR)
            val incomeEntity = IncomeEntity(amount = income, month = month, year = year)
            repository.addIncome(incomeEntity)
        }
    }
}

class DashboardViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DashboardViewModel(repository) as T
    }
}
