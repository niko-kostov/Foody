package com.example.foody.models

import com.google.gson.annotations.SerializedName

data class AccountLoginRequest (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)