package com.expenseflow.app.data.repository

import com.expenseflow.app.data.dao.CategoryDao
import com.expenseflow.app.data.dao.ExpenseDao
import com.expenseflow.app.data.dao.IncomeDao
import com.expenseflow.app.data.entity.CategoryEntity
import com.expenseflow.app.data.entity.ExpenseEntity
import com.expenseflow.app.data.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao,
    private val incomeDao: IncomeDao
) {
    // Expense operations
    suspend fun addExpense(expense: ExpenseEntity) = expenseDao.insertExpense(expense)
    suspend fun updateExpense(expense: ExpenseEntity) = expenseDao.updateExpense(expense)
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    
    fun getExpenseById(id: Int) = expenseDao.getExpenseById(id)
    fun getAllExpenses() = expenseDao.getAllExpenses()
    fun getExpensesByCategory(category: String) = expenseDao.getExpensesByCategory(category)
    fun getExpensesByStatus(isPaid: Boolean) = expenseDao.getExpensesByStatus(isPaid)
    fun searchExpenses(query: String) = expenseDao.searchExpenses(query)
    fun getExpensesByMonth(month: String, year: String) = expenseDao.getExpensesByMonth(month, year)
    fun getPendingTotal() = expenseDao.getPendingTotal()
    fun getPaidTotal() = expenseDao.getPaidTotal()
    fun getCategoryTotal(category: String) = expenseDao.getCategoryTotal(category)
    
    // Category operations
    suspend fun addCategory(category: CategoryEntity) = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)
    
    fun getCategoryById(id: Int) = categoryDao.getCategoryById(id)
    fun getAllCategories() = categoryDao.getAllCategories()
    
    // Income operations
    suspend fun addIncome(income: IncomeEntity) = incomeDao.insertIncome(income)
    suspend fun updateIncome(income: IncomeEntity) = incomeDao.updateIncome(income)
    suspend fun deleteIncome(income: IncomeEntity) = incomeDao.deleteIncome(income)
    
    fun getIncomeByMonth(month: Int, year: Int) = incomeDao.getIncomeByMonth(month, year)
    fun getAllIncome() = incomeDao.getAllIncome()
}
