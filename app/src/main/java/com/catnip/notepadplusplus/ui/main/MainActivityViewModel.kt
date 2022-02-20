package com.catnip.notepadplusplus.ui.main

import com.catnip.notepadplusplus.base.arch.BaseViewModelImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainActivityViewModel(private val repository: MainActivityContract.Repository) :
    MainActivityContract.ViewModel, BaseViewModelImpl() {

    override fun isPasswordExist() = repository.isPasswordExist()


}