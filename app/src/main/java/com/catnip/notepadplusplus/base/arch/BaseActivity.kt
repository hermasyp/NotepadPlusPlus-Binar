package com.catnip.notepadplusplus.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseActivity<B : ViewBinding, VM : BaseContract.BaseViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {

    private lateinit var binding: B
    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        viewModel = initViewModel()
        observeData()
        initView()
    }

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModel

    abstract fun initView()
    abstract fun initViewModel() : VM
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
    override fun observeData() {}
}