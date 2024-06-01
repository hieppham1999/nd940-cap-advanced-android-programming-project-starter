package com.example.android.politicalpreparedness.utils

import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.VoterInfo
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

fun VoterInfoResponse.toVoterInfo(): VoterInfo? {
    val administrationData = this.state?.first()?.electionAdministrationBody

    return if (administrationData != null) {
        VoterInfo(
            this.state?.first()!!.name,
            administrationData.votingLocationFinderUrl,
            administrationData.ballotInfoUrl,
            administrationData.correspondenceAddress?.toFormattedString()
        )
    } else {
        null
    }
}

fun android.location.Address.toAppAddress(): Address? {
    if (thoroughfare == null || subThoroughfare == null || locality == null || adminArea == null || postalCode == null) {
        return null
    }
    return Address(
        thoroughfare,
        subThoroughfare,
        locality,
        adminArea,
        postalCode
    )
}