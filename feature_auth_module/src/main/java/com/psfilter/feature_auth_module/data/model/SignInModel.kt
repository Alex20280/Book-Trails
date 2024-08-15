package com.psfilter.feature_auth_module.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
enum class SignInModel (val value: String)  : Parcelable {
    EMAIL ("Email/Password"), GMAIL("Gmail"), FACEBOOK("Facebook")
}