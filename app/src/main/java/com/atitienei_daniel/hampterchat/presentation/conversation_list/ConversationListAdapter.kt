package com.atitienei_daniel.hampterchat.presentation.conversation_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.atitienei_daniel.hampterchat.databinding.FriendConversationListItemViewBinding
import com.atitienei_daniel.hampterchat.domain.model.Person
import java.text.SimpleDateFormat
import java.util.*

class ConversationListAdapter(val clickListener: PersonListener) :
    ListAdapter<Person, ConversationListAdapter.ViewHolder>(PersonDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(person = item, clickListener)
    }

    class ViewHolder private constructor(val binding: FriendConversationListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person, clickListener: PersonListener) {
            binding.person = person
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FriendConversationListItemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}

class PersonListener(val clickListener: (person: Person) -> Unit) {
    fun onClick(person: Person) = clickListener(person)
}

@BindingAdapter("profilePicture")
fun ImageView.setProfilePicture(person: Person) {
    load(person.imageUrl) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("latestMessage")
fun TextView.setLatestMessage(person: Person) {
    val latestMessage = person.chat.last()
    val date = latestMessage.sentAt.toDate()

    val sentAt = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)

    text = "${latestMessage.text} ($sentAt)"
}