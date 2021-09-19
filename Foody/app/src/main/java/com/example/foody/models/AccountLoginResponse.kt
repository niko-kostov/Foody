package com.example.foody.models

import com.google.gson.annotations.SerializedName

data class AccountLoginResponse (
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("tokenType")
    val tokenType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("profileImage")
    val profileImage: String,
)