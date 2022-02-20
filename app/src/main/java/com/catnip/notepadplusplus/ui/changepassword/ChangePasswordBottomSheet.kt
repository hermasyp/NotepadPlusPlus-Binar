package com.catnip.notepadplusplus.ui.changepassword

import android.widget.Toast
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.base.arch.BaseBottomSheetDialogFragment
import com.catnip.notepadplusplus.base.arch.GenericViewModelFactory
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.databinding.FragmentChangePasswordBottomSheetBinding

class ChangePasswordBottomSheet(private val onPasswordChanged: () -> Unit) :
    BaseBottomSheetDialogFragment<FragmentChangePasswordBottomSheetBinding, ChangePasswordViewModel>(
        FragmentChangePasswordBottomSheetBinding::inflate),ChangePasswordContract.View {

    override fun changePassword() {
        if (validateForm()) {
            val insertedPassword = getViewBinding().etPassword.text.toString().trim()
            context?.let { UserPreference(it).password = insertedPassword }
            onPasswordChanged.invoke()
            dismiss()
        }
    }

    override fun setClickListeners() {
        getViewBinding().btnConfirmPassword.setOnClickListener { changePassword() }
    }

    override fun validateForm(): Boolean {
        val password = getViewBinding().etPassword.text.toString()
        val confirmedPassword = getViewBinding().etConfirmedPassword.text.toString()
        var isFormValid = true

        if (password.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilPassword.isErrorEnabled = false
        }
        if (confirmedPassword.isEmpty()) {
            isFormValid = false
            getViewBinding().tilConfirmedPassword.isErrorEnabled = true
            getViewBinding().tilConfirmedPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilConfirmedPassword.isErrorEnabled = false
        }
        if (password != confirmedPassword) {
            isFormValid = false
            Toast.makeText(
                context,
                getString(R.string.text_password_doesnt_match),
                Toast.LENGTH_SHORT
            ).show()
        }
        return isFormValid
    }

    override fun initView() {
        setClickListeners()
    }

    override fun initViewModel(): ChangePasswordViewModel {
        val repository = ChangePasswordRepository(UserPreference(requireContext()))
        return GenericViewModelFactory(ChangePasswordViewModel(repository)).create(
            ChangePasswordViewModel::class.java
        )
    }


}