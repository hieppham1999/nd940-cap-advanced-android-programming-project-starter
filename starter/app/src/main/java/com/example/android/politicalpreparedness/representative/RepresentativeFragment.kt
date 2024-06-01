package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.base.BaseFragment
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    override val viewModel: RepresentativeViewModel by viewModels()

    private lateinit var representativeListAdapter: RepresentativeListAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isLocationPermissionGranted = false
        permissions.entries.forEach {
            if (it.value) {
                isLocationPermissionGranted = true
            }
        }
        if (isLocationPermissionGranted) {
            getLocationAndSearchForRepresentatives()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.permission_denied_explanation),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentRepresentativeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        representativeListAdapter  = RepresentativeListAdapter(RepresentativeListener {

        })
        binding.representativeList.adapter = representativeListAdapter

        binding.buttonUseMyLocation.setOnClickListener {
            hideKeyboard()
            getLocationAndSearchForRepresentatives()
        }

        binding.buttonSearch.setOnClickListener {
            hideKeyboard()
            viewModel.searchForRepresentative()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.representativesList.observe(viewLifecycleOwner) { representativeList ->
            representativeListAdapter.submitList(representativeList)
        }

    }

    private fun getLocationAndSearchForRepresentatives() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    geoCodeLocationAndSearch(it)
                }
            }

    }

    private fun geoCodeLocationAndSearch(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<android.location.Address>) {
                    Timber.d("geoCodeLocation: ${addresses.first()}")
                    viewModel.fetchInputFromAddressThenSearch(addresses.first().toAppAddress())
                }
            }
            geocoder.getFromLocation(location.latitude, location.longitude, 1,geocodeListener)
        } else {
            geocoder.getFromLocation(location.latitude, location.longitude, 1,)
                ?.map { address ->
                    Timber.d("geoCodeLocation: $address")
                    viewModel.fetchInputFromAddressThenSearch(address.toAppAddress())
                }
                ?.first()
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun android.location.Address.toAppAddress(): Address? {
        if (thoroughfare == null || subThoroughfare == null || locality == null || adminArea == null || postalCode == null) {
            // show error
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
}