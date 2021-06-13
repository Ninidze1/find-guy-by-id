package com.example.shualeduri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shualeduri.databinding.ItemLayoutBinding
import com.example.shualeduri.model.IpModel

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    val items = mutableListOf<IpModel>()


    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IpModel) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun addItems(personInfo: MutableList<IpModel>) {
        this.items.clear()
        this.items.addAll(personInfo)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

}