package com.catnip.notepadplusplus.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BasePresenterImpl : BaseContract.BasePresenter {
    private val coroutineJob = Job()
    val scope = CoroutineScope(Dispatchers.IO + coroutineJob)
    override fun onDestroy() {
        coroutineJob.cancel()
    }
}