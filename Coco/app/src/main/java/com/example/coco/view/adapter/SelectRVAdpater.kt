package com.example.coco.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.dataModel.CurrentPriceResult
import timber.log.Timber

class SelectRVAdapter(
    val context: Context,
    private var coinPriceList: List<CurrentPriceResult>,
) : RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

    val selectedCoinList = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<CurrentPriceResult>) {
        // 데이터가 올바르게 들어왔는지 로그 확인
        Timber.d("Updating data, new list size: ${newList.size}")

        // null이나 비어있는 리스트를 처리
        if (newList.isNotEmpty()) {
            coinPriceList = newList
            notifyDataSetChanged()
        } else {
            Timber.d("Received empty list")
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coinName: TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown: TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeBtn: ImageView = view.findViewById(R.id.likeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.coinName.text = coinPriceList[position].coinName

        val fluctate = coinPriceList[position].coinInfo.fluctate_24H

        if (fluctate.contains("-")) {
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        } else {
            holder.coinPriceUpDown.text = "상승입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }

        val likeImage = holder.likeBtn
        val currentCoin = coinPriceList[position].coinName

        // 뷰를 그릴 때
        if (selectedCoinList.contains(currentCoin)) {
            likeImage.setImageResource(R.drawable.like_red)
        } else {
            likeImage.setImageResource(R.drawable.like_grey)
        }

        // 하트를 선택할 때
        likeImage.setOnClickListener {

            if (selectedCoinList.contains(currentCoin)) {
                likeImage.setImageResource(R.drawable.like_grey)
                selectedCoinList.remove(currentCoin)
            } else {
                likeImage.setImageResource(R.drawable.like_red)
                selectedCoinList.add(currentCoin)
            }

        }
    }
}
