package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityRecallBinding
import java.text.SimpleDateFormat
import java.util.*

class RecallActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecallBinding
    private lateinit var model : FlowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate( layoutInflater )
        setContentView( binding.root)

        setSupportActionBar( binding.toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)

        model.loadNextWateringFlower(SimpleDateFormat("yyyy-MM-dd",Locale.FRANCE).format(Date()))

        val adapter = RecallRecycledAdapter(model, this@RecallActivity)
        binding.recycler.hasFixedSize()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        model.flowers.value?.let {adapter.majFlower(it)}
        model.flowers.observe(this) {
            adapter.majFlower(it)
        }
    }
}