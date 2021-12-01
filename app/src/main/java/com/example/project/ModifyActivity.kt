package com.example.project

import android.content.Intent
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
        model.loadFlower(receivedIntent.getIntExtra("id", 0)!!)

        //Fill the information
        model.flowers.observe(this) {
            if (it.size == 1){
                binding.edName.setText(it[0].name)
                binding.edLatinName.setText(it[0].latinName)
                binding.edFrequency.setText(it[0].frequency)
                binding.edNutrimentFrequency.setText(it[0].nutrimentFrequency.toString())
            }
        }

        binding.bModifyFlower.setOnClickListener(){
            val name = binding.edName.text.toString()
            val latinName = binding.edLatinName.text.toString()
            val frequency = binding.edFrequency.text.toString()
            val nutrimentFrequency = binding.edNutrimentFrequency.text.toString()

            if (name == "" || latinName == "" || frequency == "" || nutrimentFrequency == "" || nutrimentFrequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                val flower = model.flowers.value?.get(0)!!
                flower.name = name
                flower.latinName = latinName
                flower.frequency = frequency
                flower.nutrimentFrequency = nutrimentFrequency.toInt()
                model.updateFlower (flower)

                var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
                setResult(RESULT_OK,goToResearch)
                finish()
            }
        }

        binding.bDeleteFlower.setOnClickListener(){
            model.deleteFlower(model.flowers.value?.get(0)!!)
            var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
            setResult(RESULT_OK,goToResearch)
            finish()
        }

    }
}