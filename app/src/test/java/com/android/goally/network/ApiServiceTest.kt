package com.android.goally.network

import com.android.goally.MainCoroutinesRule
import com.android.goally.data.model.api.ErrorResponse
import com.android.goally.data.model.api.response.copilot.CopilotResponse
import com.android.goally.data.network.rest.api.GeneralApi
import com.haroldadmin.cnradapter.NetworkResponse
import com.testassignment.core.network.api.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ApiServiceTest: ApiAbstract<GeneralApi>() {

    private lateinit var apiService: GeneralApi

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(GeneralApi::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `test load latest copilots returns copilotsData` () = runBlocking {
        enqueueResponse("/copilot_list_response.json")

        // Invoke
        val result = apiService.getCopilots("db922e4debe1e80e")
        mockWebServer.takeRequest()

        val response = result as NetworkResponse.Success

        // Then
        MatcherAssert.assertThat(response.body.routines.size, CoreMatchers.`is`(9))
        MatcherAssert.assertThat(response.body.checklists.size, CoreMatchers.`is`(2))
        MatcherAssert.assertThat(response.body.routines[0].Id, CoreMatchers.`is`("66c5c0b15d6710c478b8aa7c"))
        MatcherAssert.assertThat(response.body.checklists[0].Id, CoreMatchers.`is`("66f265b0ce71508587e497cd"))
    }

    @After
    fun tearDown() {
    }
}