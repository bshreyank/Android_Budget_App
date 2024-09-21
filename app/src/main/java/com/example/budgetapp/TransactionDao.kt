package com.example.budgetapp

import androidx.room.*

@Dao
interface TransactionDao {
    @Query("SELECT * from transactions")
    suspend fun getAll(): List<Transaction>

    @Insert
    suspend fun insertAll(vararg transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Update
    suspend fun update(vararg transaction: Transaction)
}
