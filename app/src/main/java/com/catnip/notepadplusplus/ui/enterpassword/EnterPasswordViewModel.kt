package com.catnip.notepadplusplus.ui.enterpassword

import com.catnip.notepadplusplus.base.arch.BaseViewModelImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class EnterPasswordViewModel(private val repository: EnterPasswordContract.Repository) :
    EnterPasswordContract.ViewModel, BaseViewModelImpl() {
    override fun getUserPassword(): String = repository.getUserPassword()
}