package com.tawajood.snail.ui.main.fragments.make_reservation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.tawajood.snail.R
import com.tawajood.snail.adapters.DaysAdapter
import com.tawajood.snail.adapters.RecordedPetsAdapter
import com.tawajood.snail.adapters.TimesAdapter
import com.tawajood.snail.databinding.FragmentMakeReservationBinding
import com.tawajood.snail.pojo.*
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates
import com.google.android.gms.location.Priority
import com.tawajood.snail.adapters.ImagesAdapter
import com.tawajood.snail.data.PrefsHelper
import java.io.File

@AndroidEntryPoint
class MakeReservationFragment : Fragment(R.layout.fragment_make_reservation) {


    private val viewModel: RequestsViewModel by viewModels()
    private lateinit var binding: FragmentMakeReservationBinding
    private lateinit var parent: MainActivity
    private lateinit var reqAdapter: ArrayAdapter<String>
    private lateinit var speAdapter: ArrayAdapter<String>
    private var types = mutableListOf<RequestTypes>()
    private lateinit var petsAdapter: RecordedPetsAdapter
    private lateinit var daysAdapter: DaysAdapter
    private lateinit var timesAdapter: TimesAdapter
    private lateinit var imagesAdapter: ImagesAdapter
    private var pets = mutableListOf<Pet>()
    private var days = mutableListOf<ClinicDays>()
    private var day = mutableListOf<Day>()
    private var times = mutableListOf<Times>()
    private lateinit var clinic: Clinic
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng
    private var images = mutableListOf<UwMediaPickerMediaModel>()
    private val imagesFiles = mutableListOf<File>()
    private var specializations = mutableListOf<Specializations>()

    private var petId by Delegates.notNull<Int>()
    private var clinicId by Delegates.notNull<Int>()
    private var clinicDayId by Delegates.notNull<Int>()
    private var clinicTimeId by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeReservationBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        clinicId = requireArguments().getInt(Constants.CLINIC)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        //getCurrentLocation()
        onClick()
        observeData()
        setupTypes()
        setupDays()
        setupUI()
        setupPetsRecycler()
        setupTimes()
        setupSpeTypes()
        setupImageRecycler()
        return binding.root
    }

    private fun setupTimes() {
        timesAdapter = TimesAdapter(object : TimesAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                clinicDayId = times[position].clinicDayId
                clinicTimeId = times[position].id
            }

        })

        binding.rvTimes.adapter = timesAdapter
    }

    private fun setupDays() {
        daysAdapter = DaysAdapter(object : DaysAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                times = days[position].times
                timesAdapter.times = times

            }

        })

        binding.rvDays.adapter = daysAdapter
    }

    private fun setupUI() {
        viewModel.getClinicInfo(clinicId.toString())
        viewModel.getPets()
        viewModel.getRequestTypes()
    }

    private fun setupImageRecycler() {
        imagesAdapter = ImagesAdapter(object : ImagesAdapter.OnDeleteImageClickListener {
            override fun onDeleteClickListener(position: Int) {
                //images.removeAt(position)
                //imagesFiles.removeAt(position + 1)
                //imagesAdapter.setImages(images)

            }

        })
        if (images.isNotEmpty()) {
            binding.imagesRv.visibility = View.VISIBLE
        }
        imagesAdapter.setImages(images)
        binding.imagesRv.adapter = imagesAdapter
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        Log.d("islam", "Request Per: Hi")
        if (result) {
            getCurrentLocation()
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.imgBtn.setOnClickListener {
            if (!SmartLocation.with(requireContext()).location().state()
                    .locationServicesEnabled()
            ) {
                ToastUtils.showToast(
                    requireContext(),
                    getString(R.string.location),
                )
            } else {
                parent.showLoading()
                locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                Log.d("islam", "onClick: Hi")
            }
        }
        binding.addImgCard.setOnClickListener {
            storagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.cancelBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_makeReservationFragment_to_cancelReservationSheetFragment)
        }
        binding.nextBtn.setOnClickListener {
            if (validate()) {
                viewModel.addRequest(
                    AddRequestBody(
                        PrefsHelper.getUserId(),
                        clinicId,
                        petId,
                        specializations[binding.typeSpeSpinner.selectedItemPosition].specialization_id.toInt(),
                        types[binding.typeSpinner.selectedItemPosition].id,
                        binding.detailsEt.text.toString(),
                        clinicDayId,
                        clinicTimeId,
                        binding.addressEt.text.toString(),
                        lat!!,
                        lng!!,
                    ),
                    ImagesBody(imagesFiles)
                )
            }


        }
        binding.btnAddAnimal.setOnClickListener {
            parent.navController.navigate(R.id.addNewAnimalFragment)
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.detailsEt.text)) {
            ToastUtils.showToast(requireContext(), "التفاصيل الاستشارة مطلوب")
            return false
        }
        if (TextUtils.isEmpty(binding.addressEt.text)) {
            ToastUtils.showToast(requireContext(), "العنوان مطلوب")

            return false
        }
        if (petId == null) {
            ToastUtils.showToast(requireContext(), " الحيوان مطلوب")

            return false
        }

        if (clinicDayId == null) {
            ToastUtils.showToast(requireContext(), "اختر اليوم")

            return false
        }
        if (clinicTimeId == null) {
            ToastUtils.showToast(requireContext(), "اختر الوقت")

            return false
        }
        if (lat == null || lng == null) {
            ToastUtils.showToast(requireContext(), "الرجاء تفعيل GPS للحصول على العنوان")
            return false
        }
        if (imagesFiles.isEmpty()) {
            ToastUtils.showToast(requireContext(), "يحب اختيار صورة على الاقل و 4 صور على الاكثر")

        }

        return true
    }

    private fun setupTypes() {
        reqAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = reqAdapter
    }

    private fun setupSpeTypes() {
        speAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpeSpinner.adapter = speAdapter
    }


    private fun setupPetsRecycler() {
        petsAdapter = RecordedPetsAdapter(object : RecordedPetsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                petId = pets[position].id

            }

        })

        binding.rvAnimals.adapter = petsAdapter
    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.requestTypesFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        types = it.data!!.types
                        types.forEach { types ->
                            reqAdapter.add(types.name)
                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.myPetsFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {

                        pets = it.data!!.pets
                        petsAdapter.pets = pets

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.clinicInfoFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {

                        clinic = it.data!!.data!!.clinics[0]
                        days = clinic.clinicDays
                        days.forEach { days -> day.add(days.day) }
                        daysAdapter.days = day

                        specializations = clinic.specializations

                        specializations.forEach { specializations ->
                            speAdapter.add(
                                specializations.specialization.name
                            )
                        }

                        //binding.tvSpecialties.text = clinic.specialization.specialization.name
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addRequestFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        parent.navController.navigate(R.id.successfulOrderSheetFragment)

                    }
                }
            }
        }
    }


    private fun getCurrentLocation() {
        parent.showLoading()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnCompleteListener {
                parent.hideLoading()
                it.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLatLng = LatLng(location.latitude, location.longitude)
                        lat = location.latitude
                        lng = location.longitude

                        getAddressForTextView(
                            requireContext(),
                            location.latitude,
                            location.longitude,
                            binding.addressEt
                        )
                    } else {
                        getCurrentLocation()
                    }
                }
                it.addOnFailureListener { e ->
                    Log.d("islam", "getLastKnownLocation: ${e.message}")
                }
            }
    }

    private val storagePermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            pickImages()
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    private fun pickImages() {

        UwMediaPicker
            .with(this)                        // Activity or Fragment
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery/VideoGallery/ImageAndVideoGallery, default is ImageGallery
            .setGridColumnCount(4)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(5)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is light status bar enable, default is true
            .enableImageCompression(true)                // Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)                // Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)                // Compressed image's max height px, default is 720
            .setCompressFormat(Bitmap.CompressFormat.JPEG)        // Compressed image's format, default is JPEG
            .setCompressionQuality(85)                // Image compression quality, default is 85
            .launch { selectedMediaList ->
                run {
                    if (!selectedMediaList.isNullOrEmpty()) {
                        selectedMediaList.forEach {
                            imagesFiles.add(File(it.mediaPath))
                        }
                        Log.d("islam", "onActivityResult: 1")
                        binding.imagesRv.visibility = View.VISIBLE
                        Log.d("islam", "onActivityResult: 2")
                        images = selectedMediaList as MutableList<UwMediaPickerMediaModel>
                        Log.d("islam", "onActivityResult: 3")
                        imagesAdapter.setImages(images)
                        Log.d("islam", "onActivityResult: 4")
                    }
                }
            }

    }
}