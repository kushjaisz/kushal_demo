package com.example.kushal_demo.data.localDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingEntity::class], version = 1)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun holdingDao(): HoldingDao
}
