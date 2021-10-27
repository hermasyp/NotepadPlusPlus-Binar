package com.catnip.notepadplusplus.ui.main

import com.catnip.notepadplusplus.base.BasePresenterImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val repository: MainActivityContract.Repository
) :
    MainActivityContract.Presenter, BasePresenterImpl() {
    override fun checkPasswordAvailability() {
        if(repository.isPasswordExist()){
            view.showEnterPasswordDialog()
        }else{
            view.showDialogChangePassword()
        }
    }


}