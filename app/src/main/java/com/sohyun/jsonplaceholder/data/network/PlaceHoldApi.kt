package com.sohyun.jsonplaceholder.data.network

import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.data.model.Post
import retrofit2.http.*

interface PlaceHoldApi {
    @GET(SUB_GET_POSTS)
    suspend fun getPosts(
        @Query(SCHEMA_START) _start: Int,
        @Query(SCHEMA_LIMIT) _limit: Int = LIMIT_COUNT,
    ): List<Post>

    @GET(SUB_SEARCH_POST)
    suspend fun searchPostItem(
        @Path(SCHEMA_PATH_ID) id: Int,
    ): Post

    @GET(SUB_GET_COMMENT)
    suspend fun getComments(
        @Path(SCHEMA_PATH_ID) id: Int,
    ): List<Comment>

    @POST(SUB_SEARCH_POST)
    suspend fun deletePost(
        @Path(SCHEMA_PATH_ID) id: Int,
    )

    @PATCH(SUB_SEARCH_POST)
    suspend fun updateData(
        @Path(SCHEMA_LIMIT) id: Int,
        @Body body: String
    )


    companion object {
        const val PLACE_HOLD_BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val LIMIT_COUNT = 10

        const val SUB_GET_POSTS = "posts"
        const val SUB_SEARCH_POST = "posts/{id}"
        const val SUB_GET_COMMENT = "posts/{id}/comments"

        const val SCHEMA_START = "_start"
        const val SCHEMA_LIMIT = "_limit"
        const val SCHEMA_PATH_ID = "id"
        const val SCHEMA_FIELD_TITLE = "title"
        const val SCHEMA_FIELD_BODY = "body"
    }
}