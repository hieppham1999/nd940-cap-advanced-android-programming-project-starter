package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.utils.DataResult

interface ElectionRepository {
    suspend fun getUpcomingElections(): DataResult<ElectionResponse>

    fun getSavedElections() : LiveData<List<Election>>
}