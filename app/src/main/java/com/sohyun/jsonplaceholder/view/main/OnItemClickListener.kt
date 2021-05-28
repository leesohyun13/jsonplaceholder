package com.sohyun.jsonplaceholder.view.main

import com.sohyun.jsonplaceholder.data.model.Post

interface OnItemClickListener {
    fun clickedPost(post: Post)
    fun deletePost(postId: Int)
}