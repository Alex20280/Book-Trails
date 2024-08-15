package com.psfilter.feature_auth_module.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Profile(
    val id: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val displayName: String? = "",
    val lastLogIn: Date? = null,
    val registeredSince: Date? = null,
    val userState: String = UserState.NONE.value,
    val signInModel: String = SignInModel.EMAIL.value
) : Parcelable