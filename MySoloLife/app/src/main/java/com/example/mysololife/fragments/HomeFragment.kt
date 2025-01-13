package com.example.mysololife.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.contentsList.BookmarkRVAdapter
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val db = Firebase.firestore

    private lateinit var rvAdapter: BookmarkRVAdapter

    private val bookmarkIdList = ArrayList<String>()
    private val itemList = ArrayList<ContentModel>()
    private val itemKeyList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        rvAdapter = BookmarkRVAdapter(requireContext(), itemList, itemKeyList, bookmarkIdList)

        val rv: RecyclerView = binding.mainRV
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        getContents()

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)

        }

        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)

        }

        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getContents() {
        db.collection("contents").get().addOnSuccessListener { result ->
            for (document in result) {

                val id = document.id

                itemList.add(
                    ContentModel(
                        title = document["title"] as String,
                        imageUrl = document["imageUrl"] as String,
                        webUrl = document["webUrl"] as String
                    )
                )
                
                itemKeyList.add(id)

            }
            rvAdapter.notifyDataSetChanged()

        }.addOnFailureListener { exception ->
            Log.w("ContentListActivity", "Error getting documents: $exception")
        }
    }

}