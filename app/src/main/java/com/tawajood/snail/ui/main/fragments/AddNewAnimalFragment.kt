package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddNewAnimalBinding
import com.tawajood.snail.databinding.FragmentMakeReservationBinding
import com.tawajood.snail.ui.main.MainActivity


class AddNewAnimalFragment : Fragment() {


    private lateinit var binding: FragmentAddNewAnimalBinding
    private lateinit var parent: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewAnimalBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        onClick()

        return binding.root
    }

    private fun onClick() {

    }


}