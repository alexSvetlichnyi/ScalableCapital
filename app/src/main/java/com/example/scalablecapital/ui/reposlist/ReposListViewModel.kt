package com.example.scalablecapital.ui.reposlist

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.scalablecapital.application.BaseAndroidViewModel
import com.example.scalablecapital.application.SingleLiveData
import com.example.scalablecapital.data.models.reposlist.RepoResponse
import com.example.scalablecapital.data.network.Resource
import com.example.scalablecapital.data.repositories.GitHubRepository
import com.example.scalablecapital.data.repositories.GitHubRepositoryRx
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposListViewModel @Inject constructor (
    app: Application,
    private val gitHubRepository: GitHubRepository,
    private val gitHubRepositoryRx: GitHubRepositoryRx
) : BaseAndroidViewModel(app) {

    private var disposables = CompositeDisposable()

    var messageAdapter : ReposAdapter = ReposAdapter(object : OnItemClickListener {
        override fun onItemCLick(item: RepoResponse) {
            openDetails.value = item
        }
    })

    val errorMessage: SingleLiveData<String> by lazy { SingleLiveData() }
    val openDetails: SingleLiveData<RepoResponse> by lazy { SingleLiveData() }

    init {
        // Coroutines way, please comment it if you want use RX.
        getReposList()
        // RX way, please uncomment to check RX.
        //getReposListRx()
    }

    private fun getReposList() {
        viewModelScope.launch {
            when (val result =
                gitHubRepository.getRepos(USER_NAME)) {
                is Resource.Success -> {
                    messageAdapter.addItems(result.data)
                }
                is Resource.Error -> errorMessage.value = result.errorMessage
            }
        }
    }

    private fun getReposListRx() {
        disposables.add(gitHubRepositoryRx.getRepos(USER_NAME)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<RepoResponse>>() {
                override fun onSuccess(t: List<RepoResponse>) {
                    messageAdapter.addItems(t)
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

    companion object {
        const val USER_NAME = "mralexgray"

        @BindingAdapter("adapter")
        @JvmStatic
        fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            recyclerView.adapter = adapter
        }
    }
}
