package com.example.scalablecapital.ui.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.scalablecapital.R
import com.example.scalablecapital.databinding.FragmentItemDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : Fragment() {

    private  val viewModel : RepoDetailsViewModel by viewModels()
    private lateinit var repoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(REPO_ID)) {
                repoId = it.getString(REPO_ID) ?: ""
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding : FragmentItemDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_item_detail, container, false)
        binding.viewModel = viewModel

        // Coroutines way, please comment it if you want use RX
        viewModel.getRepoDetails(repoId)
        // RX way, please uncomment to check RX.
        //viewModel.getRepoDetailsRx(repoId)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = repoId
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.close)) { requireActivity().onBackPressed() }
                .show()
        })
        return binding.root
    }

    companion object {
        const val REPO_ID = "REPO_NAME"
    }
}