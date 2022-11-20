package com.example.homely.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homely.Models.Facilities
import com.example.homely.databinding.FragmentEditFacilitesBinding
import com.example.homely.databinding.ItemStayBinding
import java.util.ArrayList

class TenantAdapter(val context : Context,val arrayList:ArrayList<Facilities>,val listener : itemClicked ) : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    inner class TenantViewHolder(val binding : ItemStayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {

        val view = ItemStayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = TenantViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            listener.onStayClicked(viewHolder.position)
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        val currentStay = arrayList[position]
        holder.binding.apply {
            name.text = currentStay.name

            if (currentStay.typeRoom.equals(1)) {
                type.text = "Room"
            }
            else{
                type.text = "PG"
            }
//
            holder.binding.roundImage.visibility=View.VISIBLE
            holder.binding.roundImage.setImageURI(Uri.parse(currentStay.imageURL))



            address.text = currentStay.street+","+currentStay.city+", "+currentStay.state
            rent.text= "₹"+currentStay.rent1.toString()




        }


    }

    override fun getItemCount() = arrayList.size
}

interface itemClicked{
    fun onStayClicked(position : Int)
}
