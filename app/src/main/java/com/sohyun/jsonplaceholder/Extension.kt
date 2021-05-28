package com.sohyun.jsonplaceholder

import android.content.Context
import android.widget.Toast
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

@BindingAdapter("addItems")
fun RecyclerView.addItems(items: List<Any>?) {
    (this.adapter as? BaseRecyclerViewAdapter<Any, BaseViewHolder<Any>>)?.run {
        items?.let { addData(items) }
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}

fun showToastMessage(context: Context, content: String) {
    Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
}