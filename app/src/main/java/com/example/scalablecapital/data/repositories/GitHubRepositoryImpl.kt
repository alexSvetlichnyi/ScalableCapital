package com.example.scalablecapital.data.repositories

import com.example.scalablecapital.data.models.ErrorMessage
import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import com.example.scalablecapital.data.network.GitHubApiService
import com.example.scalablecapital.data.network.Resource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

import javax.inject.Inject

/**
 * Foursquare implementation that provides Places API
 */
@Suppress("UNCHECKED_CAST")
class GitHubRepositoryImpl @Inject constructor(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {

    /**
     * Get list of repos.
     *
     * @return resource with list of repos or error
     */
    override suspend fun getRepos(user: String): Resource<List<RepoResponse>>
    = withContext(Dispatchers.IO) {
        handleResult(gitHubApiService.getRepos(user))
                as Resource<List<RepoResponse>>
    }

    /**
     * Get repo commits.
     *
     * @return resource with repo details or error
     */
    override suspend fun getRepoDetails(user: String, repo: String): Resource<List<CommitResponse>>
            = withContext(Dispatchers.IO) {
        handleResult(gitHubApiService.getRepoCommits(user, repo))
                as Resource<List<CommitResponse>>
    }

    private fun <T> handleResult(result: Response<T>): Resource<Any> {
        val resource = if (result.isSuccessful) {
            result.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error(result.message())
        } else {
            try {
                val errorMessage = Gson().fromJson(result.errorBody()?.charStream(), ErrorMessage::class.java)
                Resource.Error(errorMessage.message ?: DEFAULT_ERROR)
            } catch (e: JsonSyntaxException) {
                Resource.Error(DEFAULT_ERROR)
            }
        }
        return resource
    }

    companion object {
        const val DEFAULT_ERROR = "Something went wrong"
    }
}