package com.example.scalablecapital.application

import com.example.scalablecapital.data.repositories.GitHubRepository
import com.example.scalablecapital.data.repositories.GitHubRepositoryImpl
import com.example.scalablecapital.data.repositories.GitHubRepositoryImplRx
import com.example.scalablecapital.data.repositories.GitHubRepositoryRx
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {
    @Binds
    abstract fun bindGitHubRepository(gitHubRepositoryImpl: GitHubRepositoryImpl): GitHubRepository

    @Binds
    abstract fun bindGitHubRepositoryRx(gitHubRepositoryImplRx: GitHubRepositoryImplRx): GitHubRepositoryRx
}