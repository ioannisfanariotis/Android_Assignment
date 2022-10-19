package com.example.ioannisfanariotis.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ioannisfanariotis.databinding.RowItemBinding
import com.example.ioannisfanariotis.models.RequestItem

class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var item: MutableList<RequestItem> = mutableListOf() //instead of passing it from the activity, I create it here

    fun submitList(responseBody: List<RequestItem>){
        item.clear()
        item.addAll(responseBody) //filling up the list and use it below
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: RequestItem = item[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class ViewHolder(private val binding: RowItemBinding):RecyclerView.ViewHolder(binding.root){ //only 4 out of 9 elements I need to show
        fun bind(model: RequestItem){
            binding.date.text = "Date: ${model.date}"
            binding.localName.text = "Local Name: ${model.localName}"
            binding.name.text = "Name: ${model.name}"
            binding.type.text = "Type: ${model.type}"
        }
    }
}