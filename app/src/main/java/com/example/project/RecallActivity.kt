package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRecallBinding
import java.text.SimpleDateFormat
import java.util.*

class RecallActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecallBinding
    lateinit var model : FlowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate( layoutInflater )
        setContentView( binding.root)

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)

        model.loadNextWateringFlower(SimpleDateFormat("yyyy-MM-dd").format(Date()))

        var adapter = RecallRecycledAdapter(model, this@RecallActivity)
        binding.recycler.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        model.flowers.value?.let {adapter.maj_flower(it)}
        model.flowers.observe(this) {
            adapter.maj_flower(it)
        }
    }
}