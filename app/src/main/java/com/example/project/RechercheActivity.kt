package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRechercheBinding

class RechercheActivity : AppCompatActivity() {

    lateinit var binding : ActivityRechercheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechercheBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.buttonAdd.setOnClickListener(){
            val goToAdd : Intent = Intent(this@RechercheActivity, AddActivity :: class.java)
            startActivity(goToAdd)
        }

        val model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        var adapter = RecallRecycledAdapter()

        binding.recyclerView.hasFixedSize()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        model.flowers.value?.let {adapter.maj_flower(it)}
        model.flowers.observe(this) {
            adapter.maj_flower(it)
        }

    }
}