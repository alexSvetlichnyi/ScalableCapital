package com.example.scalablecapital.data.network

import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiServiceRx {
    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Observable<List<RepoResponse>>

    @GET("repos/{user}/{repo}/commits")
    fun getRepoCommits(@Path("user") user: String,
                               @Path("repo") repo: String): Observable<List<CommitResponse>>
}