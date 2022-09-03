package com.example.asteroidRadarApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidRadarApp.model.AsteroidModel
import com.example.asteroidRadarApp.databinding.ListModelBinding


class AsteroidAdapter(private val itemClicked: (item: AsteroidModel) -> Unit) :
    ListAdapter<AsteroidModel, AsteroidAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListModelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    class ViewHolder(private val binding: ListModelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AsteroidModel, itemClicked: (item: AsteroidModel) -> Unit) {
            itemView.setOnClickListener { itemClicked(item) }
            binding.model = item
            binding.executePendingBindings()
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<AsteroidModel>() {
        override fun areItemsTheSame(oldItem: AsteroidModel, newItem: AsteroidModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidModel, newItem: AsteroidModel): Boolean {
            return oldItem == newItem
        }
    }


}