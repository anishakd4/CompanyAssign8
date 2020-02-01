package com.developer.anishakd4.unacdemyassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.anishakd4.unacdemyassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var imageList = ArrayList<String>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-1_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-2_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-3_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-4_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-5_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-6_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-7_0.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-8_1.jpg")
        imageList.add("https://gamedata.britishcouncil.org/sites/default/files/attachment/number-9_1.jpg")

        binding.imageList.visibility = View.VISIBLE
        val adapter = RecyclerViewAdapter(imageList, this)
        binding.imageList.layoutManager = LinearLayoutManager(this)
        binding.imageList.adapter = adapter

    }
}
