package com.sohyun.jsonplaceholder.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.databinding.ItemCommentBinding
import com.sohyun.jsonplaceholder.view.base.BaseRecyclerViewAdapter
import com.sohyun.jsonplaceholder.view.base.BaseViewHolder


class CommentAdapter : BaseRecyclerViewAdapter<Comment, CommentAdapter.CommentViewHolder>(DiffCallback()) {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        BaseViewHolder<Comment>(binding.root) {
        private lateinit var item: Comment

        override fun bind(item: Comment) {
            binding.comment.text = item.body
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.CommentViewHolder =
        CommentViewHolder(
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}

private class DiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}