package com.example.scalablecapital.data.repositories

import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import com.example.scalablecapital.data.network.GitHubApiServiceRx
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Foursquare implementation that provides Places API
 */
@Suppress("UNCHECKED_CAST")
class GitHubRepositoryImplRx @Inject constructor(
    private val gitHubApiService: GitHubApiServiceRx
) : GitHubRepositoryRx {

    /**
     * Get list of repos.
     *
     * @return resource with list of repos or error
     */
    override fun getRepos(user: String): Single<List<RepoResponse>> =
        gitHubApiService.getRepos(user).firstOrError()


    /**
     * Get repo commits.
     *
     * @return resource with repo details or error
     */
    override fun getRepoDetails(user: String, repo: String): Single<List<CommitResponse>> =
        gitHubApiService.getRepoCommits(user, repo).firstOrError()
}