package com.example.mapd726_group3_newsbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mapd726_group3_newsbox.databinding.FragmentAlexaBinding
import com.example.mapd726_group3_newsbox.databinding.FragmentCategoryBinding
import com.example.mapd726_group3_newsbox.databinding.FragmentHomeBinding

class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

}