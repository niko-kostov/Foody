package com.example.foody.data

import com.example.foody.data.network.BoniApi
import com.example.foody.data.network.FoodRecipesApi
import com.example.foody.di.ApiService1
import com.example.foody.di.ApiService2
import com.example.foody.models.AccountLoginRequest
import com.example.foody.models.AccountLoginResponse
import com.example.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @ApiService1 private val foodRecipesApi: FoodRecipesApi,
    @ApiService2 private val boniApi: BoniApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun loginUser(loginRequest: AccountLoginRequest): Response<AccountLoginResponse> {
        return boniApi.loginUser(loginRequest)
    }
}