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
import com.example.mysololife.databinding.FragmentBookmarkBinding
import com.example.mysololife.utils.FBAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val bookmarkIdList = ArrayList<String>()
    private val itemList = ArrayList<ContentModel>()
    private val itemKeyList = ArrayList<String>()

    private lateinit var rvAdapter: BookmarkRVAdapter
    private val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        // 2. 북마크 정보 가져오기
        getBookmarks()

        // 3. 북마크된 컨텐츠만 보여주기
        rvAdapter = BookmarkRVAdapter(requireContext(), itemList, itemKeyList, bookmarkIdList)
        val rv: RecyclerView = binding.bookmarkRV
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)

        }

        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)

        }

        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)

        }

        return binding.root
    }

    private fun getBookmarks() {
        val uid = FBAuth.getUid()

        db.collection("bookmarks")
            .document(uid)
            .collection("markedContents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    bookmarkIdList.add(document["contentId"] as String)

                }
                getContents()

            }.addOnFailureListener { exception ->
                Log.w("ContentListActivity", "Error getting documents: $exception")
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getContents() {
        db.collection("contents").get().addOnSuccessListener { result ->
            for (document in result) {

                val id = document.id

                if (bookmarkIdList.contains(id)) {

                    itemList.add(ContentModel(title = document["title"] as String, imageUrl = document["imageUrl"] as String, webUrl = document["webUrl"] as String))
                    itemKeyList.add(id)

                }

            }
            rvAdapter.notifyDataSetChanged()

        }.addOnFailureListener { exception ->
            Log.w("ContentListActivity", "Error getting documents: $exception")
        }
    }


}