package com.example.scalablecapital.ui.repodetails

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.viewModelScope
import com.example.scalablecapital.application.BaseAndroidViewModel
import com.example.scalablecapital.application.SingleLiveData
import com.example.scalablecapital.data.models.repocommits.CommitResponse
import com.example.scalablecapital.data.network.Resource
import com.example.scalablecapital.data.repositories.GitHubRepository
import com.example.scalablecapital.data.repositories.GitHubRepositoryRx
import com.example.scalablecapital.ui.reposlist.ReposListViewModel.Companion.USER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor (
    app: Application,
    private val gitHubRepository: GitHubRepository,
    private val gitHubRepositoryRx: GitHubRepositoryRx
) : BaseAndroidViewModel(app) {

    val commitsNumber: ObservableField<String> by lazy { ObservableField<String>() }
    val month: ObservableField<String> by lazy { ObservableField<String>() }
    val level: ObservableFloat by lazy { ObservableFloat(-1f) }

    val errorMessage: SingleLiveData<String> by lazy { SingleLiveData() }

    // Array of commits per month
    private var commits = IntArray(NUMBER_OF_MONTH)

    // Create months array with names
    private var months = Array(NUMBER_OF_MONTH) {
        val formatterM = SimpleDateFormat(DATE_FORMAT_M, Locale.getDefault())
        val formatterMonth = SimpleDateFormat(DATE_FORMAT_MMMM, Locale.getDefault())
        formatterM.parse((it + 1).toString())?.let {
            formatterMonth.format(it) } ?: ""
    }

    private var maxCommitsNumber: Int = 0
    private var disposables = CompositeDisposable()

    fun getRepoDetails(repo: String) {
        viewModelScope.launch {
            when(val result = gitHubRepository.getRepoDetails(USER_NAME, repo)) {
                is Resource.Success -> {
                    fillCommitsArray(result.data)
                    repeat(REPEAT_COUNT) {
                        setCommitsPerMonthInfo(it)
                        delay(SCROLL_DELAY)
                    }
                }
                is Resource.Error -> errorMessage.postValue(result.errorMessage)
            }
        }
    }

    fun getRepoDetailsRx(repo: String) {
        disposables.add(gitHubRepositoryRx.getRepoDetails(USER_NAME, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                fillCommitsArray(it)
                it
            }
            .subscribeWith(object : DisposableSingleObserver<List<CommitResponse>>() {
                override fun onSuccess(data: List<CommitResponse>) {
                    var counter = 0
                    setCommitsPerMonthInfo(counter)
                    disposables.add(Observable.timer(SCROLL_DELAY, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .repeat(REPEAT_COUNT.toLong())
                        .subscribe {
                            counter++
                            setCommitsPerMonthInfo(counter)
                        })
                }

                override fun onError(e: Throwable) {
                    errorMessage.value = e.localizedMessage
                }
            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private fun fillCommitsArray(list: List<CommitResponse>) {
        list.forEach {
            val formatter = SimpleDateFormat(GIT_HUB_DATE_FORMAT, Locale.getDefault())
            val date = formatter.parse(it.commit.committer.date)
            val formatterMonth = SimpleDateFormat(DATE_FORMAT_M, Locale.getDefault())
            val month = formatterMonth.format(date).toInt()
            commits[month - 1]++
        }
        maxCommitsNumber = commits.maxOrNull() ?: 0
    }

    private fun setCommitsPerMonthInfo(index: Int) {
        val commits = commits[index % NUMBER_OF_MONTH]
        commitsNumber.set("$commits commits")
        month.set(months[index % NUMBER_OF_MONTH])
        level.set(commits.toFloat() / maxCommitsNumber)
    }

    companion object {
        const val REPEAT_COUNT = 1000
        const val SCROLL_DELAY = 1500L
        const val NUMBER_OF_MONTH = 12
        const val GIT_HUB_DATE_FORMAT = "yyyy-MM-dd"
        const val DATE_FORMAT_M = "M"
        const val DATE_FORMAT_MMMM = "MMMM"
    }
}
