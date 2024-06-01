package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.utils.DataResult

interface ElectionRepository {
    suspend fun getUpcomingElections(): DataResult<ElectionResponse>

    suspend fun getVoterInfo(electionId: String, address: String): DataResult<VoterInfoResponse>

    fun getSavedElections() : LiveData<List<Election>>

    suspend fun getElectionById(id: Int): Election?

    suspend fun saveElection(election: Election)
    suspend fun deleteElection(election: Election)
}