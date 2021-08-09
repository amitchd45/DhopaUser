package com.dhopa.dhopaservice.cart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.network.models.Detail
import com.dhopa.dhopaservice.profile.adapter.AdapterAddressList

class AdapterChooseTopServices(val context: Context, val serviceList: MutableList<Detail>,val select:Select) : RecyclerView.Adapter<AdapterChooseTopServices.MyTopServices>() {

    private  var lastSelectedPosition:Int=-1

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.iv_service_image) as ImageView
        val serviceTitle  = itemView.findViewById(R.id.tv_title) as TextView
        val mSelectType  = itemView.findViewById(R.id.rb_button) as RadioButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_choose_services, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {
        holder.serviceTitle.text = serviceList[position].title
        Glide.with(context).load(serviceList[position].image).into(holder.image)

        holder.mSelectType.setOnClickListener {
            lastSelectedPosition = position
            select.selectItem(position,serviceList[position].id)
            notifyDataSetChanged();
        }

        holder.mSelectType.isChecked = lastSelectedPosition==position
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
    interface Select{
        fun selectItem(position: Int,serviceId:String)
    }
}