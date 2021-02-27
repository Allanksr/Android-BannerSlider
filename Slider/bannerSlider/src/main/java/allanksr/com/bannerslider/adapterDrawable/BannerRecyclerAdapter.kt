package allanksr.com.bannerslider.adapterDrawable


import allanksr.com.bannerslider.R
import allanksr.com.bannerslider.databinding.RecyclerBannerAdapterBinding
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide

class BannerRecyclerAdapter
internal constructor(private val pairs: List<GetterSetterBannerAdapter>) :
    RecyclerView.Adapter<BannerRecyclerAdapter.ViewHolder>() {
    private var logTag = "logTag-BannerRecyclerAdapter"
    private lateinit var recyclerBannerAdapterBinding: RecyclerBannerAdapterBinding
    private var clickListener: ItemClickListener? = null
    private var longClickListener: ItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        recyclerBannerAdapterBinding = RecyclerBannerAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(recyclerBannerAdapterBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        recyclerBannerAdapterBinding.tvCounter.text = pairs[position].pName
        Glide.with(holder.itemView.context).load(pairs[position].pImage).into(recyclerBannerAdapterBinding.ivCounterType)
    }

    override fun getItemCount(): Int {
        return pairs.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            if (longClickListener != null) longClickListener!!.onLongClick(view, adapterPosition)
            Toast.makeText( view.context,  pairs[adapterPosition].pName, Toast.LENGTH_SHORT).show()
            Log.d(logTag, "LongClickRecyclerView ${getItem(adapterPosition)}")
            return true
        }
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(view: View) {
            if (clickListener != null) clickListener!!.onItemClick(view, adapterPosition)
            val bannerOpenUrl = pairs[adapterPosition].pUrl
            try {
                val bannerOpen = Uri.parse(bannerOpenUrl)
                view.context.startActivity(Intent(Intent.ACTION_VIEW, bannerOpen))
            } catch (e: Exception) {
                Toast.makeText( view.context,  view.context.getString(R.string.install_browser), Toast.LENGTH_SHORT).show()
            }
            Log.d(logTag, "ClickRecyclerView ${getItem(adapterPosition)}")
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
    interface ItemLongClickListener {
        fun onLongClick(view: View, position: Int)
    }

    fun getItem(id: Int): Int {
        return id
    }
}
