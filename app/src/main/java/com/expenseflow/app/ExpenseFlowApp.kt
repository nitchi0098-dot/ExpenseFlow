package com.expenseflow.app

import android.app.Application
import androidx.room.Room
import com.expenseflow.app.data.database.ExpenseFlowDatabase
import com.expenseflow.app.data.repository.ExpenseRepository

class ExpenseFlowApp : Application() {
    companion object {
        lateinit var database: ExpenseFlowDatabase
        lateinit var repository: ExpenseRepository
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Room Database
        database = Room.databaseBuilder(
            applicationContext,
            ExpenseFlowDatabase::class.java,
            "expense_flow_db"
        ).build()
        
        // Initialize Repository
        repository = ExpenseRepository(
            database.expenseDao(),
            database.categoryDao(),
            database.incomeDao()
        )
    }
}
