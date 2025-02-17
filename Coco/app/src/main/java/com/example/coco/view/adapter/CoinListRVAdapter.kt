package com.example.coco.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.db.entity.InterestCoinEntity

class CoinListRVAdapter(val context: Context, private val dataSet: List<InterestCoinEntity>) : RecyclerView.Adapter<CoinListRVAdapter.ViewHolder>() {

    // 클릭을 외부에서 처리하기 위한 인터페이스
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coinName = view.findViewById<TextView>(R.id.coinName)!!
        val likeBtn = view.findViewById<ImageView>(R.id.likeBtn)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_coin_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.coinName.text = dataSet[position].coin_name

        // 내 코드
        holder.likeBtn.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        // 강의 코드
//        holder.itemView.findViewById<ImageView>(R.id.likeBtn).setOnClickListener { v ->
//            itemClick?.onClick(v, position)
//        }

        val selected = dataSet[position].selected
        if (selected) {
            holder.likeBtn.setImageResource(R.drawable.like_red)
        } else {
            holder.likeBtn.setImageResource(R.drawable.like_grey)
        }

    }

}