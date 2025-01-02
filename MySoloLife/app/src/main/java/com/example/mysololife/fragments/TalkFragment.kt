package com.example.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.databinding.FragmentTalkBinding

class TalkFragment : Fragment() {

    private lateinit var binding: FragmentTalkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        binding.tipTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)

        }

        binding.homeTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)

        }

        binding.bookmarkTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener{

            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)

        }

        return binding.root

    }

}