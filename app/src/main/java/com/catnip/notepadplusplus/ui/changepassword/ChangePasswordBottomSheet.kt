package com.catnip.notepadplusplus.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.databinding.FragmentChangePasswordBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePasswordBottomSheet(private val onPasswordChanged: () -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChangePasswordBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun changePassword() {
        if (validateForm()) {
            val insertedPassword = binding.etPassword.text.toString().trim()
            context?.let { UserPreference(it).password = insertedPassword}
            onPasswordChanged.invoke()
            dismiss()
        }
    }

    private fun setClickListeners(){
        binding.btnConfirmPassword.setOnClickListener { changePassword() }
    }

    private fun validateForm(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmedPassword = binding.etConfirmedPassword.text.toString()
        var isFormValid = true

        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_password_empty)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        if (confirmedPassword.isEmpty()) {
            isFormValid = false
            binding.tilConfirmedPassword.isErrorEnabled = true
            binding.tilConfirmedPassword.error = getString(R.string.error_password_empty)
        } else {
            binding.tilConfirmedPassword.isErrorEnabled = false
        }
        if(password != confirmedPassword){
            isFormValid = false
            Toast.makeText(context, getString(R.string.text_password_doesnt_match), Toast.LENGTH_SHORT).show()
        }
        return isFormValid
    }


}