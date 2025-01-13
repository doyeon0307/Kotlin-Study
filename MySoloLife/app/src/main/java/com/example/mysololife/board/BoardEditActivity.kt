package com.example.mysololife.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardEditBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBTime
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BoardEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardEditBinding
    private val TAG = BoardEditActivity::class.java.simpleName
    private val db = Firebase.firestore
    private lateinit var key: String
    private lateinit var writerUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)
        key = intent.getStringExtra("key").toString()

        getBoardData(key)

        binding.editBtn.setOnClickListener {

            editBoardDate(key)

        }

    }

    private fun editBoardDate(key: String) {

        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        val uid = writerUid
        val time = FBTime.getTime()

        db.collection("boards")
            .document(key)
            .update(
                hashMapOf(
                    "title" to title,
                    "content" to content,
                    "uid" to uid,
                    "time" to time,
                ) as Map<String, Any>
            )

        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()

    }

    private fun getBoardData(key: String) {

        db.collection("boards")
            .document(key)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed:", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val board = snapshot.toObject(BoardModel::class.java)

                    binding.titleArea.setText(board!!.title)
                    binding.contentArea.setText(board.content)
                    writerUid = board.uid

                } else {
                    Log.d(TAG, "Current data: null")
                }


            }

    }

}