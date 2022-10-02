package com.tawajood.snail.ui.main.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddNewAnimalBinding
import com.tawajood.snail.databinding.FragmentMakeReservationBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.ToastUtils
import java.io.File


class AddNewAnimalFragment : Fragment() {


    private lateinit var binding: FragmentAddNewAnimalBinding
    private lateinit var parent: MainActivity
    private var profilePic: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewAnimalBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        onClick()
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.nextBtn.setOnClickListener {
            parent.onBackPressed()
        }
        binding.cancelBtn.setOnClickListener {
            parent.onBackPressed()
        }
        binding.addImgCard.setOnClickListener {
            imagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val imagePermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    pickImageResultLauncher.launch(intent)
                }
        } else {
            Log.e("isllam", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
        }
    }

    private var pickImageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            val resultCode = result.resultCode
            val data = result.data!!

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val file = File(getRealPathFromURI(parent, data.data!!))
                    profilePic = file
                    Glide.with(requireContext())
                        .load(file)
                        .into(binding.animalImg)

                }
                ImagePicker.RESULT_ERROR -> {
                    ToastUtils.showToast(requireContext(), ImagePicker.getError(data))
                }
                else -> {
                    ToastUtils.showToast(requireContext(), "Task Cancelled")
                }
            }
        }

    private fun getRealPathFromURI(activity: Activity, contentURI: Uri): String {
        val result: String
        val cursor: Cursor? = activity.contentResolver
            .query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path.toString()
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(Constants._DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


}