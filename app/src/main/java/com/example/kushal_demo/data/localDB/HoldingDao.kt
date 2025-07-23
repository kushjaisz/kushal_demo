package com.example.kushal_demo.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HoldingDao {
    @Query("SELECT * FROM HoldingEntity")
    suspend fun getHoldings(): List<HoldingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldings(list: List<HoldingEntity>)
}
