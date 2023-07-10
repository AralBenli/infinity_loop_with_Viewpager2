package com.example.loopingviewpager

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.zhpan.bannerview.holder.ViewHolder



/**
 * Created by AralBenli on 10.07.2023.
 */

class SecondSliderAdapter(private val context : Context) : ViewHolder<SliderItem> {

    override fun getLayoutId(): Int {
        return R.layout.slider_item
    }

    override fun onBind(itemView: View, data: SliderItem?, position: Int, size: Int) {
            Glide.with(context)
                .load(data?.ImageId)
                .into(itemView.findViewById(R.id.slider_image))
    }
}