package com.android.goally.data.repo

import com.android.goally.data.db.dao.GeneralDao
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.data.network.rest.api.GeneralApi


class GeneralRepo(
    private val generalApi: GeneralApi,
    private val generalDao: GeneralDao,
) {

    suspend fun checkHealth() = generalApi.checkHealth()
    suspend fun getToken(userEmail:String) = generalApi.getToken(userEmail)

    suspend fun getCopilots(token: String) = generalApi.getCopilots(token)


    suspend fun getLocalRoutines() = generalDao.getRoutines()
    suspend fun addLocalRoutines(routines: List<Routines>) = generalDao.addRoutines(routines)

    fun getAuthenticationLive() = generalDao.getAuthenticationLive()
    suspend fun getAuthentication() = generalDao.getAuthentication()

}