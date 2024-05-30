package com.example.android.politicalpreparedness.base

import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.utils.SingleLiveEvent
import javax.inject.Inject

open class BaseViewModel @Inject constructor(): ViewModel() {
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
}