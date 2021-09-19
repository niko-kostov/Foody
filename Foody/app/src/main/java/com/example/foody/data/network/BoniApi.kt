package com.example.foody.data.network

import com.example.foody.models.AccountLoginRequest
import com.example.foody.models.AccountLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BoniApi {

    @POST("/api/auth/signIn")
    suspend fun loginUser(
        @Body loginRequest: AccountLoginRequest
    ): Response<AccountLoginResponse>
}