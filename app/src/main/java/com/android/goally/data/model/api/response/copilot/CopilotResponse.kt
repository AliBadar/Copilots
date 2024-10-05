package com.android.goally.data.model.api.response.copilot

import com.google.gson.annotations.SerializedName


data class CopilotResponse (
  @SerializedName("routines"   ) var routines   : ArrayList<Routines>   = arrayListOf(),
  @SerializedName("checklists" ) var checklists : ArrayList<Routines> = arrayListOf()
)