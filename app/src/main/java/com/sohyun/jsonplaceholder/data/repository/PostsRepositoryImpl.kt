package com.sohyun.jsonplaceholder.data.repository

import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.data.network.NetworkStatus
import com.sohyun.jsonplaceholder.data.network.PlaceHoldApi
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val placeHoldApi: PlaceHoldApi
): PostsRepository, BaseRepository(){
    override suspend fun getPosts(start: Int): NetworkStatus<List<Post>> =
        safeApiCall { placeHoldApi.getPosts(start) }

    override suspend fun searchPostItem(id: Int): NetworkStatus<Post> =
        safeApiCall { placeHoldApi.searchPostItem(id) }


    override suspend fun getComments(id: Int): NetworkStatus<List<Comment>> =
        safeApiCall { placeHoldApi.getComments(id) }

    override suspend fun deletePost(id: Int): NetworkStatus<Unit> =
        safeApiCall { placeHoldApi.deletePost(id) }

    // FIXME
    override suspend fun updateData(id: Int, content: String) = placeHoldApi.updateData(id, content)
}