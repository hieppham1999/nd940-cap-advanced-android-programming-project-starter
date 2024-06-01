package com.example.android.politicalpreparedness.voterinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Constants
import com.example.android.politicalpreparedness.election.ElectionRepository
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.model.VoterInfo
import com.example.android.politicalpreparedness.utils.DataResult
import com.example.android.politicalpreparedness.utils.toVoterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class VoterInfoViewModel @Inject constructor(private val electionRepository: ElectionRepository, savedStateHandle: SavedStateHandle): ViewModel() {

    val voterInformation = MutableLiveData<VoterInfo?>()

    private val _isElectionSaved = MutableLiveData(false)

    val isElectionSaved: LiveData<Boolean>
        get() = _isElectionSaved

    val election: Election =
        savedStateHandle[Constants.KEY_NAV_ELECTION] ?: getFakeElection()

    init {
        updateSaveElectionState()
        getVoterInfo()
    }

    private fun updateSaveElectionState() {
        viewModelScope.launch {
            _isElectionSaved.value = electionRepository.getElectionById(election.id) != null
        }
    }

    private fun getVoterInfo() {
        viewModelScope.launch {
            val result = electionRepository.getVoterInfo(election.id.toString(), "${election.division.state}, ${election.division.country}")
            voterInformation.value = when (result) {
                is DataResult.Success -> result.data.toVoterInfo()
                else -> null
            }
        }
    }

    fun followElection() {
        viewModelScope.launch {
            if (_isElectionSaved.value == true) {
                electionRepository.deleteElection(election)
            } else {
                electionRepository.saveElection(election)
            }
        }
        updateSaveElectionState()
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    private fun getFakeElection(): Election {
        return Election(-1, "", Date(), Division("", "", ""))
    }

}