package com.example.mysololife.board

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardInsideBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBTime
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding: ActivityBoardInsideBinding
    private val db = Firebase.firestore

    private lateinit var key: String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentLVAdapter: CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        commentLVAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentLVAdapter

        key = intent.getStringExtra("key").toString()

        getBoardData(key)

        getCommentData(key)

        binding.boardSettingIcon.setOnClickListener {

            showDialog()

        }

        binding.commentBtn.setOnClickListener {

            insertComment()

        }

    }

    private fun getCommentData(key: String) {
        db.collection("comments")
            .document(key)
            .collection("comment")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("ContentListActivity", "Listen failed: ", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    // 데이터 변경 시 리스트 초기화
                    commentDataList.clear()

                    for (document in snapshot.documents) {
                        val commentTitle = document.getString("commentTitle") ?: ""
                        val commentCreatedTime = document.getString("commentCreatedTime") ?: ""

                        commentDataList.add(
                            CommentModel(
                                commentTitle = commentTitle,
                                commentCreatedTime = commentCreatedTime
                            )
                        )
                    }

                    commentLVAdapter.notifyDataSetChanged()
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun insertComment() {

        db.collection("comments")
            .document(key)
            .collection("comment")
            .add(
                hashMapOf(
                    "commentTitle" to binding.commentArea.text.toString(),
                    "commentTime" to FBTime.getTime()
                )
            )
            .addOnSuccessListener {

                Toast.makeText(baseContext, "댓글 입력 완료", Toast.LENGTH_LONG).show()

                binding.commentArea.setText("")

            }
            .addOnFailureListener { e ->

                Toast.makeText(baseContext, "댓글 입력 실패: ${e.message}", Toast.LENGTH_LONG).show()

            }
    }

    private fun showDialog() {

        val mDialog = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            db.collection("boards")
                .document(key)
                .delete()

            Toast.makeText(baseContext, "삭제완료", Toast.LENGTH_LONG).show()

            finish()

        }


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

                    binding.titleArea.text = board!!.title
                    binding.contentArea.text = board.content
                    binding.timeArea.text = board.time

                    val myUid = FBAuth.getUid()
                    val writerUid = board.uid

                    if (myUid == writerUid) {
                        binding.boardSettingIcon.isVisible = true
                    }

                } else {
                    Log.d(TAG, "Current data: null")
                }


            }

    }

}