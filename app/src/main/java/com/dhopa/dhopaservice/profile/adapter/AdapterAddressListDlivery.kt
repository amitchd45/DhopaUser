package com.dhopa.dhopaservice.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.home.ModelClass.Services
import com.dhopa.dhopaservice.profile.models.AddressDetail

class AdapterAddressListDlivery(private val context: Context, private val addressList: MutableList<AddressDetail>, val select:Select) : RecyclerView.Adapter<AdapterAddressListDlivery.MyTopServices>() {

    private  var lastSelectedPosition:Int=-1

    class MyTopServices(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mType = itemView.findViewById(R.id.tv_type) as TextView
        val mDelete  = itemView.findViewById(R.id.iv_delete) as ImageView
        val iv_view_address  = itemView.findViewById(R.id.iv_view_address) as ImageView
        val mImage  = itemView.findViewById(R.id.iv_image) as ImageView
        val mSelectType  = itemView.findViewById(R.id.rb_selectType) as RadioButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTopServices {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_address_item1, parent, false)
        return MyTopServices(v)
    }

    override fun onBindViewHolder(holder: MyTopServices, position: Int) {

        holder.mType.text=addressList[position].type
        if (addressList[position].equals("Home")){
            holder.mImage.setImageResource(R.drawable.ic_home_1)
        }
        if (addressList[position].equals("Hotel")){
            holder.mImage.setImageResource(R.drawable.ic_office_building)
        }
        if (addressList[position].equals("Office")){
            holder.mImage.setImageResource(R.drawable.ic_office_building)
        }

        holder.mDelete.setOnClickListener {
            select.deleteAddress(position,addressList[position].id)
        }
        holder.mSelectType.setOnClickListener {
            lastSelectedPosition = position
            select.selectDeliverAddress(addressList[position].id)
            notifyDataSetChanged();
        }

        holder.iv_view_address.setOnClickListener {
            select.viewAddress(addressList[position])
        }
        holder.mSelectType.isChecked = lastSelectedPosition==position
    }
    override fun getItemCount(): Int {
        return addressList.size
    }
    interface Select{
        fun deleteAddress(position: Int,addressId:String)
        fun viewAddress(addressDetails:AddressDetail)
        fun selectDeliverAddress(addressId:String)
    }
}