package com.sohyun.jsonplaceholder.view

import androidx.lifecycle.*
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.data.network.NetworkStatus
import com.sohyun.jsonplaceholder.data.repository.PostsRepository
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

    private suspend fun fetchPosts() {
        isLoading.postValue(true)
        val posts = postsRepository.getPosts(postPage.value!!)
        when (posts) {
            is NetworkStatus.Success -> {
                postsList.value?.addAll(posts.data) // FIXME 이렇게 넣어도 notify되는지 확인하기
                postsList.notifyObserver()
            }
            is NetworkStatus.Failure -> {} // FIXME
        }
        isLoading.postValue(false)
    }

    private val postPageObserver = Observer<Int> {
        viewModelScope.launch(Dispatchers.IO) { fetchPosts() }
    }

    fun increasePage() {
        postPage.value = postPage.value?.inc()
    }

    fun getPostsList(): LiveData<MutableList<Post>> = postsList
    fun isLoading(): LiveData<Boolean> = isLoading

    init {
        postPage.observeForever(postPageObserver)
    }

    override fun onCleared() {
        postPage.removeObserver(postPageObserver)
        super.onCleared()
    }
}