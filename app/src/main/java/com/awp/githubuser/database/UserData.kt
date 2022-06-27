package com.awp.githubuser.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val id: Int,
    val login: String,
    val avatar_url: String
) : Parcelable
