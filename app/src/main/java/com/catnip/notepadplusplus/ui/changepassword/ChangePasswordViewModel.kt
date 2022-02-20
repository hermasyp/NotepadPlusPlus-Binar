package com.catnip.notepadplusplus.ui.changepassword

import com.catnip.notepadplusplus.base.arch.BaseViewModelImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ChangePasswordViewModel(private val repository: ChangePasswordContract.Repository) :
    ChangePasswordContract.ViewModel, BaseViewModelImpl() {
    override fun getUserPassword(): String = repository.getUserPassword()
    override fun setUserPassword(newPassword: String) {
        repository.setUserPassword(newPassword)
    }
}