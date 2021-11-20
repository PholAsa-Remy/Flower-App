package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityAddBinding
import com.example.project.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddBinding
    lateinit var flower : Flower
    lateinit var model : FlowerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate( layoutInflater )
        setContentView( binding.root)

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.flower.observe(this){
            Toast.makeText(this, "Add new flower", Toast.LENGTH_SHORT).show()
        }


        binding.bAddFlower.setOnClickListener(){
            val name = binding.edName.text.toString()
            val picture = binding.edPicture.text.toString()
            val period = binding.edPeriod.text.toString()
            val nextWatering = binding.edNextWatering.text.toString()
            val frequency = binding.edFrequency.text.toString().toInt()

            if (name == "" || picture == "" || period == "" || nextWatering == "" || frequency <= 0){
                Toast.makeText(this, "lack of informations", Toast.LENGTH_SHORT).show()
            }else{
                flower = Flower(name, picture, period, nextWatering, frequency)
                model.dao.insertFlower(flower)
            }
        }
    }
}