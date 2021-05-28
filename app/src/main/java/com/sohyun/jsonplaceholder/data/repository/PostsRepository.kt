package com.sohyun.jsonplaceholder.data.repository

import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.data.network.NetworkStatus

interface PostsRepository {
    suspend fun getPosts(start: Int): NetworkStatus<List<Post>>
    suspend fun searchPostItem(id: Int): NetworkStatus<Post>
    suspend fun getComments(id: Int): NetworkStatus<List<Comment>>
    suspend fun deletePost(id: Int): NetworkStatus<Unit>
    suspend fun updateData(id: Int, content: String)
}