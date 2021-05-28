package com.sohyun.jsonplaceholder.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohyun.jsonplaceholder.data.model.Comment
import com.sohyun.jsonplaceholder.data.network.NetworkStatus
import com.sohyun.jsonplaceholder.data.repository.PostsRepository
import com.sohyun.jsonplaceholder.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val commentList = MutableLiveData<List<Comment>>().apply {
        value = arrayListOf()
    }

    suspend fun fetchComments(postId: Int) {
        val comment = postsRepository.getComments(postId)
        when (comment) {
            is NetworkStatus.Success -> {
                commentList.postValue(comment.data)
            }
            is NetworkStatus.Failure -> {
            }
        }
    }

    fun update(postId: Int, data: HashMap<String, String>) {
        viewModelScope.launch(ioDispatcher) {
            val response = postsRepository.updateData(postId, data)
            when (response) {
                is NetworkStatus.Failure -> {
                }
                is NetworkStatus.Success -> {
                }
            }
        }
    }

    fun getCommentList(): LiveData<List<Comment>> = commentList
}