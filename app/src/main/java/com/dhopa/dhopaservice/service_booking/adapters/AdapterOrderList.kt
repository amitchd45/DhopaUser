package com.dhopa.dhopaservice.service_booking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.home.ModelClass.Services

class AdapterOrderList(val contax:Context,val select:Select) : RecyclerView.Adapter<AdapterOrderList.MyTopServices>() {

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(services: Services) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_order_list, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {

        holder.itemView.setOnClickListener {
            select.onClick(position)
        }

    }

    override fun getItemCount(): Int {
        return 8
    }

    interface Select{
        fun onClick(position: Int)
    }
}