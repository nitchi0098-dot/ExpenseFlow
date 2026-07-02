package com.expenseflow.app.data.dao

import androidx.room.*
import com.expenseflow.app.data.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity)

    @Update
    suspend fun updateIncome(income: IncomeEntity)

    @Delete
    suspend fun deleteIncome(income: IncomeEntity)

    @Query("SELECT * FROM income WHERE month = :month AND year = :year")
    fun getIncomeByMonth(month: Int, year: Int): Flow<IncomeEntity?>

    @Query("SELECT * FROM income ORDER BY year DESC, month DESC")
    fun getAllIncome(): Flow<List<IncomeEntity>>
}
