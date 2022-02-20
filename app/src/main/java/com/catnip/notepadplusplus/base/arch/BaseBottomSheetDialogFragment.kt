package com.catnip.notepadplusplus.base.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseBottomSheetDialogFragment<B : ViewBinding, VM : BaseContract.BaseViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BottomSheetDialogFragment(), BaseContract.BaseView {
    private lateinit var binding: B
    private lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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