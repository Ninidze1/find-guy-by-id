package com.example.shualeduri.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class IpModel(
    val city: String? = "",
    val country: String? = "",
    val ip: String? = "",
    val loc: String? = "",
    val org: String? = "",
    val region: String? = "",
    val timezone: String? = ""
) {

}