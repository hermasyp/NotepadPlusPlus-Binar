package com.catnip.notepadplusplus.ui.enterpassword

import androidx.lifecycle.MutableLiveData
import com.catnip.notepadplusplus.base.arch.BaseContract

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface EnterPasswordContract {
    interface View : BaseContract.BaseView{
        fun checkPassword()
        fun setClickListeners()
        fun validateForm() : Boolean
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getUserPassword() : String
    }

    interface Repository {
        fun getUserPassword() : String
    }
}