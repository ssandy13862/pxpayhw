package com.example.sandy_pxpay.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sandy_pxpay.R
import com.example.sandy_pxpay.model.Banner


class BannerRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<BannerRecyclerViewAdapter.MsgViewHolder>() {

    private var bannerList = listOf<Banner>()

    inner class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var banner: ImageView = itemView.findViewById(R.id.iv_item_banner)

        fun bind(item: Banner) {

            Glide.with(context)
                .load(item.image)
                .placeholder(R.drawable.ic_background)
                .into(banner)

            banner.setOnClickListener {
                val uri = Uri.parse(item.targetUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bannerItemView = inflater.inflate(R.layout.item_banner, parent, false)
        return MsgViewHolder(bannerItemView)
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    fun updateData(bannerList: List<Banner>) {
        this.bannerList = bannerList
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

}