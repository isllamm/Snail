package com.tawajood.snail.ui.main.fragments.pets

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
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentAddNewAnimalBinding
import com.tawajood.snail.pojo.PetBody
import com.tawajood.snail.pojo.PetType
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class AddNewAnimalFragment : Fragment() {


    private lateinit var binding: FragmentAddNewAnimalBinding
    private lateinit var parent: MainActivity
    private var petPic: File? = null
    private var types = mutableListOf<PetType>()
    private lateinit var typesAdapter: ArrayAdapter<String>
    private val viewModel: PetsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewAnimalBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        onClick()
        observeData()
        setupUI()
        setupTypes()
        return binding.root
    }


    private fun setupUI() {
        viewModel.getPetTypes()
        parent.showBottomNav(false)
    }

    private fun setupTypes() {
        typesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = typesAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.nextBtn.setOnClickListener {
            if (validate()) {
                viewModel.addPet(
                    PetBody(
                        PrefsHelper.getUserId(),
                        binding.nameEt.text.toString(),
                        binding.ageEt.text.toString(),
                        binding.weightEt.text.toString(),
                        binding.genderSpinner.selectedItemPosition,
                        petPic,
                        types[binding.typeSpinner.selectedItemPosition].id,
                    )
                )
            }
            Log.d("islam", "onClick: " + types[binding.typeSpinner.selectedItemPosition].id.toInt())
        }
        binding.cancelBtn.setOnClickListener {
            parent.onBackPressed()
        }
        binding.addImgCard.setOnClickListener {
            imagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.nameEt.text)) {
            ToastUtils.showToast(requireContext(), "اسم الحيوان مطلوب")
            return false
        }
        if (TextUtils.isEmpty(binding.ageEt.text)) {
            ToastUtils.showToast(requireContext(), "عمر الحيوان مطلوب")

            return false
        }
        if (TextUtils.isEmpty(binding.weightEt.text)) {
            ToastUtils.showToast(requireContext(), "وزن الحيوان مطلوب")

            return false
        }

        return true
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.addAnimalFlow.collectLatest {
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

                        ResultDialogFragment.newInstance(
                            getString(R.string.done),
                            R.drawable.done
                        )
                            .show(
                                parentFragmentManager,
                                ResultDialogFragment::class.java.canonicalName
                            )
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.petTypesFlow.collectLatest {
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
                        types = it.data!!.petTypes
                        types.forEach { types ->
                            typesAdapter.add(types.name)
                        }
                        Log.d("islam", "observeData: " + types[0].name)

                    }
                }
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
                    petPic = file
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