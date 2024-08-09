package com.psfilter.feature_auth_module.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class UserState (val value: String)  : Parcelable {
    NONE ("None"), SUBSCRIBED("Subscribed"), UNSUBSCRIBED("Unsubscribed")
}