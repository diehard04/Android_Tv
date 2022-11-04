package com.example.androidtv.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtv.R
import com.example.androidtv.data.model.User

/**
 * Created by Dipak Kumar Mehta on 10/21/2021.
 */

class UserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textViewUserName:TextView ?= null
        private var textViewUserEmail:TextView ?=null
        private var imageViewAvatar:ImageView ?= null

        fun bind(user: User, holder: DataViewHolder) {
            textViewUserName = itemView.findViewById(R.id.textViewUserName)
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail)
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar)
            itemView.apply {
                textViewUserName?.text = user.name
                textViewUserEmail?.text = user.email
                imageViewAvatar?.context?.let {
                    Glide.with(it)
                        .load(user.avatar)
                        .into(imageViewAvatar!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder{

        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position], holder)

    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
}