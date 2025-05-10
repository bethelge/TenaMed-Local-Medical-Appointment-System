package com.example.tena.data.network

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Save token to shared preferences
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    // Get token from shared preferences
    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    // Remove token from shared preferences (logout)
    fun removeToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }
}
