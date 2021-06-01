package com.example.sandy_pxpay.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandy_pxpay.model.Message
import com.example.sandy_pxpay.databinding.ActivityMessageBinding
import kotlinx.android.synthetic.main.activity_message.*


class MessageActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMessageBinding
    private val msgAdapter = MessageRecyclerViewAdapter()

    companion object{
        const val ARG_MSGLIST = "msgList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setupView()
    }

    private fun setupView(){
        val msgList: ArrayList<Message> = intent.getParcelableArrayListExtra(ARG_MSGLIST) ?: return
        msgList.apply {
            this.sortByDescending {it.timeStamp}
            msgAdapter.updateData(msgList)
            bind.rvMsg.layoutManager = LinearLayoutManager(this@MessageActivity)
            bind.rvMsg.adapter = msgAdapter

            //  實現拖移、左右滑動刪除的效果
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    msgList.removeAt(position)
                    msgAdapter.notifyItemRemoved(position)
                    Toast.makeText(this@MessageActivity, "已經刪除第 ${position+1} 則通知", Toast.LENGTH_LONG).show()
                }
            }).attachToRecyclerView(rv_msg)
        }
    }
}