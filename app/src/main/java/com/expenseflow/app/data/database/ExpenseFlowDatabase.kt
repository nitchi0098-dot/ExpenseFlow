package com.expenseflow.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.expenseflow.app.data.dao.CategoryDao
import com.expenseflow.app.data.dao.ExpenseDao
import com.expenseflow.app.data.dao.IncomeDao
import com.expenseflow.app.data.entity.CategoryEntity
import com.expenseflow.app.data.entity.ExpenseEntity
import com.expenseflow.app.data.entity.IncomeEntity

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, IncomeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseFlowDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun incomeDao(): IncomeDao
}
