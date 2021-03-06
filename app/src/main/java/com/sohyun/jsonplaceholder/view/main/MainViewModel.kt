package com.sohyun.jsonplaceholder.view.main

import androidx.lifecycle.*
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.data.network.NetworkStatus
import com.sohyun.jsonplaceholder.data.repository.PostsRepository
import com.sohyun.jsonplaceholder.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val postPage = MutableLiveData(0)
    private val postsList = MutableLiveData<MutableList<Post>>().apply {
        value = arrayListOf()
    }
    private val isLoading = MutableLiveData(false)
    private val response = MutableLiveData("")

    private suspend fun fetchPosts() {
        isLoading.postValue(true)
        val posts = postsRepository.getPosts(postPage.value!!)
        when (posts) {
            is NetworkStatus.Success -> {
                postsList.value?.addAll(posts.data)
                postsList.notifyObserver()
            }
            is NetworkStatus.Failure -> response.postValue("게시물을 가져오는데 실패했습니다.")
        }
        isLoading.postValue(false)
    }

    suspend fun deletePost(postId: Int) {
        val result = postsRepository.deletePost(postId)
        when (result) {
            is NetworkStatus.Failure -> response.postValue("게시물을 삭제하는데 실패했습니다.")
            is NetworkStatus.Success -> response.postValue("성공적으로 게시물을 삭제했습니다.")
        }
    }

    private val postPageObserver = Observer<Int> {
        viewModelScope.launch(Dispatchers.IO) { fetchPosts() }
    }

    fun increasePage() {
        postPage.value = postPage.value?.inc()
    }

    fun getPostsList(): LiveData<MutableList<Post>> = postsList
    fun getResponse(): LiveData<String> = response
    fun isLoading(): LiveData<Boolean> = isLoading

    init {
        postPage.observeForever(postPageObserver)
    }

    override fun onCleared() {
        postPage.removeObserver(postPageObserver)
        super.onCleared()
    }
}