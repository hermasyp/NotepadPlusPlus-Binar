package com.catnip.notepadplusplus.base

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface BaseContract {
    interface BasePresenter {
        fun onDestroy()
    }

    interface BaseView {
        fun showContent(isContentVisible: Boolean)
        fun showLoading(isLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String?)
    }
}