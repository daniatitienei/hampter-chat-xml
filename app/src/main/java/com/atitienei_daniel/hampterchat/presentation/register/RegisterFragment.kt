package com.atitienei_daniel.hampterchat.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.databinding.FragmentRegisterBinding
import com.atitienei_daniel.hampterchat.domain.model.ValidationEvent
import com.atitienei_daniel.hampterchat.presentation.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.title.observe(viewLifecycleOwner, { newTitle ->
            binding.registerTitle.text = newTitle
        })

        viewModel.emailError.observe(viewLifecycleOwner, { error ->
            binding.registerEmailTextInputLayout.error = error
        })

        viewModel.passwordError.observe(viewLifecycleOwner, { error ->
            binding.registerPasswordTextInputLayout.error = error
        })

        viewModel.nameError.observe(viewLifecycleOwner, { error ->
            binding.registerNameTextInputLayout.error = error
        })

        viewModel.usernameError.observe(viewLifecycleOwner, { error ->
            binding.registerUsernameTextInputLayout.error = error
        })

        viewModel.genderError.observe(viewLifecycleOwner, { error ->
            binding.genderDropdownMenuLayout.error = error
        })

        binding.genderDropdownMenu.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnGenderSelected(text.toString()))
        }

        binding.registerEmailTextInputEditText.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnEmailChanged(text.toString()))
        }

        binding.registerPasswordTextInputEditText.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnPasswordChanged(text.toString()))
        }

        binding.registerNameTextInputEditText.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnNameChanged(text.toString()))
        }

        binding.registerUsernameTextInputEditText.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnUsernameChanged(text.toString()))
        }

        binding.registerEmailTextInputEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.registerPasswordTextInputEditText.requestFocus()
                    viewModel.onEvent(RegisterViewModel.RegisterEvents.OnNextImeClickEmailTextField)
                    binding.registerPasswordTextInputLayout.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }

        binding.registerPasswordTextInputEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.registerNameTextInputEditText.requestFocus()
                    viewModel.onEvent(RegisterViewModel.RegisterEvents.OnNextImeClickPasswordTextField)
                    binding.registerNameTextInputLayout.visibility = View.VISIBLE
                    binding.registerUsernameTextInputLayout.visibility = View.VISIBLE
                    binding.genderDropdownMenuLayout.visibility = View.VISIBLE
                    binding.signUpButton.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }

        binding.signUpButton.setOnClickListener {
            viewModel.onEvent(RegisterViewModel.RegisterEvents.OnValidateFields)
        }

        val genders = resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.genderDropdownMenu.setAdapter(arrayAdapter)

        return binding.root
    }


}