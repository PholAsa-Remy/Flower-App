package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityModifyBinding
    lateinit var model :FlowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate( layoutInflater )
        setContentView( binding.root)

        val receivedIntent = intent
        //Get the flower with the primary key
        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.loadFlower(receivedIntent.getStringExtra("name")!!)

        //Fill the information
        model.flowers.observe(this) {
            Toast.makeText(this, "Add new flower", Toast.LENGTH_SHORT).show()
            if (it.size == 1){
                Toast.makeText(this, it[0].name, Toast.LENGTH_SHORT).show()
                binding.flowerName.setText(it[0].name)
                binding.edPicture.setText(it[0].picture)
                binding.edPeriod.setText(it[0].period)
                binding.edNextWatering.setText(it[0].nextWatering)
                binding.edFrequency.setText(it[0].frequency.toString())
            }
        }

    }
}