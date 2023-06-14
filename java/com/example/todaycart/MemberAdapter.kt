package com.example.todaycart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(context: Context, layout : Int,members : MutableList<MemberVO>):RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    val context = context
    val layout = layout
    val members = members
    val inflater = LayoutInflater.from(context)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val img : ImageView = view.findViewById(R.id.img)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.ad_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        holder.img.setImageResource(members[position].img)
    }

    override fun getItemCount(): Int {
        return members.size
    }


}