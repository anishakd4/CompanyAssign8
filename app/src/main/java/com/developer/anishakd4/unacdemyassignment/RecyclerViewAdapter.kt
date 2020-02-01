package com.developer.anishakd4.unacdemyassignment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerViewAdapter(val imageList: ArrayList<String>, val activity: AppCompatActivity) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(),
    AsyncTaskLoadImage.ImageLoadedCallback, ImageLoadingLibrary.ImageLoadedCallbackLibrary {


    val currentOpen = mutableSetOf<Int>();

    var placeholderImage = BitmapFactory.decodeResource(activity.getResources(), android.R.drawable.ic_menu_upload)


    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.imageview
        val textView = view.current_index

        fun bind(url: String, current_position: Int) {
            textView.text = current_position.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        Log.i("anisham", "onCreateViewHolder")
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 40
    }

    fun printSet() {
        for (element in currentOpen) {
            print("$element ")
        }
        println()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(imageList.get(position % (imageList.size - 1)), position)
        currentOpen.add(position)
        Log.i("anisham", "onBindViewHolder " + position)
        printSet()

        //AsyncTaskLoadImage(holder.imageView, position, this, placeholderImage).execute(imageList.get(position % (imageList.size - 1)))

        ImageLoadingLibrary.with(activity).load(holder.imageView, imageList.get(position % (imageList.size - 1)), this, position)
    }

    override fun onViewRecycled(holder: RecyclerViewHolder) {
        super.onViewRecycled(holder)
        currentOpen.remove(holder.layoutPosition)
//        holder.imageView.setImageDrawable(activity.getDrawable(android.R.drawable.ic_menu_upload))
        Log.i("anisham", "onViewRecycled " + holder.layoutPosition + " " + holder.adapterPosition + " " + holder.oldPosition)
    }

    override fun imageCallback(bitmap: Bitmap?, position: Int?, imageView: ImageView?) {
        Log.i("anisham", "imageCallback " + position)
        if (position != null && bitmap != null && currentOpen.contains(position) && imageView != null) {
            imageView.setImageBitmap(bitmap)
        }
    }

    override fun imageCallbacking(bitmap: Bitmap?, position: Int?, imageView: ImageView?) {
        Log.i("anisham", "imageCallback2= " + position)
        if (position != null && bitmap != null && currentOpen.contains(position) && imageView != null) {
            imageView.setImageBitmap(bitmap)
        }
    }

}