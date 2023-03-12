package com.example.scalablecapital.ui.reposlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.scalablecapital.R
import com.example.scalablecapital.databinding.FragmentItemListBinding
import com.example.scalablecapital.ui.repodetails.RepoDetailFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReposListFragment : Fragment() {

    private val viewModel: ReposListViewModel by viewModels()

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        binding.viewModel = viewModel
        viewModel.openDetails.observe(viewLifecycleOwner, {
            val bundle = Bundle()
            bundle.putString(
                RepoDetailFragment.REPO_ID,
                it.name
            )
            findNavController().navigate(R.id.show_item_detail, bundle)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            if (snackbar?.isShown != true) {
                snackbar = Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.close)) { }.apply { show() }
            }
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}