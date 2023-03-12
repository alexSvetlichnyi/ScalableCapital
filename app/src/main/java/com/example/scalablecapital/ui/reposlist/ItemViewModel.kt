package com.example.scalablecapital.ui.reposlist

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.example.scalablecapital.data.models.reposlist.RepoResponse

class ItemViewModel(private val repo: RepoResponse, private val onItemClickListener: OnItemClickListener) : BaseObservable() {
    val text = ObservableField<String>()
    val content = ObservableField<String>()

    init {
        text.set(repo.name)
        content.set(repo.description)
    }

    fun onItemClick(view: View) {
        onItemClickListener.onItemCLick(repo)
    }
}