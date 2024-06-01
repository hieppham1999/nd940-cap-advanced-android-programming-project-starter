package com.example.android.politicalpreparedness.utils

import com.example.android.politicalpreparedness.network.models.VoterInfo
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