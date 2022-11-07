package com.tawajood.snail.ui.main.fragments.profile

import android.Manifest
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentEditProfileBinding
import com.tawajood.snail.databinding.FragmentProfileBinding
import com.tawajood.snail.pojo.ProfileBody
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : Fragment() {


    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var parent: MainActivity
    private var profilePic: File? = null
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()
        observeData()
        return binding.root
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.frameEdit.setOnClickListener {
            imagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }
        binding.editBtn.setOnClickListener {
            if (validate()) {
                viewModel.updateProfile(
                    ProfileBody(
                        PrefsHelper.getUserId(),
                        binding.usernameEt.text.toString(),
                        binding.ccp.selectedCountryCode.toString(),
                        binding.phoneEt.text.toString(),
                        binding.emailEt.text.toString(),
                        profilePic
                    )
                )
            }
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.usernameEt.text)) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.name_is_required)
            )
            return false
        }

        if (TextUtils.isEmpty(binding.phoneEt.text)) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.phone_is_required)
            )
            return false
        }

        if (TextUtils.isEmpty(binding.emailEt.text)) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.email)
            )
            return false
        }


        return true
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.profileFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val profile = it.data!!
                        binding.usernameEt.setText(profile.name)
                        binding.phoneEt.setText(profile.phone)
                        binding.ccp.setCountryForPhoneCode(profile.countryCode.toInt())
                        binding.emailEt.setText(profile.email)
                        Glide.with(requireContext())
                            .load(profile.image)
                            .into(binding.profileImg)


                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updateFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        PrefsHelper.setUsername(binding.usernameEt.text.toString())
                        PrefsHelper.setCountryCode(binding.ccp.selectedCountryCode.toString())
                        PrefsHelper.setPhone(binding.phoneEt.text.toString())
                        PrefsHelper.setEmail(binding.emailEt.text.toString())
                        parent.onBackPressed()
                        ResultDialogFragment.newInstance(
                            getString(R.string.profile_updated_successfully), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )
                    }
                }
            }
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
                        .into(binding.profileImg)

                }
                ImagePicker.RESULT_ERROR -> {
                    ToastUtils.showToast(requireContext(), ImagePicker.getError(data))
                }
                else -> {
                    ToastUtils.showToast(requireContext(), "Task Cancelled")
                }
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
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
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