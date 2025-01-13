package com.example.mysololife.board

import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardWriteBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBTime
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    private val db = Firebase.firestore
    private val TAG = BoardWriteActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBTime.getTime()

            db.collection("boards")
                .add(
                    hashMapOf(
                        "title" to title,
                        "content" to content,
                        "uid" to uid,
                        "time" to time,
                    )
                )

            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()

            finish()

        }

        binding.imageArea.setOnClickListener {

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)

        }

    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {

            try {
                val imageUri = data?.data
                imageUri?.let {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.createSource(this.contentResolver, it)
                        val source = ImageDecoder.createSource(this.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                    }
                    binding.imageArea.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "이미지 로딩 실패", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
