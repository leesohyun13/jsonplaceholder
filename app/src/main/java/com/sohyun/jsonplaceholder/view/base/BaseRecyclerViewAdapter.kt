package com.sohyun.jsonplaceholder.view.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, H : BaseViewHolder<T>>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, H>(diffCallback) {

    private val items: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder: H, position: Int) {
        holder.bind(items[position])
    }

    open fun setData(newItems: List<T>) {
        this.items.addAll(newItems)
        submitList(items)
        notifyDataSetChanged()
    }

    open fun removeItem(item: T) {
        val pos = (this.items.indexOf(item))
        this.items.remove(item)
        notifyItemRemoved(pos)
    }

    open fun updateItem(item: T) {
        val pos = (this.items.indexOf(item))
        this.items[pos] = item
        notifyItemChanged(pos)
    }

    override fun getItem(position: Int): T {
        return super.getItem(position)
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}