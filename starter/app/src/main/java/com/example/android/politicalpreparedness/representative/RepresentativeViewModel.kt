package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.representative.model.Representative

class RepresentativeViewModel: ViewModel() {

    private val _representativesList = MutableLiveData<List<Representative>>(emptyList())
    val representativesList : LiveData<List<Representative>>
        get() = _representativesList

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

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
