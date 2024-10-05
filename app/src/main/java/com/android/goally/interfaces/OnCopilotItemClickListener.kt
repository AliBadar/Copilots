package com.android.goally.interfaces

import com.android.goally.data.model.api.response.copilot.Routines


interface OnCopilotItemClickListener {
    fun onItemClick(routines: Routines)
}