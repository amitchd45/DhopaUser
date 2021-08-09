package com.dhopa.dhopaservice.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.home.ModelClass.Services

class AdapterOffers() : RecyclerView.Adapter<AdapterOffers.MyTopServices>() {

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(services: Services) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_offer, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}