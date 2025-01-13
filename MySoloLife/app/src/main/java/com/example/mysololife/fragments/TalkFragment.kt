package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.board.BoardInsideActivity
import com.example.mysololife.board.BoardListRVAdapter
import com.example.mysololife.board.BoardModel
import com.example.mysololife.board.BoardWriteActivity
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.utils.FBAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TalkFragment : Fragment() {

    private lateinit var binding: FragmentTalkBinding
    private val boardList = mutableListOf<BoardModel>()
    private val db = Firebase.firestore
    private val TAG = TalkFragment::class.java.simpleName
    private lateinit var boardRVAdapter: BoardListRVAdapter
    private val boardKeyList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardRVAdapter = BoardListRVAdapter(boardList)
        binding.boardListView.adapter = boardRVAdapter

        getFBBoardData()

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)

        }

        binding.writeBtn.setOnClickListener {

            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)

        }

        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)

        }

        binding.homeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)

        }

        binding.bookmarkTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)

        }

        binding.storeTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)

        }

        return binding.root

    }

    private fun getFBBoardData() {
        db.collection("boards")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed:", e)
                    return@addSnapshotListener
                }

                if (snapshots == null) {
                    Log.w(TAG, "Snapshots is null")
                    return@addSnapshotListener
                }

                boardList.clear()

                for (document in snapshots) {
                    try {
                        boardList.add(
                            BoardModel(
                                title = document["title"] as String,
                                content = document["content"] as String,
                                uid = document["uid"] as String,
                                time = document["time"] as String
                            )
                        )
                        boardKeyList.add(document.id)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing document: ${document.id}", e)
                    }
                }

                boardKeyList.reverse()
                boardList.reverse()
                boardRVAdapter.notifyDataSetChanged()
            }
    }

}