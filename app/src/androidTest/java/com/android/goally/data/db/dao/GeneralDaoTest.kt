//package com.android.goally.data.db.dao
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import com.android.goally.MockTestUtil
//import com.android.goally.data.db.AppDatabase
//import kotlinx.coroutines.runBlocking
//import org.hamcrest.CoreMatchers
//import org.hamcrest.MatcherAssert
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class GeneralDaoTest {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var dao: GeneralDao
//    private lateinit var database: AppDatabase
//
//    @Before
//    fun setUp() {
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            AppDatabase::class.java
//        ).build()
//        dao = database.getGeneralDao()
//    }
//
//    @Test
//    fun saveCoPilotsInDataBaseShouldSucceed(): Unit = runBlocking{
//        val mockData = MockTestUtil.getCopilotsData()
//        dao.addRoutines(mockData)
//
//        val allRates = dao.getRoutines()
//        MatcherAssert.assertThat(allRates, CoreMatchers.notNullValue())
//        MatcherAssert.assertThat(allRates, CoreMatchers.`is`(mockData))
//        MatcherAssert.assertThat(allRates.size, CoreMatchers.`is`(mockData.size))
//    }
//
//    @After
//    fun tearDown() {
//        database.close()
//    }
//
//}