package com.example.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        binding.storeWebView.loadUrl("https://www.inflearn.com/")

        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)

        }

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_talkFragment)

        }

        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)

        }

        return binding.root
    }

}