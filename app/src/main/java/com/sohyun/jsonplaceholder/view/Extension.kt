package com.sohyun.jsonplaceholder.view

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sohyun.jsonplaceholder.view.base.BaseRecyclerViewAdapter
import com.sohyun.jsonplaceholder.view.base.BaseViewHolder

@BindingAdapter("setItems")
fun RecyclerView.setItems(items: List<Any>?) {
    (this.adapter as? BaseRecyclerViewAdapter<Any, BaseViewHolder<Any>>)?.run {
        items?.let { setData(items) }
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}