package com.android.goally.data.model.api.response.copilot

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Activities (
  @ColumnInfo
  @SerializedName("name"      ) var name      : String? = null,

  @ColumnInfo
  @SerializedName("ordering"  ) var ordering  : Int?    = null,

  @ColumnInfo
  @SerializedName("audioType" ) var audioType : String? = null,

  @ColumnInfo
  @SerializedName("imgURL" ) var imgURL : String? = null,

  @ColumnInfo
  @SerializedName("_id"       ) var Id        : String? = null

): Parcelable