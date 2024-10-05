package com.android.goally.data.model.api.response.copilot

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ScheduleV2(

    @SerializedName("timeType") var timeType: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("dailyRepeatValues") var dailyRepeatValues: DailyRepeatValues? = DailyRepeatValues(),
    @SerializedName("yearlyRepeatDateValue") var yearlyRepeatDateValue: String? = ""

): Parcelable