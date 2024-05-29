package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.base.BaseViewModel
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectionsViewModel @Inject constructor(
    private val electionRepository: ElectionRepository
): BaseViewModel()  {

    private val _upcomingElectionList = MutableLiveData<List<Election>>(emptyList())
    val savedElectionList = electionRepository.getSavedElections()
    val upcomingElectionList : LiveData<List<Election>>
        get() = _upcomingElectionList
    

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    private fun refreshUpcomingElectionsList() {
        showLoading.value = true
        viewModelScope.launch {
            when (val result = electionRepository.getUpcomingElections()) {
                is DataResult.Success -> _upcomingElectionList.value = result.data.elections
                is DataResult.Failure -> {

                }
            }
            showLoading.value = false
        }
    }

    init {
        refreshUpcomingElectionsList()
    }


}