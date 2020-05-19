package com.example.catapp.scenes.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.catapp.R
import com.example.catapp.bases.BaseViewModelFragment
import com.example.catapp.databinding.FragmentLoginBinding
import com.example.catapp.model.User
import com.example.catapp.utils.ToolbarFragment
import com.example.catapp.utils.displayModalPopup
import com.example.catapp.utils.navigateIfAdded
import com.example.catapp.utils.observeNonNull
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
        }
        viewModel.loginError.observeNonNull(viewLifecycleOwner) {
            requireContext().displayModalPopup(
                getString(R.string.something_bad_happened),
                it.message
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            viewModel.login(
                User(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            )
        }
    }

}