package com.example.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_EMAIL
import com.example.foody.util.Constants.Companion.PREFERENCES_FULL_NAME
import com.example.foody.util.Constants.Companion.PREFERENCES_LOGGED_IN_USER
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_NAME
import com.example.foody.util.Constants.Companion.PREFERENCES_PHONE_NUMBER
import com.example.foody.util.Constants.Companion.PREFERENCES_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val isUserLoggedIn = booleanPreferencesKey(PREFERENCES_LOGGED_IN_USER)
        val token = stringPreferencesKey(PREFERENCES_TOKEN)
        val fullName = stringPreferencesKey(PREFERENCES_FULL_NAME)
        val email = stringPreferencesKey(PREFERENCES_EMAIL)
        val phoneNumber = stringPreferencesKey(PREFERENCES_PHONE_NUMBER)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    suspend fun saveLoginCredentials(
        token: String,
        fullName: String,
        email: String,
        phoneNumber: String,
        isUserLoggedIn: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.token] = token
            preferences[PreferenceKeys.fullName] = fullName
            preferences[PreferenceKeys.email] = email
            preferences[PreferenceKeys.phoneNumber] = phoneNumber
            preferences[PreferenceKeys.isUserLoggedIn] = isUserLoggedIn
        }
    }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readIsUserLoggedIn: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val isUserLoggedIn = preferences[PreferenceKeys.isUserLoggedIn] ?: false
            isUserLoggedIn
        }

    val readLoggedInUser: Flow<LoggedInUser> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val token = preferences[PreferenceKeys.token] ?: ""
            val fullName = preferences[PreferenceKeys.fullName] ?: ""
            val phoneNumber = preferences[PreferenceKeys.phoneNumber] ?: ""
            val email = preferences[PreferenceKeys.email] ?: ""
            val isUserLoggedIn = preferences[PreferenceKeys.isUserLoggedIn] ?: false
            LoggedInUser(token, fullName, email, phoneNumber, isUserLoggedIn)
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int,
)

data class LoggedInUser(
    val token: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val isUserLoggedIn: Boolean
)