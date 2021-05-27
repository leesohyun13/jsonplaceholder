package com.sohyun.jsonplaceholder.di

import com.sohyun.jsonplaceholder.data.repository.PostsRepository
import com.sohyun.jsonplaceholder.data.repository.PostsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostsRepository(
        PostsRepository: PostsRepositoryImpl
    ): PostsRepository
}