package com.nagl.memesschedule.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    @SerializedName("isCurrentWeekEven")
    val isCurrentWeekEven: Boolean,

    @SerializedName("evenWeek")
    val evenWeek: List<UniPair>,

    @SerializedName("oddWeek")
    val oddWeek: List<UniPair>,
) : Parcelable
