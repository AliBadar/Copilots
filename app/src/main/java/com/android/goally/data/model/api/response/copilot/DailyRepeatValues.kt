package com.android.goally.data.model.api.response.copilot

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DailyRepeatValues (

  @SerializedName("Sun" ) var Sun : ArrayList<String> = arrayListOf(),
  @SerializedName("Mon" ) var Mon : ArrayList<String> = arrayListOf(),
  @SerializedName("Thu" ) var Thu : ArrayList<String> = arrayListOf(),
  @SerializedName("Wed" ) var Wed : ArrayList<String> = arrayListOf(),
  @SerializedName("Tue" ) var Tue : ArrayList<String> = arrayListOf(),
  @SerializedName("Fri" ) var Fri : ArrayList<String> = arrayListOf(),
  @SerializedName("Sat" ) var Sat : ArrayList<String> = arrayListOf()

): Parcelable