package com.catnip.notepadplusplus.ui.main

import com.catnip.notepadplusplus.data.local.preference.UserPreference

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainActivityRepository(private val userPreference: UserPreference) :
    MainActivityContract.Repository {
    override fun isPasswordExist(): Boolean {
        return !userPreference.password.isNullOrEmpty()
    }
}