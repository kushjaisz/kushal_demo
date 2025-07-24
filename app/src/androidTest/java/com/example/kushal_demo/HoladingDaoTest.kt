package com.example.kushal_demo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.kushal_demo.data.localDB.HoldingDao
import com.example.kushal_demo.data.localDB.HoldingEntity
import com.example.kushal_demo.data.localDB.PortfolioDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HoldingDaoTest {

    private lateinit var db: PortfolioDatabase
    private lateinit var dao: HoldingDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PortfolioDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.holdingDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert() = runTest {
        val entity = HoldingEntity("TCS", 10.0, 3500.0, 3400.0, 3300.0)
        dao.insertHoldings(listOf(entity))

        val result = dao.getHoldings()
        assertEquals(1, result.size)
        assertEquals("TCS", result[0].name)
    }


    @Test
    fun insert_and_update() = runTest {
        val initial = HoldingEntity("TCS", 10.0, 3500.0, 3400.0, 3300.0)
        val updated = HoldingEntity("TCS", 20.0, 3600.0, 3550.0, 3400.0)

        dao.insertHoldings(listOf(initial))
        dao.insertHoldings(listOf(updated))

        val result = dao.getHoldings()
        assertEquals(1, result.size)
        assertEquals(20.0, result[0].quantity)
        assertEquals(3600.0, result[0].avgPrice)
    }


    @Test
    fun empty_result() = runTest {
        val result = dao.getHoldings()
        assertEquals(0, result.size)
    }


}
