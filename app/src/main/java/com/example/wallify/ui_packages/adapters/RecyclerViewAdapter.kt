package com.example.wallify.ui_packages.adapters

//import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.wallify.R
import com.example.wallify.databinding.ItemRecyclerviewBinding
import com.example.wallify.ui_packages.model.Data
import com.example.wallify.ui_packages.utils.BlurHashDecoder
import com.example.wallify.ui_packages.utils.WallInterfaceListener

class RecyclerViewAdapter(private val listener: WallInterfaceListener):
    PagingDataAdapter<Data, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview,
                parent,
                false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.blurHash == newItem.blurHash
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemRecyclerviewBinding.bind(view)
        fun bind(data: Data){

            val blurHashAsDrawable = BlurHashDecoder.blurHashBitmap(itemView.resources,data)

            Glide.with(itemView.context)
                .asBitmap()
                .load(data.smallImageUrl)
                .centerCrop()
                .transition(BitmapTransitionOptions.withCrossFade(80))
                .error(blurHashAsDrawable)
                .placeholder(blurHashAsDrawable)
                .into(binding.imageView)

            itemView.setOnClickListener{listener.onClickItem(data,it)
            }
        }
    }
}