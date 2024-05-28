package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.utils.DataResult
import javax.inject.Inject

class ElectionRepositoryImpl @Inject constructor(private val service: CivicsApiService, private val electionDao: ElectionDao) :
    ElectionRepository {
    override suspend fun getUpcomingElections(): DataResult<ElectionResponse> {
        TODO("Not yet implemented")
    }

    override fun getSavedElections(): LiveData<List<Election>> {
        return electionDao.getAllElections()
    }
}