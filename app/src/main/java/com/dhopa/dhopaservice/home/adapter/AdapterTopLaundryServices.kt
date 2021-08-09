package com.dhopa.dhopaservice.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.home.ModelClass.Services

class AdapterTopLaundryServices() : RecyclerView.Adapter<AdapterTopLaundryServices.MyTopServices>() {

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(services: Services) {
//            val image = itemView.findViewById(R.id.iv_service_image) as ImageView
//            val serviceTitle  = itemView.findViewById(R.id.tv_title) as TextView
//            serviceTitle.text = services.title
//            image.setImageResource(services.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_top_preview_services, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {
//        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return 5
    }
}