package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by viewModels()
    private lateinit var binding: FragmentVoterInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {

        binding = FragmentVoterInfoBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.electionName.setTitleTextColor(requireContext().getColor(R.color.white))

        viewModel.voterInformation.observe(viewLifecycleOwner) {
            it?.let {

                it.ballotInfoUrl?.let { ballotUrl ->
                    enableLink(binding.stateBallot, ballotUrl)
                }

                it.votingLocationFinderUrl?.let { locationUrl ->
                    enableLink(binding.stateLocations, locationUrl)
                }

                it.correspondenceAddress?.let {
                    binding.stateCorrespondenceHeader.visibility = View.VISIBLE
                }

                it.correspondenceAddress?.let {
                    binding.stateAddress.visibility = View.VISIBLE
                }
            }
        }


        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        binding.buttonSaveElection.setOnClickListener {
            viewModel.followElection()
        }

        return binding.root
    }

    private fun enableLink(view: View, url: String) {
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            setIntent(url)
        }
    }

    private fun setIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}