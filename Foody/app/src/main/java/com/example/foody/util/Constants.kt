package com.example.foody.util

class Constants {

    companion object {
        const val API_KEY = "03304cf9ec20471e8fcaf17df6a06775"
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_URL_BONI = "http://192.168.0.31:8080"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        const val RECIPE_BUNDLE_KEY = "recipeBundle"

        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

        //boni preferences
        const val PREFERENCES_LOGGED_IN_USER = "isUserLoggedIn"
        const val PREFERENCES_TOKEN = "token"
        const val PREFERENCES_FULL_NAME = "fullName"
        const val PREFERENCES_EMAIL = "email"
        const val PREFERENCES_PHONE_NUMBER = "phoneNumber"
        const val PREFERENCES_PROFILE_IMAGE = "profileImage"
    }
}