package com.catnip.notepadplusplus.ui.changepassword

import androidx.lifecycle.MutableLiveData
import com.catnip.notepadplusplus.base.arch.BaseContract

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ChangePasswordContract {
    interface View : BaseContract.BaseView{
        fun changePassword()
        fun setClickListeners()
        fun validateForm() : Boolean
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getUserPassword() : String
        fun setUserPassword(newPassword : String)
    }

    interface Repository {
        fun getUserPassword() : String
        fun setUserPassword(newPassword : String)
    }
}