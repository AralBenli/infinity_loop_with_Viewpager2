package com.example.loopingviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loopingviewpager.databinding.FragmentSecondSliderBinding
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorSlideMode


class SecondSliderFragment : Fragment() {
    private var _binding: FragmentSecondSliderBinding? = null
    private val binding get() = _binding!!
    private lateinit var mBannerViewPager: BannerViewPager<SliderItem, SecondSliderAdapter>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondSliderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sliderItems = ArrayList<SliderItem>()
        sliderItems.add(SliderItem(R.drawable.triss_marigold))
        sliderItems.add(SliderItem(R.drawable.yennifer))
        sliderItems.add(SliderItem(R.drawable.ciri))
        sliderItems.add(SliderItem(R.drawable.triss))
        sliderItems.add(SliderItem(R.drawable.witcha))
        sliderItems.add(SliderItem(R.drawable.triss_mg))
        sliderItems.add(SliderItem(R.drawable.witcher_im_jpg))

        mBannerViewPager = view.findViewById(R.id.bannerViewPager)
        mBannerViewPager
            .setIndicatorSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorGravity(IndicatorGravity.CENTER)
            .setHolderCreator { SecondSliderAdapter(requireContext()) }
            .setAutoPlay(false)
            .setIndicatorStyle(35)
            .create(sliderItems)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}