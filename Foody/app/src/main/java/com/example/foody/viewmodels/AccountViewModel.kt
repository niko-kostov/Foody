package com.example.foody.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.foody.data.DataStoreRepository
import com.example.foody.data.Repository
import com.example.foody.models.AccountLoginRequest
import com.example.foody.models.AccountLoginResponse
import com.example.foody.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository,
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var isUserLoggedIn = dataStoreRepository.readIsUserLoggedIn.asLiveData()

    var loggedInUserResponse: MutableLiveData<NetworkResult<AccountLoginResponse>> = MutableLiveData()

    suspend fun loginUser(email: String, password: String) {
        loggedInUserResponse.value = NetworkResult.Loading()
        try {
            val response = repository.remote.loginUser(AccountLoginRequest(email = email, password = password))

            loggedInUserResponse.value = handleLoggedInUserResponse(response)
        } catch (e: Exception) {
            loggedInUserResponse.value = NetworkResult.Error("Login invalid.")
        }
    }

    private suspend fun handleLoggedInUserResponse(response: Response<AccountLoginResponse>): NetworkResult<AccountLoginResponse>? {
        return when {
            response.code() == 400 -> {
                NetworkResult.Error("Bad request.")
            }
            response.isSuccessful -> {
                val loginUser = response.body()!!
                val token = loginUser.tokenType + loginUser.accessToken
                dataStoreRepository.saveLoginCredentials(token, loginUser.fullName, loginUser.email, loginUser.phoneNumber, true)
                NetworkResult.Success(loginUser)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }


}