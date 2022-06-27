package com.awp.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awp.githubuser.database.UserData
import com.awp.githubuser.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val list = ArrayList<UserData>()
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback{
        fun onItemClicked(data: UserData)
    }

    inner class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userData: UserData){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(userData)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(userData.avatar_url)
                    .centerCrop()
                    .into(imgAvatar)
                tvUsername.text = userData.login
            }
        }
    }

    fun setList(userData: ArrayList<UserData>){
        list.clear()
        list.addAll(userData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}