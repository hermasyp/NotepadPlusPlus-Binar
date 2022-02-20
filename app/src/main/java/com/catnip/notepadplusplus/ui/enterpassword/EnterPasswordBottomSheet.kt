package com.catnip.notepadplusplus.ui.enterpassword

import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.base.arch.BaseBottomSheetDialogFragment
import com.catnip.notepadplusplus.base.arch.GenericViewModelFactory
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.databinding.FragmentEnterPasswordBottomSheetBinding

class EnterPasswordBottomSheet(private val isPasswordConfirmed: (Boolean) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentEnterPasswordBottomSheetBinding, EnterPasswordViewModel>(
        FragmentEnterPasswordBottomSheetBinding::inflate
    ),EnterPasswordContract.View {

    override fun initView() {
        setClickListeners()
    }

    override fun checkPassword() {
        if (validateForm()) {
            val password = getViewModel().getUserPassword()
            val insertedPassword = getViewBinding().etPassword.text.toString().trim()
            isPasswordConfirmed.invoke(password == insertedPassword)
            dismiss()
        }
    }

    override  fun setClickListeners() {
        getViewBinding().btnConfirmPassword.setOnClickListener { checkPassword() }
    }

    override  fun validateForm(): Boolean {
        val password = getViewBinding().etPassword.text.toString()
        var isFormValid = true

        //for checking is title empty
        if (password.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilPassword.isErrorEnabled = false
        }

        return isFormValid
    }


    override fun initViewModel(): EnterPasswordViewModel {
        val repository = EnterPasswordRepository(UserPreference(requireContext()))
        return GenericViewModelFactory(EnterPasswordViewModel(repository)).create(
            EnterPasswordViewModel::class.java
        )
    }


}