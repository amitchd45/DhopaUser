package com.dhopa.dhopaservice.sidemenu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.home.ModelClass.Services

class AdapterInProcessTrackingService(val contex : Context, val select : Select) : RecyclerView.Adapter<AdapterInProcessTrackingService.MyTopServices>() {

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll_confirm=itemView.findViewById<LinearLayout>(R.id.ll_confirm)
        fun bindItems(services: Services) {


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_progress, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {

        holder.ll_confirm.setOnClickListener {

            select.click(position)
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    interface Select{
        fun click(position: Int)
    }
}