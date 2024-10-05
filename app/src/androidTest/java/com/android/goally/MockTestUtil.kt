package com.android.goally

import com.android.goally.data.model.api.ErrorResponse
import com.android.goally.data.model.api.response.copilot.CopilotResponse
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.data.model.api.response.copilot.ScheduleV2
import okio.IOException

class MockTestUtil {

    companion object {
        fun getCopilotsData() = arrayListOf(
            Routines(
                "1",
                "Test 1",
                "https://goally-files-dev.s3.amazonaws.com/visualaids/picture/dessert/carrot_cake.jpeg",
                arrayListOf(),
                ScheduleV2(),
                "OTHER"
            )
        )


        fun getServerError(): ErrorResponse {
            return ErrorResponse(500, "Invalid Api Key")
        }

        fun getNetworkError(): IOException {
            return java.io.IOException("Invalid Api Key")
        }

        fun getNoInternetAndNoDataFoundMessage(): String {
            return NETWORK_OR_DATA_NOT_AVAILABLE
        }

        const val NETWORK_OR_DATA_NOT_AVAILABLE =
            "Network is not available and no local data found."

    }
}