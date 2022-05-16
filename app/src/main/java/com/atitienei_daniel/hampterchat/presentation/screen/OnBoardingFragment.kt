package com.atitienei_daniel.hampterchat.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOnBoardingBinding>(
            inflater,
            R.layout.fragment_on_boarding,
            container,
            false
        )

        binding.registerHamsterLogo.setOnClickListener {
            /* TODO */
        }

        binding.goToAuthenticationButton.setOnClickListener {
            /* TODO */
        }

        return binding.root
    }
}