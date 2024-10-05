package com.android.goally.constants

object WebServiceConstant {
    const val AUTHORIZATION = "db922e4debe1e80e"

    const val BASE_URL: String = "https://devapi.getgoally.com/"
    private const val API_VERSION= "v1/api/"

    const val CHECK_HEALTH = API_VERSION + "health"
    const val GET_TOKEN = API_VERSION + "devices/get-token"
    const val GET_COPILOTS = API_VERSION + "devices/copilot-list"
}