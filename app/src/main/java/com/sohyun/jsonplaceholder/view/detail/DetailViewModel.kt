package com.sohyun.jsonplaceholder.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.data.network.NetworkStatus
import com.sohyun.jsonplaceholder.data.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postsRepository: PostsRepository
): ViewModel() {
    private val commentList = MutableLiveData<List<Comment>>().apply {
        value = arrayListOf()
    }

    suspend fun fetchComments(postId: Int) {
        val comment = postsRepository.getComments(postId)
        when (comment) {
            is NetworkStatus.Success -> {
                commentList.postValue(comment.data)
            }
            is NetworkStatus.Failure -> {}
        }
    }

    fun getCommentList(): LiveData<List<Comment>> = commentList
}