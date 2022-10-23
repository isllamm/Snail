package com.tawajood.snail.ui.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.tawajood.snail.R
import com.tawajood.snail.databinding.ActivityMapsBinding
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.ToastUtils
import com.tawajood.snail.utils.getAddressForTextView
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val TAG = "MapActivity"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var selectedLocation: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectedLocation = LatLng(
            intent.getDoubleExtra(Constants.LAT, 0.0),
            intent.getDoubleExtra(Constants.LNG, 0.0)
        )

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapLoc) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onClick()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = CameraUpdateFactory.newLatLngZoom(selectedLocation, 12f)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.animateCamera(location)
        mMap.setOnCameraIdleListener {
            selectedLocation = LatLng(
                mMap.cameraPosition.target.latitude,
                mMap.cameraPosition.target.longitude
            )

            getAddressForTextView(
                this,
                mMap.cameraPosition.target.latitude,
                mMap.cameraPosition.target.longitude,
                binding.locationTv
            )
        }
    }

    private fun onClick() {
        binding.confirmBtn.setOnClickListener {
            val retIntent = Intent()
            retIntent.putExtra("lat", selectedLocation.latitude)
            retIntent.putExtra("lng", selectedLocation.longitude)
            setResult(RESULT_OK, retIntent)
            finish()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var addresses = mutableListOf<Address>()

                if (query != null || query != "") {
                    val geoCoder = Geocoder(this@MapsActivity)
                    try {
                        addresses = geoCoder.getFromLocationName(query, 1)
                    } catch (e: IOException) {
                        Log.d(TAG, "onQueryTextSubmit: ${e.message}")
                    }
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val latLng = LatLng(address.latitude, address.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                    } else {
                        ToastUtils.showToast(
                            this@MapsActivity,
                            getString(R.string.error_location_address)
                        )
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}