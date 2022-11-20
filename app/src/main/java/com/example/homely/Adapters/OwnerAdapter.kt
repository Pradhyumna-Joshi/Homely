package com.example.homely.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.homely.*
import com.example.homely.Models.Facilities
import com.example.homely.databinding.ItemStayBinding
import java.util.*


class OwnerAdapter(
    val context: Context,
    val arrayList: ArrayList<Facilities>,
    private val listener: OnItemClicked
) :
    RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder>() {


    inner class OwnerViewHolder(val binding: ItemStayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerViewHolder {
        val view = ItemStayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = OwnerViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            listener.onStayClicked(viewHolder.position)
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {

        val currentStay = arrayList[position]
        holder.binding.apply {
            name.text = currentStay.name

            if (currentStay.typeRoom.equals(1)) {
                type.text = "Room"
            } else {
                type.text = "PG"
            }


//
            holder.binding.roundImage.visibility = View.VISIBLE
            holder.binding.roundImage.setImageURI(Uri.parse(currentStay.imageURL))



            address.text = currentStay.street + "," + currentStay.city + ", " + currentStay.state
            rent.text = "₹"+currentStay.rent1.toString()

        }

        holder.binding.root.setOnLongClickListener {
            listener.onDeleteClicked(position)
            true
        }


    }

    override fun getItemCount(): Int = arrayList.size

}

interface OnItemClicked {
    fun onStayClicked(position: Int)
    fun onDeleteClicked(position: Int)
}


