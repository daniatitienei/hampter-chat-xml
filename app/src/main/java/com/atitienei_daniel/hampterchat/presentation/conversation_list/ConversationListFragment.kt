package com.atitienei_daniel.hampterchat.presentation.conversation_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.databinding.FragmentConversationListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationListFragment : Fragment() {

    private val viewModel by viewModels<ConversationListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentConversationListBinding>(
            inflater,
            R.layout.fragment_conversation_list,
            container,
            false
        )

        val adapter = ConversationListAdapter(clickListener = PersonListener { friend ->
            Toast.makeText(context, friend.username, Toast.LENGTH_SHORT).show()
        })
        binding.friendList.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                adapter.submitList(user.friendList)
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }

}