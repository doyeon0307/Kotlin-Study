package com.example.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        binding.tipTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)

        }

        binding.homeTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)

        }

        binding.storeTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)

        }


        return binding.root
    }

}