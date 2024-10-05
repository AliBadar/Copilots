package com.android.goally.data.repo

import com.android.goally.data.db.dao.GeneralDao
import com.android.goally.data.network.rest.api.GeneralApi
import com.haroldadmin.cnradapter.NetworkResponse
import com.android.goally.MockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test


class GeneralRepoTest {

    lateinit var sut: GeneralRepo

    @MockK
    lateinit var generalApi: GeneralApi

    @MockK
    lateinit var generalDao: GeneralDao


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GeneralRepo(generalApi, generalDao)
    }


    @Test
    fun `get copilots should return success response from remote server`() = runBlocking {
        val givenPilotsData = MockTestUtil.getCopilotsData()

        coEvery {
            generalApi.getCopilots("QERY12&#%")
        }.returns(NetworkResponse.Success(givenPilotsData, code = 200))

        val result = sut.getCopilots("QERY12&#%")

        val response = result as NetworkResponse.Success

        MatcherAssert.assertThat(result, CoreMatchers.`is`(NetworkResponse.Success(givenPilotsData, code = 200)))
        MatcherAssert.assertThat(response.code, CoreMatchers.`is`(200))
        MatcherAssert.assertThat(response.body.routines, CoreMatchers.`is`(givenPilotsData.routines))
    }

    @Test
    fun `get copilots should return error from remote server`() = runBlocking {

        val givenError = MockTestUtil.getServerError()

        coEvery {
            generalApi.getCopilots("QERY12&#%")
        }.returns(NetworkResponse.ServerError(body = givenError, code = givenError.status))

        val result = sut.getCopilots("QERY12&#%")

        val errorResponse = result as NetworkResponse.ServerError

        MatcherAssert.assertThat(result, CoreMatchers.`is`(NetworkResponse.ServerError(givenError, 500)))
        MatcherAssert.assertThat(errorResponse.body?.message, CoreMatchers.`is`(givenError.message))
        MatcherAssert.assertThat(errorResponse.body?.status, CoreMatchers.`is`(500))
    }

    @Test
    fun `get copilots should return error from network`() = runBlocking {

        val givenError = MockTestUtil.getNetworkError()

        coEvery {
            generalApi.getCopilots("QERY12&#%")
        }.returns(NetworkResponse.NetworkError(givenError))

        val result = sut.getCopilots("QERY12&#%")

        val errorResponse = result as NetworkResponse.NetworkError

        MatcherAssert.assertThat(result, CoreMatchers.`is`(NetworkResponse.NetworkError(givenError)))
        MatcherAssert.assertThat(errorResponse.error.message, CoreMatchers.`is`(givenError.message))
    }

}
