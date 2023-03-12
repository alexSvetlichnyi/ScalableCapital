package com.example.scalablecapital.ui.reposlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.scalablecapital.BR
import com.example.scalablecapital.R
import com.example.scalablecapital.data.models.reposlist.RepoResponse

class ReposAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private val items: MutableList<RepoResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_list_content, parent, false)
        return ViewHolder(dataBinding)
    }

    fun addItems(list: List<RepoResponse>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewModel = ItemViewModel(items[position], onItemClickListener)
        val binding = holder.dataBinder
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun getItemCount() = items.size

    class ViewHolder(var dataBinder: ViewDataBinding) : RecyclerView.ViewHolder(dataBinder.root)

}