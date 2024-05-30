package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Election
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectionsFragment: BaseFragment() {

    override val viewModel: ElectionsViewModel by viewModels()

    private lateinit var upcomingElectionsAdapter: ElectionListAdapter
    private lateinit var savedElectionsAdapter: ElectionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {

        val binding = FragmentElectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        upcomingElectionsAdapter = ElectionListAdapter(ElectionListener { election ->
            navigateToVoteInfo(election)
        })

        savedElectionsAdapter = ElectionListAdapter(ElectionListener { election ->
            navigateToVoteInfo(election)
        })


        binding.electionList.adapter = upcomingElectionsAdapter
        binding.myElectionList.adapter = savedElectionsAdapter


        // TODO: Initiate recycler adapters

        // TODO: Populate recycler adapters
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.upcomingElectionList.observe(viewLifecycleOwner) { elections ->
            upcomingElectionsAdapter.submitList(elections)
        }

        viewModel.savedElectionList.observe(viewLifecycleOwner) { elections ->
            savedElectionsAdapter.submitList(elections)
        }

    }

    private fun navigateToVoteInfo(election: Election) {
        this.findNavController().navigate(
            ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                election
            )
        )
    }

}