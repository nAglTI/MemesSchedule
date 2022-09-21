package com.nagl.memesschedule.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagl.memesschedule.data.model.UniPair
import com.nagl.memesschedule.databinding.PairItemBinding

class PairAdapter() : ListAdapter<UniPair, PairAdapter.PairViewHolder>(UniPairDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PairItemBinding.inflate(layoutInflater, parent, false)
        return PairViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PairViewHolder, position: Int) {
        holder.bind(getItem(position))
//        holder.itemView.setOnClickListener {
//            clickListener.onClick()
//        }
    }

    class PairViewHolder(private val binding: PairItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uniPair: UniPair) {
            binding.uniPair = uniPair
//            binding.weatherForecast = weatherForecast
//            val weatherDescription = weatherForecast.networkWeatherDescription.first()
//            binding.weatherForecastDescription = weatherDescription
            binding.executePendingBindings()
        }
//        companion object {
//            fun from(parent: ViewGroup): PairViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = PairItemBinding.inflate(layoutInflater, parent, false)
//                return PairViewHolder(binding)
//            }
//        }
    }

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class UniPairDiffCallBack : DiffUtil.ItemCallback<UniPair>() {
        override fun areItemsTheSame(oldItem: UniPair, newItem: UniPair): Boolean {
            return oldItem.dayNumber == newItem.dayNumber && oldItem.pairNumber == newItem.pairNumber
        }

        override fun areContentsTheSame(
            oldItem: UniPair,
            newItem: UniPair
        ): Boolean {
            return oldItem == newItem
        }
    }
}