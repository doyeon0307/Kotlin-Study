package com.example.mysololife.contentsList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R

class ContentRVAdapter(val context: Context, private val items: ArrayList<ContentModel>) : RecyclerView.Adapter<ContentRVAdapter.Viewholder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    // 만들어 둔 아이템을 가져 옴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.Viewholder, position: Int) {

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }

        holder.bindItems(items[position])
    }

    // 전체 아이템의 개수
    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel) {

            val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)

            contentTitle.text = item.title
            Glide.with(context).load(item.imageUrl).into(imageViewArea)

        }

    }
}