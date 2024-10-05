package com.android.goally.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.goally.MainCoroutinesRule
import com.android.goally.MockTestUtil
import com.android.goally.constants.AppConstant.Companion.TOKEN
import com.android.goally.data.model.api.FilterData
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.data.repo.GeneralRepo
import com.android.goally.util.PreferenceUtil
import com.haroldadmin.cnradapter.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GeneralViewModelTest {

    private lateinit var sut: GeneralViewModel

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var generalRepo: GeneralRepo

    @MockK
    lateinit var preferenceUtil: PreferenceUtil

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GeneralViewModel(generalRepo, preferenceUtil)
    }

    @Test
    fun `test when generalViewModel is initialized, success is returned when copilots data is fetched`() = runBlocking {
        val givenPilotsData = MockTestUtil.getCopilotsData()
        val token = "test_token"
        every { preferenceUtil.getString(TOKEN) } returns token
        coEvery { generalRepo.getLocalRoutines() } returns givenPilotsData.routines
        coEvery { generalRepo.getCopilots(token) } returns NetworkResponse.Success(givenPilotsData, code = 200)
        coEvery { generalRepo.addLocalRoutines(any()) } just Runs

        val onLoading: (Boolean) -> Unit = mockk(relaxed = true)
        val onError: (String) -> Unit = mockk(relaxed = true)
        val onSuccess: (List<Routines>, List<FilterData>, List<FilterData>) -> Unit = mockk(relaxed = true)

        sut.getCopilots(onLoading, onError, onSuccess)

        verify { onLoading(true) }
        coVerify { onSuccess(any(), any(), any()) }
        verify { onLoading(false) }
        verify(exactly = 0) { onError(any()) }
    }

    @Test
    fun `test when generalViewModel is initialized, error is returned when copilots data is fetched`() = runBlocking {
        val token = "test_token"
        val errorResponse = MockTestUtil.getServerError()

        every { preferenceUtil.getString(TOKEN) } returns token
        coEvery { generalRepo.getLocalRoutines() } returns emptyList()
        coEvery { generalRepo.getCopilots(token) } returns NetworkResponse.ServerError(errorResponse, 500)
        coEvery { generalRepo.addLocalRoutines(any()) } just Runs
        // Mocking callback lambdas
        val onLoading: (Boolean) -> Unit = mockk(relaxed = true)
        val onError: (String) -> Unit = mockk(relaxed = true)
        val onSuccess: (List<Routines>, List<FilterData>, List<FilterData>) -> Unit = mockk(relaxed = true)

        // Call the function being tested
        sut.getCopilots(onLoading, onError, onSuccess)

        // Verifications
        verify { onLoading(true) }
        verify { onLoading(false) }
        verify(exactly = 0) { onSuccess(any(), any(), any()) }
        coVerify { onError(errorResponse.message) }
    }
}