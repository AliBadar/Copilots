package com.android.goally.data.model.api.response.copilot

import com.google.gson.annotations.SerializedName


data class Schedule (

  @SerializedName("Sun" ) var Sun : String? = null,
  @SerializedName("Mon" ) var Mon : String? = null,
  @SerializedName("Tue" ) var Tue : String? = null,
  @SerializedName("Wed" ) var Wed : String? = null,
  @SerializedName("Thu" ) var Thu : String? = null,
  @SerializedName("Fri" ) var Fri : String? = null,
  @SerializedName("Sat" ) var Sat : String? = null

)