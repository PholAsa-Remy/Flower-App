package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRecallBinding

class RecallActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.recycler.hasFixedSize()

        val model = ViewModelProvider(this).get(FlowerViewModel::class.java)

        var adapter = RecallRecycledAdapter()
        binding.recycler.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        model.flowers.value?.let {adapter.maj_flower(it)}
        model.flowers.observe(this) {
            adapter.maj_flower(it)
        }
    }
}