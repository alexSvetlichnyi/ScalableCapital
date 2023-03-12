package com.example.scalablecapital.data.repositories

import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface that provides Places API
 */
interface GitHubRepositoryRx {

    /**
     * Get list of repos for the current user.
     *
     * @param user github user
     *
     * @return resource with list of repos
     */
    fun getRepos(user: String): Single<List<RepoResponse>>

    /**
     * Get list of commits for the selected repo.
     *
     * @param user github user
     * @param repo name of the repo
     *
     * @return resource with list of commits
     */
    fun getRepoDetails(user: String, repo: String): Single<List<CommitResponse>>
}