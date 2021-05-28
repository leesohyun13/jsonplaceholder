package com.sohyun.jsonplaceholder.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.databinding.ItemPostBinding
import com.sohyun.jsonplaceholder.view.base.BaseRecyclerViewAdapter
import com.sohyun.jsonplaceholder.view.base.BaseViewHolder

class PostAdapter : BaseRecyclerViewAdapter<Post, PostAdapter.ViewHolder>(DiffCallback()) {
    inner class ViewHolder(private val binding: ItemPostBinding) :
        BaseViewHolder<Post>(binding.root) {
        private lateinit var item: Post

        override fun bind(item: Post) {
            this.item = item
            with(binding) {
                title.text = item.title
                body.text = item.body
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder =
        ViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}

private class DiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}