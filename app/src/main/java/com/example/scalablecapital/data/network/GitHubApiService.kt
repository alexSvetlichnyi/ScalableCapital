package com.example.scalablecapital.data.network

import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): Response<List<RepoResponse>>

    @GET("repos/{user}/{repo}/commits")
    suspend fun getRepoCommits(@Path("user") user: String,
                               @Path("repo") repo: String): Response<List<CommitResponse>>
}