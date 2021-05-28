package com.eyepertizer.mvvm.ui.main.apapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyepertizer.mvvm.R
import com.eyepertizer.mvvm.data.model.User

class MainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        private val textViewUserEmail: TextView = itemView.findViewById(R.id.textViewUserEmail)

        fun bind(user: User) {
            textViewUserName.text = user.name
            textViewUserEmail.text = user.email
//            Glide.with(itemView.imageViewAvatar.context)
//                .load(user.avatar)
//                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}