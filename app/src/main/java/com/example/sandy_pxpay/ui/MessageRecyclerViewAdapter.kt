package com.example.sandy_pxpay.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sandy_pxpay.model.Message
import com.example.sandy_pxpay.R

class MessageRecyclerViewAdapter : RecyclerView.Adapter<MessageRecyclerViewAdapter.MsgViewHolder>() {

    private var msgList = listOf<Message>()

    inner class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.txv_item_title)
        var content: TextView = itemView.findViewById(R.id.txv_item_content)

        fun bind(item: Message) {
            title.text = item.title
            content.text = item.msg
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val msgItemView = inflater.inflate(R.layout.item_msg, parent, false)
        return MsgViewHolder(msgItemView)
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        holder.bind(msgList[position])
    }

    fun updateData(msgList: List<Message>) {
        this.msgList = msgList
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

}