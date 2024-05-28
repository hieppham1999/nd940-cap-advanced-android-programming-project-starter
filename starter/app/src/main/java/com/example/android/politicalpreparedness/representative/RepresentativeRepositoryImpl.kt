package com.example.android.politicalpreparedness.representative

import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import javax.inject.Inject

class RepresentativeRepositoryImpl @Inject constructor(private val service: CivicsApiService) :
    RepresentativeRepository {
    override suspend fun getRepresentativeFromNetwork(address: String): Result<RepresentativeResponse> {
        TODO("Not yet implemented")
    }
}