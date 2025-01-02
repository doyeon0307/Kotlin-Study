package com.example.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.tipTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)

        }

        binding.bookmarkTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)

        }

        return binding.root

    }

}