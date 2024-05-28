package com.example.android.politicalpreparedness.representative

import com.example.android.politicalpreparedness.network.models.RepresentativeResponse

interface RepresentativeRepository {
    suspend fun getRepresentativeFromNetwork(address: String): Result<RepresentativeResponse>

}