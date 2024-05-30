package com.example.android.politicalpreparedness.representative

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseViewModel
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepresentativeViewModel @Inject constructor(
    private val representativeRepository: RepresentativeRepository,
): BaseViewModel(){

    private val _representativesList = MutableLiveData<List<Representative>>(emptyList())
    val representativesList : LiveData<List<Representative>>
        get() = _representativesList

    val currentAddress : Address?
        get() = addressFromInputFields()

    val addressLine1: MutableLiveData<String?> = MutableLiveData()
    val addressLine2: MutableLiveData<String?> = MutableLiveData()
    val city: MutableLiveData<String?> = MutableLiveData()
    val zipCode: MutableLiveData<String?> = MutableLiveData()
    val state: MutableLiveData<String?> = MutableLiveData()

    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun searchForRepresentative() {
        viewModelScope.launch {
            if (currentAddress == null) return@launch
            val result = representativeRepository.getRepresentativeFromNetwork(currentAddress!!.toFormattedString())

            if (result.isSuccess && result.resultData != null) {
                _representativesList.value = result.resultData!!.offices.flatMap { office ->
                    office.getRepresentatives(result.resultData!!.officials)
                }
            } else {
                _representativesList.value = emptyList()
                showSnackBarInt.value = R.string.error
            }
        }
    }

    private fun parseAddressToInputFields(address: Address?) {
        address?.let {
            addressLine1.value = it.line1
            addressLine2.value = it.line2
            city.value = it.city
            zipCode.value = it.zip
            state.value = it.state
        }
    }

    fun fetchInputFromAddressThenSearch(address: Address?) {
        parseAddressToInputFields(address)
        searchForRepresentative()
    }

    private fun addressFromInputFields(): Address? {
        if (addressLine1.value == null || city.value == null || zipCode.value == null || state.value == null) {
            return null
        }
        return Address(
            addressLine1.value!!,
            addressLine2.value,
            city.value!!,
            zipCode.value!!,
            state.value!!
        )
    }





    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
