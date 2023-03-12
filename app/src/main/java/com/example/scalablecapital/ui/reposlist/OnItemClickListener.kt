package com.example.scalablecapital.ui.reposlist

import com.example.scalablecapital.data.models.reposlist.RepoResponse

interface OnItemClickListener {
    fun onItemCLick(item: RepoResponse)
}