package com.example.mysololife.board

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth

class BoardListRVAdapter(private val boardList: MutableList<BoardModel>) : BaseAdapter() {
    override fun getCount(): Int {

        return boardList.size

    }

    override fun getItem(position: Int): Any {

        return boardList[position]

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)


        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        if (boardList[position].uid == FBAuth.getUid()) {
            itemLinearLayoutView?.setBackgroundColor(R.color.mainColor)
        }

        title!!.text = boardList[position].title
        content!!.text = boardList[position].content
        time!!.text = boardList[position].time

        return view!!

    }


}