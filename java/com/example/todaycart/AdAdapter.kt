package com.example.todaycart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdAdapter(context: Context, layout : Int, ads : MutableList<AdVO>): RecyclerView.Adapter<AdAdapter.ViewHolder>() {

    val context = context
    val layout = layout
    val ads = ads
    val inflater = LayoutInflater.from(context)


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val ad : ImageView = view.findViewById(R.id.ad)
        val pnName : TextView = view.findViewById(R.id.pnName)
        val pnCost : TextView = view.findViewById(R.id.pnCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.ad_list2,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ad.setImageResource(ads[position].img3)
        holder.pnName.text = ads[position].name
        holder.pnCost.text = ads[position].cost
    }

    override fun getItemCount(): Int {
        return ads.size
    }

}