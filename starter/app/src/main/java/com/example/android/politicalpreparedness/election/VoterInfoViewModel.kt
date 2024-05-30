package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Constants
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import java.util.Timer
import javax.inject.Inject

@HiltViewModel
class VoterInfoViewModel @Inject constructor(private val electionRepository: ElectionRepository, savedStateHandle: SavedStateHandle): ViewModel() {

    val electionInfo = MutableLiveData<Election>()

    private val _isElectionSaved = MutableLiveData(false)

    val isElectionSaved: LiveData<Boolean>
        get() = _isElectionSaved

    val election: Election =
        savedStateHandle[Constants.KEY_NAV_ELECTION] ?: getFakeData()

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    init {
        updateSaveElectionState()
    }

    private fun updateSaveElectionState() {
        viewModelScope.launch {
            _isElectionSaved.value = electionRepository.getElectionById(election.id) != null
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

    private fun getFakeData(): Election {
        return Election(-1, "", Date(), Division("", "", ""))
    }

}