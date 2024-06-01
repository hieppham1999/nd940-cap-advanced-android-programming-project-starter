package com.example.android.politicalpreparedness.representative

import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RepresentativeRepositoryImpl @Inject constructor(private val service: CivicsApiService) :
    RepresentativeRepository {
    override suspend fun getRepresentativeFromNetwork(address: String): DataResult<RepresentativeResponse> =
        withContext(Dispatchers.IO) {
            try {
                val list = service.getRepresentativeList(CivicsHttpClient.API_KEY, address)
                list.officials.forEach {
                    Timber.d("name: ${it.name}, url: ${it.photoUrl}")
                }
                DataResult.Success(list)
            } catch (ex: Exception) {
                DataResult.Failure(ex.localizedMessage ?: "")
            }
        }
}