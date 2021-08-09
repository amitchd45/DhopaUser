package com.dhopa.dhopaservice.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.network.models.Detail

class AdapterTopServices(private val context: Context,val serviceList: MutableList<Detail>) : RecyclerView.Adapter<AdapterTopServices.MyTopServices>() {

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.iv_service_image) as ImageView
        val serviceTitle  = itemView.findViewById(R.id.tv_title) as TextView
        fun bindItems(services: Detail) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top_services, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {

        holder.serviceTitle.text=serviceList[position].title
        Glide.with(context).load(serviceList[position].image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}