package com.catnip.notepadplusplus.ui.enterpassword

import com.catnip.notepadplusplus.data.local.preference.UserPreference

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class EnterPasswordRepository(private val userPreference: UserPreference) :
    EnterPasswordContract.Repository {
    override fun getUserPassword(): String {
        return userPreference.password.orEmpty()
    }

}