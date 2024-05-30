package com.example.android.politicalpreparedness.utils

import android.content.Context
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.R

@BindingAdapter("followElectionText")
fun Button.bindFollowElectionButtonText(isElectionSaved: Boolean) {
    text = context.getString(if (isElectionSaved) R.string.button_unfollow_election else R.string.button_follow_election)
}