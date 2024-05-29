package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.utils.DataResult
import java.util.Date
import javax.inject.Inject

class ElectionRepositoryImpl @Inject constructor(private val service: CivicsApiService, private val electionDao: ElectionDao) :
    ElectionRepository {
    override suspend fun getUpcomingElections(): DataResult<ElectionResponse> {
        return DataResult.Success(
            ElectionResponse("kind", listOf(
                Election(1, "Election1", Date(), Division("1", "US", "Cali")),
                Election(2, "Election2", Date(), Division("1", "US", "Cali")),
                Election(3, "Election3", Date(), Division("1", "US", "Cali")),
                Election(4, "Election4", Date(), Division("1", "US", "Cali")),
            ))
        )
    }

    override fun getSavedElections(): LiveData<List<Election>> {
        return electionDao.getAllElections()
    }
}