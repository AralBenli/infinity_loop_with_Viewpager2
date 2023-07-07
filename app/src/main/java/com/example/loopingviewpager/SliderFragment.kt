package com.example.loopingviewpager


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loopingviewpager.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class SliderFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private var autoScrollDelay = 2500L
    private var autoScrollRunnable: Runnable? = null
    private var isScrollingLeft = false
    private var isScrollingRight = false
    private var isAutoScrolling = false
    private var sliderAdapter: SliderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.add(SliderItem(R.drawable.triss_marigold))
        sliderItems.add(SliderItem(R.drawable.yennifer))
        sliderItems.add(SliderItem(R.drawable.ciri))
        sliderItems.add(SliderItem(R.drawable.triss))
        sliderItems.add(SliderItem(R.drawable.witcha))
        sliderItems.add(SliderItem(R.drawable.triss_mg))
        sliderItems.add(SliderItem(R.drawable.witcher_im_jpg))

        val firstItem = sliderItems[sliderItems.size - 1]
        val lastItem = sliderItems[0]
        sliderItems.add(0, firstItem)
        sliderItems.add(lastItem)

        sliderAdapter = SliderAdapter(requireContext(), sliderItems)

        adjustSpeed()
        buttonFunctions()
        scrollListener()

    }
    private fun buttonFunctions(){
        binding.leftArrow.setOnClickListener {
            startAutoScrollLeft(autoScrollDelay)
            binding.leftArrow.setColorFilter(Color.RED)
            binding.rightArrow.setColorFilter(Color.BLACK)
        }

        binding.rightArrow.setOnClickListener {
            startAutoScrollRight(autoScrollDelay)
            binding.rightArrow.setColorFilter(Color.RED)
            binding.leftArrow.setColorFilter(Color.BLACK)
        }


        binding.stop.setOnClickListener {
            stopAutoScroll()
            binding.leftArrow.setColorFilter(Color.BLACK)
            binding.rightArrow.setColorFilter(Color.BLACK)

        }
    }
    private fun scrollListener() {
        with(binding){
            val recyclerView = binding.homeViewPager.getChildAt(0) as RecyclerView
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val itemCount = sliderAdapter!!.itemCount
            val actualItemCount = itemCount - 2 // Number of actual items , remove first and last imaginal duplicates

            homeViewPager.adapter = sliderAdapter
            //homeViewPager.setPageTransformer(null)
            binding.homeViewPager.offscreenPageLimit = actualItemCount

            Log.d("Array", "$itemCount")
            binding.homeViewPager.setCurrentItem(1, false)

            val scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
                    val lastItemVisible = layoutManager.findLastVisibleItemPosition()
                    //val position = binding.homeViewPager.currentItem
                    //Log.d("Array","firstItemVisible:$firstItemVisible")
                    //Log.d("Array","lastItemVisible:$lastItemVisible")
                    //Log.d("Array","CurrentItem:$position")

                    if (firstItemVisible == itemCount - 1 && dx > 0) {
                        //Log.d("Array","firstItemVisible:$firstItemVisible")
                        binding.homeViewPager.setCurrentItem(1, false)
                    }
                    if (lastItemVisible == 0 && dx < 0) {
                        //Log.d("Array","lastItemVisible:$lastItemVisible")
                        binding.homeViewPager.setCurrentItem(itemCount - 2, false)
                    }
                }
            }
            recyclerView.addOnScrollListener(scrollListener)


            TabLayoutMediator(homeTabLayout, homeViewPager) { _, position ->
                when (position) {
                    0 -> actualItemCount - 1 // Last actual item
                    else -> position - 1
                }
            }.attach()
            homeTabLayout.getTabAt(0)?.view?.visibility = View.GONE
            homeTabLayout.getTabAt(itemCount - 1)?.view?.visibility = View.GONE
        }

    }
    private fun adjustSpeed() {
        binding.speedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val invertedProgress = binding.speedSeekBar.max - progress
                autoScrollDelay = calculateDelayFromProgress(invertedProgress)
                if (isAutoScrolling) {
                    if (isScrollingLeft) {
                        stopAutoScroll()
                        startAutoScrollLeft(autoScrollDelay)
                    } else {
                        stopAutoScroll()
                        startAutoScrollRight(autoScrollDelay)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun calculateDelayFromProgress(progress: Int): Long {
        val minDelay = 5000L
        val maxDelay = 1000L
        val range = maxDelay - minDelay
        return maxDelay - (range * progress / binding.speedSeekBar.max)
    }
    private fun startAutoScrollRight(delay: Long) {
        stopAutoScroll()
        isAutoScrolling = true
        isScrollingRight = true
        isScrollingLeft = false
        autoScrollRunnable = Runnable {
            val currentItem = binding.homeViewPager.currentItem
            val itemCount = sliderAdapter?.itemCount ?: 0

            val nextItem = if (currentItem == itemCount - 1) 0 else currentItem + 1
            binding.homeViewPager.setCurrentItem(nextItem, true)
            autoScrollRunnable?.let { autoScrollHandler.postDelayed(it, delay) }
        }
        autoScrollHandler.postDelayed(autoScrollRunnable!!, autoScrollDelay)
    }
    private fun startAutoScrollLeft(delay: Long) {
        stopAutoScroll()
        isAutoScrolling = true
        isScrollingLeft = true
        isScrollingRight = false
        autoScrollRunnable = Runnable {
            val currentItem = binding.homeViewPager.currentItem
            val itemCount = sliderAdapter?.itemCount ?: 0

            val nextItem = if (currentItem == 0) itemCount - 1 else currentItem - 1
            binding.homeViewPager.setCurrentItem(nextItem, true)
            autoScrollRunnable?.let { autoScrollHandler.postDelayed(it, delay) }
        }
        autoScrollHandler.postDelayed(autoScrollRunnable!!, autoScrollDelay)
    }
    private fun stopAutoScroll() {
        isAutoScrolling = false
        isScrollingRight = false
        isScrollingLeft = false
        autoScrollRunnable?.let {
            autoScrollHandler.removeCallbacks(it)
            autoScrollRunnable = null
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        stopAutoScroll()
    }
}
