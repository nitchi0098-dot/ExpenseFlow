package com.expenseflow.app.data.dao

import androidx.room.*
import com.expenseflow.app.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int): Flow<ExpenseEntity?>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE isPaid = :isPaid ORDER BY date DESC")
    fun getExpensesByStatus(isPaid: Boolean): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE title LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchExpenses(query: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE strftime('%m', datetime(date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(date/1000, 'unixepoch')) = :year ORDER BY date DESC")
    fun getExpensesByMonth(month: String, year: String): Flow<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM expenses WHERE isPaid = 0")
    fun getPendingTotal(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM expenses WHERE isPaid = 1")
    fun getPaidTotal(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM expenses WHERE category = :category")
    fun getCategoryTotal(category: String): Flow<Double?>
}
