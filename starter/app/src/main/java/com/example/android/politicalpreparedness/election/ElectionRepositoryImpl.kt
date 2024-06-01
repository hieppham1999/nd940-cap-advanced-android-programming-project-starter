package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ElectionRepositoryImpl @Inject constructor(private val service: CivicsApiService, private val electionDao: ElectionDao) :
    ElectionRepository {
    override suspend fun getUpcomingElections(): DataResult<ElectionResponse> = withContext(Dispatchers.IO) {
        try {
            val result = service.getElections(CivicsHttpClient.API_KEY)
            DataResult.Success(result)
        } catch (ex: Exception) {
            Timber.e(ex.message)
            DataResult.Failure(ex.message ?: "")
        }
    }

    override suspend fun getVoterInfo(electionId: String, address: String): DataResult<VoterInfoResponse> = withContext(Dispatchers.IO) {
        try {
            val result = service.getVoterInfo(CivicsHttpClient.API_KEY, address, electionId)
            DataResult.Success(result)
        } catch (ex: Exception) {
            DataResult.Failure(ex.message ?: "")
        }
    }

    override fun getSavedElections(): LiveData<List<Election>> {
        return electionDao.getAllElections()
    }

    override suspend fun getElectionById(id: Int): Election? {
        return electionDao.getElectionById(id)
    }

    override suspend fun saveElection(election: Election) {
        return electionDao.insertElection(election)
    }

    override suspend fun deleteElection(election: Election) {
        electionDao.deleteElection(election)
    }
}