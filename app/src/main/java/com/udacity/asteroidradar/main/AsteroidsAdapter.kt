package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.network.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

// The AsteroidsAdapter class implements a [RecyclerView] [ListAdapter] which uses Data Binding
// to present [List] data, including computing diffs between lists.

class AsteroidsAdapter(val clickListener: AsteroidClickListener): ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(DiffCallback) {

    //Create AsteroidViewHolder inner class, and implement the bind() method
    // that includes a binding to Asteroid.
    class AsteroidViewHolder(private var binding: AsteroidItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(asteroid: Asteroid) {
                binding.asteroid = asteroid

                // Call binding.executePendingBindings(), which causes the update to execute immediately.
                binding.executePendingBindings()
            }
    }

    //Create the DiffCallback companion object and override its two required areItemsTheSame() methods.
    companion object DiffCallback: DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }

    //Create new [RecyclerView] item views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsAdapter.AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    //Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AsteroidsAdapter.AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        //Set up onClickListener() to pass asteroid on button click
        holder.itemView.setOnClickListener{
            clickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    //The clickListener for the Asteroid Item in the RecycleView
    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

}