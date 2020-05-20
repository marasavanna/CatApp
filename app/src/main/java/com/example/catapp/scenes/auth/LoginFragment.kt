package com.example.catapp.scenes.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.catapp.R
import com.example.catapp.bases.BaseViewModelFragment
import com.example.catapp.databinding.FragmentLoginBinding
import com.example.catapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseViewModelFragment<FragmentLoginBinding, LoginViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()

    override fun builder(): ToolbarFragment? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observeNonNull(viewLifecycleOwner) {
            findNavController().navigateIfAdded(
                this,
                LoginFragmentDirections.loginToBreedsFragment(),
                R.id.loginFragment
            )
            viewModel.isLoading.set(false)
        }
        viewModel.loginError.observeNonNull(viewLifecycleOwner) {
            requireContext().displayModalPopup(
                getString(R.string.something_bad_happened),
                it.message
            )
            viewModel.isLoading.set(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.login.setOnClickListener {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                viewModel.isLoading.set(true)
                with(viewModel) {
                    email.get()?.let { emailText ->
                        password.get()?.let { passwordText ->
                            if (LoginValidation.validateFields(emailText, passwordText)) {
                                login(emailText, passwordText)
                            } else {
                                email.set(null)
                                password.set(null)
                                binding.email.error = getString(R.string.login_invalid_format)
                                binding.password.error = getString(R.string.login_invalid_format)
                            }
                        }
                    }
                }
            }
        }
    }
}