package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.database.User

class UserPreferences(context: Context) {

    private val PREFS_NAME = "user_prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserId(id: Long) {
        val editor = prefs.edit()
        editor.putLong("id", id)
        editor.apply()
    }

    fun getUserId(): Long {
        return prefs.getLong("id",-1)
    }

    fun saveSetting1(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean("setting1", value)
        editor.apply()
    }
    fun saveSetting2(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean("setting2", value)
        editor.apply()
    }
    fun saveSetting3(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean("setting3", value)
        editor.apply()
    }

    fun getSetting1(): Boolean {
        return prefs.getBoolean("setting_1", true)
    }
    fun getSetting2(): Boolean {
        return prefs.getBoolean("setting_2", true)
    }
    fun getSetting3(): Boolean {
        return prefs.getBoolean("setting_3", true)
    }

    fun clear() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
