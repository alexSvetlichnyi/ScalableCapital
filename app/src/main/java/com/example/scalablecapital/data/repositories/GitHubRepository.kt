package com.example.scalablecapital.data.repositories

import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import com.example.scalablecapital.data.network.Resource

/**
 * Interface that provides Places API
 */
interface GitHubRepository {

    /**
     * Get list of repos for the current user.
     *
     * @param user github user
     *
     * @return resource with list of repos
     */
    suspend fun getRepos(user: String): Resource<List<RepoResponse>>

    /**
     * Get list of commits for the selected repo.
     *
     * @param user github user
     * @param repo name of the repo
     *
     * @return resource with list of commits
     */
    suspend fun getRepoDetails(user: String, repo: String): Resource<List<CommitResponse>>
}