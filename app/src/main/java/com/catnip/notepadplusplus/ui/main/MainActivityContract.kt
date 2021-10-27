package com.catnip.notepadplusplus.ui.main

import androidx.fragment.app.Fragment
import com.catnip.notepadplusplus.base.BaseContract

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MainActivityContract {
    interface View : BaseContract.BaseView{
        fun setupFragment()
        fun showFragment(fragment: Fragment)
        fun changePassword()
        fun showEnterPasswordDialog()
        fun showDialogChangePassword()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun checkPasswordAvailability()
    }

    interface Repository {
        fun isPasswordExist() : Boolean
    }
}