package allanksr.com.bannerslider

import allanksr.com.bannerslider.adapterDrawable.BannerRecyclerAdapter
import allanksr.com.bannerslider.adapterDrawable.GetterSetterBannerAdapter
import allanksr.com.bannerslider.databinding.BannerControlInfalterBinding
import allanksr.com.bannerslider.databinding.InjectRecycleBinding
import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout

class InjectDrawable (
    ctx: Context,
    milliSecondsPerInch: Float,
    bannerVelocity: Long
) {
    private var logTag = "logTag-ImageInflater"
    private var context = ctx
    private var bannerRecyclerAdapter: BannerRecyclerAdapter? = null
    private lateinit var getterSetterBannerAdapter: MutableList<GetterSetterBannerAdapter>
    private var velocity = bannerVelocity
    private var bannerTimer: CountDownTimer? = null
    private lateinit var bannerControlInfalterBinding: BannerControlInfalterBinding
    private lateinit var linearLayout: FlexboxLayout
    private lateinit var linearLayoutBannerControl: FlexboxLayout
    private lateinit var injectRecycleBinding: InjectRecycleBinding
    private lateinit var recyclerBanner: RecyclerView
    private lateinit var horizontalLayoutManager : LinearLayoutManager
    private var bannerRunning = false
    private lateinit var linearSmoothScroller: LinearSmoothScroller
    fun setData(
              arrayBannerName: Array<String>,
              arrayBannerImage: Array<Int>,
              arrayBannerUrl: Array<String>): RelativeLayout{
        injectRecycleBinding = InjectRecycleBinding.inflate(LayoutInflater.from(context))
        recyclerBanner = injectRecycleBinding.recyclerBanner
        horizontalLayoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerBanner.layoutManager = horizontalLayoutManager
        getterSetterBannerAdapter = ArrayList()
        for (a in arrayBannerName.indices) {
            getterSetterBannerAdapter.add(
                GetterSetterBannerAdapter(
                        arrayBannerName[a],
                        arrayBannerImage[a],
                        arrayBannerUrl[a]
                )
            )
            linearLayoutBannerControl = injectRecycleBinding.llBannerControl
            bannerControlInfalterBinding = BannerControlInfalterBinding.inflate(LayoutInflater.from(context))
            linearLayout = bannerControlInfalterBinding.root
            linearLayoutBannerControl.addView(linearLayout)
            bannerControlInfalterBinding.positionControl.text = "$a"
            linearLayoutBannerControl[0].setBackgroundResource(R.drawable.border_controller_selected)
            bannerControlInfalterBinding.positionControl.setOnClickListener{
                recyclerBanner.smoothScrollToPosition(a)
                linearLayoutBannerControl[a].setBackgroundResource(R.drawable.border_controller_pressed)
                if(bannerRunning){
                    bannerRunning = false
                    if(bannerTimer != null){
                        bannerTimer?.cancel()
                    }
                    recyclerSize = 1
                    Log.d(logTag, "InjectView bannerTimer paused")
                }else{
                    bannerTimer.also {
                        it?.start()
                        bannerRunning = true
                        Log.d(logTag, "InjectView bannerTimer started again")
                    }
                }

            }
            bannerRecyclerAdapter = BannerRecyclerAdapter(getterSetterBannerAdapter)
            recyclerBanner.adapter?.notifyDataSetChanged()
            recyclerBanner.adapter = bannerRecyclerAdapter
        }
        if(velocity in 1..9){
           val timer = velocity*1000L
            bannerTimer =  object: CountDownTimer(timer, 1000)  {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    if (context is Activity && (context as Activity).isFinishing){
                        Log.d(logTag, "InjectView context isFinishing")
                        return
                    }
                    if(recyclerSize == arrayBannerName.size) {
                        recyclerSize = 1
                        recyclerBanner.smoothScrollToPosition(0)
                        linearLayoutBannerControl[0].setBackgroundResource(R.drawable.border_controller_selected)
                        linearLayoutBannerControl[arrayBannerName.size - 1].setBackgroundResource(R.drawable.border_controller)
                        bannerTimer?.start()
                    }else{
                        for (a in arrayBannerName.indices) {
                            linearLayoutBannerControl[a].setBackgroundResource(R.drawable.border_controller)
                        }
                        linearLayoutBannerControl[recycleAuto(recyclerSize) - 1].setBackgroundResource(R.drawable.border_controller)
                        recycleAuto(recyclerSize)
                        bannerTimer?.start()
                        linearLayoutBannerControl[recycleAuto(recyclerSize)].setBackgroundResource(R.drawable.border_controller_selected)
                        recyclerSize++
                    }
                }
            }
           if(!bannerRunning){
               bannerRunning = true
               bannerTimer?.start()
               Log.d(logTag, "InjectView bannerTimer started")
           }
        }
        Log.d(logTag, "InjectView loaded")
        return injectRecycleBinding.bannerContainer
    }

   private var recyclerSize = 1
   private val secondsPerInch = milliSecondsPerInch
   private fun recycleAuto(recyclerPlus: Int): Int{
         linearSmoothScroller = object : LinearSmoothScroller(context) {
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return secondsPerInch
                    }
                }
        linearSmoothScroller.targetPosition = recyclerPlus
        horizontalLayoutManager.startSmoothScroll(linearSmoothScroller)
        return linearSmoothScroller.targetPosition
    }

}