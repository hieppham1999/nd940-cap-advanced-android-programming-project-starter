package com.example.android.politicalpreparedness.utils

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R

@BindingAdapter("followElectionText")
fun Button.bindFollowElectionButtonText(isElectionSaved: Boolean) {
    text = context.getString(if (isElectionSaved) R.string.button_unfollow_election else R.string.button_follow_election)
}

@BindingAdapter("representativePhoto")
fun ImageView.bindImageFromNetwork(url: String?) {
    if (url == null) return
    val uri = url.toUri().buildUpon().scheme("https").build()
    Glide
        .with(context)
        .load(uri)
        .circleCrop()
        .placeholder(R.drawable.ic_profile)
        .into(this);
}