package com.example.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ContentRVAdapter(val context: Context, private val items: ArrayList<ContentModel>, private val keys: ArrayList<String>, private val markedIds: ArrayList<String>) : RecyclerView.Adapter<ContentRVAdapter.Viewholder>() {

    // 만들어 둔 아이템을 가져 옴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.Viewholder, position: Int) {

        holder.bindItems(items[position], keys[position])

    }

    // 전체 아이템의 개수
    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel, key: String) {

            itemView.setOnClickListener {

                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)

            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (markedIds.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            bookmarkArea.setOnClickListener {
                val db = Firebase.firestore

                val uid = FBAuth.getUid()

                if (markedIds.contains(key)) {
                    // 북마크가 있을 때
                    markedIds.remove(key)
                    bookmarkArea.setImageResource(R.drawable.bookmark_white)
                    db.collection("bookmarks")
                        .document(uid)
                        .collection("markedContents")
                        .document(key)
                        .delete()


                } else {
                    // 북마크가 없을 때
                    markedIds.add(key)
                    bookmarkArea.setImageResource(R.drawable.bookmark_color)
                    db.collection("bookmarks")
                        .document(uid)
                        .collection("markedContents")
                        .document(key)
                        .set(
                            hashMapOf(
                                "contentId" to key
                            )
                        )

                }
            }

            contentTitle.text = item.title
            Glide.with(context).load(item.imageUrl).into(imageViewArea)

        }

    }
}