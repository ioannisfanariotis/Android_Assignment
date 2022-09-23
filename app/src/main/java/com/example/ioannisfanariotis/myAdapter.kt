package com.example.ioannisfanariotis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ioannisfanariotis.databinding.RowItemBinding

class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var item: MutableList<RequestItem> = mutableListOf() //instead of passing it from the activity, I create it here

    fun submitList(responseBody: List<RequestItem>){
        item.clear()
        item.addAll(responseBody) //filling up the list and use it below
        notifyDataSetChanged()
    }

    class ViewHolder(binding: RowItemBinding):RecyclerView.ViewHolder(binding.root){ //only 4 out of 9 elements I need to show
        val date = binding.date
        val localName = binding.localName
        val name = binding.name
        val type = binding.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: RequestItem = item[position]
        holder.date.text = "Date: " + model.date
        holder.localName.text = "Local Name: " + model.localName
        holder.name.text = "Name: " + model.name
        holder.type.text = "Type: " + model.type
    }

    override fun getItemCount(): Int {
        return item.size
    }
}