package com.catnip.notepadplusplus.data.local.preference

import android.content.Context
import android.content.SharedPreferences

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class UserPreference(context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "NotepadPlusPlus" //app name or else
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_USER_PASSWORD = Pair("PREF_USER_PASSWORD", null)
    }

    var password: String?
        get() = preference.getString(PREF_USER_PASSWORD.first, PREF_USER_PASSWORD.second)
        set(value) = preference.edit {
            it.putString(PREF_USER_PASSWORD.first, value)
        }

}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

private fun SharedPreferences.delete() {
    edit().clear().apply()
}
