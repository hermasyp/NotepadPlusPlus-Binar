package com.catnip.notepadplusplus.ui.changepassword

import com.catnip.notepadplusplus.data.local.preference.UserPreference

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ChangePasswordRepository(private val userPreference: UserPreference) :
    ChangePasswordContract.Repository {
    override fun getUserPassword(): String {
        return userPreference.password.orEmpty()
    }

    override fun setUserPassword(newPassword: String) {
        userPreference.password = newPassword
    }

}