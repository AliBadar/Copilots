package com.android.goally.data.model.api.response.copilot

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "routines")
data class Routines(

    @ColumnInfo
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @SerializedName("_id") var Id: String,

    @ColumnInfo
    @SerializedName("name") var name: String? = null,

    @ColumnInfo
    @SerializedName("imgURL") var imgURL: String? = null,

    @ColumnInfo
    @SerializedName("activities") var activities: ArrayList<Activities> = arrayListOf(),


    @ColumnInfo
    @SerializedName("scheduleV2") var scheduleV2: ScheduleV2? = ScheduleV2(),

    @ColumnInfo
    @SerializedName("folder") var folder: String? = null,
    var days: String? = null
) : Parcelable