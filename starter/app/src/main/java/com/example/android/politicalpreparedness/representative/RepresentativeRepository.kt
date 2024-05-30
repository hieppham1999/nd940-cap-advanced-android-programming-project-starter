package com.example.android.politicalpreparedness.representative

import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.utils.DataResult

interface RepresentativeRepository {
    suspend fun getRepresentativeFromNetwork(address: String): DataResult<RepresentativeResponse>

}