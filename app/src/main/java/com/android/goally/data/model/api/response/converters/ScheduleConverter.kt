package com.android.goally.data.model.api.response.converters

import androidx.room.TypeConverter
import com.android.goally.data.model.api.response.copilot.ScheduleV2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScheduleConverter {

    @TypeConverter
    fun fromScheduleV2(schedule: ScheduleV2?): String? {
        return Gson().toJson(schedule)
    }

    @TypeConverter
    fun toScheduleV2(scheduleString: String?): ScheduleV2? {
        val type = object : TypeToken<ScheduleV2>() {}.type
        return Gson().fromJson(scheduleString, type)
    }

}