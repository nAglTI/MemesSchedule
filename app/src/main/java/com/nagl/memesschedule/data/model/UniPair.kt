package com.nagl.memesschedule.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniPair(

    @SerializedName("dayNumber")
    val dayNumber: Int,

    @SerializedName("pairNumber")
    val pairNumber: Int,

    @SerializedName("className")
    val pairName: String,

    @SerializedName("cabinetNumber")
    val cabinetNumber: String,

    @SerializedName("teacherName")
    val teacherName: String,
) : Parcelable

// Json data example
//"dayNumber": 1,
//"pairNumber": 5,
//"className": " ДОТ ТИВ, Миловидова А.А. ",
//"cabinetNumber": "",
//"teacherName": "",
//"classType": "",
//"classTime": "",
//"group": "5014"