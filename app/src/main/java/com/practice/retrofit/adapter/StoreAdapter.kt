package com.practice.retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.retrofit.databinding.ItemStoreBinding
import com.practice.retrofit.model.Store

class StoreAdapter(private val context: Context) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private val listStore = mutableListOf<Store>()

    fun populate(listStore: List<Store>) {
        this.listStore.clear()
        this.listStore.addAll(listStore)
        notifyDataSetChanged()
    }

    inner class StoreViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Store) {
            binding.tvTitle.text = item.name
            binding.latitude.text = item.lat
            binding.longtitude.text = item.long
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(listStore[position])
    }

    override fun getItemCount(): Int = listStore.size
}