package com.example.mysololife.contentsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ContentListActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_content_list)

        val rv: RecyclerView = findViewById(R.id.rv)

        val items = ArrayList<ContentModel>()
        val keys = ArrayList<String>()
        val markedIds = ArrayList<String>()

        val rvAdapter = ContentRVAdapter(baseContext, items, keys, markedIds)
        rv.adapter = rvAdapter

        val db = Firebase.firestore

        val category = intent.getStringExtra("category")

        var collectionPath = ""

        if (category == "category1") {

            collectionPath = "contents"

        } else if (category == "category2") {

            collectionPath = "contents"

        }

        db.collection(collectionPath).get().addOnSuccessListener { result ->
            for (document in result) {
                items.add(ContentModel(title = document["title"] as String, imageUrl = document["imageUrl"] as String, webUrl = document["webUrl"] as String))
                keys.add(document.id)
            }
            rvAdapter.notifyDataSetChanged()

        }.addOnFailureListener { exception ->
            Log.w("ContentListActivity", "Error getting documents: $exception")
        }

        rv.layoutManager = GridLayoutManager(this, 2)


        val uid = FBAuth.getUid()

        db.collection("bookmarks")
            .document(uid)
            .collection("markedContents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    markedIds.add(document["contentId"] as String)

                }

                rvAdapter.notifyDataSetChanged()

            }.addOnFailureListener { exception ->
                Log.w("ContentListActivity", "Error getting documents: $exception")
            }
    }
}