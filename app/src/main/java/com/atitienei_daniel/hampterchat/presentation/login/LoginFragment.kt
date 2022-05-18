package com.atitienei_daniel.hampterchat.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.databinding.FragmentLoginBinding
import com.atitienei_daniel.hampterchat.domain.model.ValidationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        lifecycleScope.launch {
            viewModel.validationEvent.collect { event ->
                if (event is ValidationEvent.Success) {
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_chatListFragment)
                }
            }
        }

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.emailError.observe(viewLifecycleOwner, {
            binding.loginEmailTextField.error = it
        })

        viewModel.passwordError.observe(viewLifecycleOwner, {
            binding.loginPasswordTextField.error = it
        })

        binding.loginEmailTextInput.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(LoginViewModel.LoginEvents.OnEmailChanged(text.toString()))
        }

        binding.loginPasswordTextInput.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(LoginViewModel.LoginEvents.OnPasswordChanged(text.toString()))
        }

        binding.signUpButton.setOnClickListener {
            viewModel.onEvent(LoginViewModel.LoginEvents.OnValidateFields)
        }

        return binding.root
    }
}