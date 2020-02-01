package com.developer.anishakd4.unacdemyassignment

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(val imageList: ArrayList<String>): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){


    val currentOpen = mutableSetOf<Int>();

    class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view), AsyncTaskLoadImage.ImageLoadedCallback{

        val imageView = view.imageview
        lateinit var asynci: AsyncTaskLoadImage

        fun bind(url: String, current_position: Int) {
            asynci = AsyncTaskLoadImage(imageView, current_position, this)
            asynci.execute(url)
        }

        override fun imageCallback(bitmap: Bitmap?) {
            Log.i("anisham", "imageCallback ")
            if(asynci != null){
                asynci.cancel(true)
            }
            if(bitmap != null){
                imageView.setImageBitmap(bitmap)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        Log.i("anisham", "onCreateViewHolder")
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 40
    }

    fun printSet(){
        for(element in currentOpen){
            print("$element ")
        }
        println()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(imageList.get(position % (imageList.size - 1)), position)
        currentOpen.add(position)
        Log.i("anisham", "onBindViewHolder " + position)
        printSet()
    }

    override fun onViewRecycled(holder: RecyclerViewHolder) {
        super.onViewRecycled(holder)
        currentOpen.remove(holder.layoutPosition)
        Log.i("anisham", "onViewRecycled " + holder.layoutPosition + " " + holder.adapterPosition + " " + holder.oldPosition)
    }

}