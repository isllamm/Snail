package com.tawajood.snail.ui.main.fragments.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentMapBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.getAddressForTextView

class MapFragment : Fragment(R.layout.fragment_map) {

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        getLastKnownLocation()
        onClick()
    }

    private lateinit var binding: FragmentMapBinding
    private lateinit var parent: MainActivity
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap

    private var clinics = mutableListOf<Clinic>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        parent.showToolbar(false)
        parent.showBottomNav(false)
        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.toolbar.logo)
    }

    private fun onClick(){
        googleMap.setOnMapClickListener {
            binding.clinicCl.isVisible = false
        }
    }

    private fun getLastKnownLocation(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful){
                if (it.result != null) {
                    val myLoc = LatLng(it.result.latitude, it.result.longitude)
                    googleMap.addMarker(MarkerOptions().position(myLoc).title("My Location"))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 17f))



                    clinics.forEach { clinic->
                        setupClinicLocation(clinic)
                    }
                }
                else{
                    getLastKnownLocation()
                }
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    @SuppressLint("InflateParams")
    private fun createClinicMarker(name: String): Bitmap? {
        val markerLayout: View = layoutInflater.inflate(R.layout.clinic_location, null)
        val markerImage: ImageView = markerLayout.findViewById<View>(R.id.marker_image) as ImageView
        val markerText = markerLayout.findViewById<View>(R.id.clinic_name_tv) as TextView
        markerImage.setImageResource(R.drawable.clinic_loc_ic)

        markerText.text = name


        markerLayout.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        markerLayout.layout(0, 0, markerLayout.measuredWidth, markerLayout.measuredHeight)
        val bitmap = Bitmap.createBitmap(
            markerLayout.measuredWidth,
            markerLayout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        markerLayout.draw(canvas)

        return bitmap
    }

    private fun setupClinicLocation(clinic: Clinic) {
        val options = MarkerOptions()
            .position(
                LatLng(
                    clinic.lat,
                    clinic.lng
                )
            )
            .draggable(false)
            .flat(false)
            .icon(
                BitmapDescriptorFactory.fromBitmap(
                    createClinicMarker(clinic.name)!!
                )
            )


        googleMap.addMarker(options)

        googleMap.setOnMarkerClickListener { p0 ->
            val pos = p0.id.filter { it.isDigit() }.toInt()

            if (pos != 0) {
                getAddressForTextView(
                    requireContext(),
                    clinics[pos-1].lat,
                    clinics[pos-1].lng,
                    binding.addressTv
                )
                Glide.with(requireContext())
                    .load(clinics[pos-1].image)
                    .into(binding.clinicImg)
                binding.clinicCl.isVisible = true
            }
            false
        }
    }

}