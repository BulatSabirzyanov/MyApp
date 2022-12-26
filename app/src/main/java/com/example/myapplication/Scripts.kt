package com.example.myapplication


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.security.MessageDigest
import java.util.regex.Pattern

internal fun hashPassword(password: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(password.toByteArray())
    return hash.fold("") { str, it -> str + "%02x".format(it) }
}

internal fun checkEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

internal fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=\\S+\$).{8,}\$"
        val regex = Regex(pattern)
        return !TextUtils.isEmpty(password) && regex.matches(password)
}
class ViewUtils {
    companion object {
        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}