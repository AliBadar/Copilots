package com.android.goally.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goally.constants.AppConstant.Companion.TOKEN
import com.android.goally.constants.WebServiceConstant.AUTHORIZATION
import com.android.goally.data.model.api.FilterData
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.data.repo.GeneralRepo
import com.android.goally.ui.viewmodels.mapper.GeneralDataMapper.mapRoutinesData
import com.android.goally.ui.viewmodels.mapper.GeneralDataMapper.setDaysForRoutines
import com.android.goally.util.LogUtil
import com.android.goally.util.PreferenceUtil
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    private val generalRepo: GeneralRepo,
    private val preferenceUtil: PreferenceUtil
) : ViewModel() {

    fun checkServerHealth(
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.checkHealth()) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    if (res.body?.status.equals("ok", true)) {
                        onSuccess("Server is up")
                    } else {
                        onError("Server is down")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }

    fun getTokenFor(
        userEmail: String,
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            when (val res = generalRepo.getToken(userEmail)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    res.body?.let {
                        if (!it.token.isNullOrEmpty() && !it.name.isNullOrEmpty()) {
                            //save token here which will be used for further api calls
                            onSuccess(it.token.toString())
                        }
                    } ?: run {
                        onError("Something went wrong")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }

    fun getCopilots(
        onLoading: (Boolean) -> Unit,
        onError: (String) -> Unit,
        onSuccess: (List<Routines>, List<FilterData>, List<FilterData>) -> Unit
    ) {
        onLoading(true)
        viewModelScope.launch {
            val localRoutines = getLocalRoutines();
            if (localRoutines.isNotEmpty()) {

                mapRoutinesData(localRoutines, onSuccess)

            }
            when (val res =
                generalRepo.getCopilots(preferenceUtil.getString(TOKEN) ?: AUTHORIZATION)) {
                is NetworkResponse.Success -> {
                    LogUtil.i(res.body.toString())
                    res.body.let { it ->

                        val copilotsList = mutableListOf<Routines>()

                        if (it.routines.isNotEmpty()) {
                            copilotsList.addAll(it.routines)
                        }

                        if (it.checklists.isNotEmpty()) {
                            copilotsList.addAll(it.checklists)
                        }

                        copilotsList.let { copilotsList ->
                            copilotsList.sortBy {
                                it.name
                            }

                            mapRoutinesData(copilotsList, onSuccess)

                            generalRepo.addLocalRoutines(copilotsList)

                        }

                    } ?: run {
                        onError("Something went wrong")
                    }
                    onLoading(false)
                }

                is NetworkResponse.ServerError -> {
                    LogUtil.e(res.code.toString())
                    LogUtil.e(res.body?.message)
                    if (localRoutines.isEmpty())
                        onError(res.body?.message ?: "Server error")
                    onLoading(false)
                }

                is NetworkResponse.NetworkError -> {
                    res.error.printStackTrace()
                    if (localRoutines.isEmpty())
                        onError(res.error.message ?: "Network error")
                    onLoading(false)
                }

                is NetworkResponse.UnknownError -> {
                    res.error.printStackTrace()
                    if (localRoutines.isEmpty())
                        onError(res.error.message ?: "Unknown error")
                    onLoading(false)
                }
            }
        }
    }




    private suspend fun getLocalRoutines() = generalRepo.getLocalRoutines()


    fun getAuthenticationLive() = generalRepo.getAuthenticationLive()
    suspend fun getAuthentication() = generalRepo.getAuthentication()
}