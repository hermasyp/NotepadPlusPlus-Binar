package com.catnip.notepadplusplus.ui.enterpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.databinding.FragmentEnterPasswordBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EnterPasswordBottomSheet(private val isPasswordConfirmed: (Boolean) -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEnterPasswordBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEnterPasswordBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun checkPassword() {
        if (validateForm()) {
            val password = context?.let { UserPreference(it).password }
            val insertedPassword = binding.etPassword.text.toString().trim()
            isPasswordConfirmed.invoke(password == insertedPassword)
            dismiss()
        }
    }

    private fun setClickListeners(){
        binding.btnConfirmPassword.setOnClickListener { checkPassword() }
    }

    private fun validateForm(): Boolean {
        val password = binding.etPassword.text.toString()
        var isFormValid = true

        //for checking is title empty
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_password_empty)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }

        return isFormValid
    }


}