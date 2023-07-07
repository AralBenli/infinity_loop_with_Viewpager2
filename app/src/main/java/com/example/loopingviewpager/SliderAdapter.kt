package com.example.loopingviewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loopingviewpager.databinding.SliderItemBinding

/**
 * Created by AralBenli on 6.07.2023.
 */
class SliderAdapter constructor(
    val context: Context,
    private val listSlider: List<SliderItem>
) : RecyclerView.Adapter<SliderAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageId: Int) {
            Glide.with(context)
                .load(imageId)
                .into(binding.sliderImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = SliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val actualPosition = position % listSlider.size
        holder.bind(listSlider[actualPosition].ImageId)
    }

    override fun getItemCount(): Int = listSlider.size

}