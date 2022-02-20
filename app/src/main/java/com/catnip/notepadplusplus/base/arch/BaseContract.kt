package com.catnip.notepadplusplus.base.arch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface BaseContract {
    interface BaseViewModel{

    }
    interface BaseView {
        fun observeData()
        fun showContent(isContentVisible: Boolean)
        fun showLoading(isLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String?)
    }
}