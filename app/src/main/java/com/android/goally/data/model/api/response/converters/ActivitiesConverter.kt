package com.android.goally.data.model.api.response.converters

import androidx.room.TypeConverter
import com.android.goally.data.model.api.response.copilot.Activities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivitiesConverter {
    @TypeConverter
    fun listToJson(value: ArrayList<Activities>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): ArrayList<Activities> {
        val listType = object : TypeToken<ArrayList<Activities>>() {}.type
        return Gson().fromJson(value, listType)
    }
}