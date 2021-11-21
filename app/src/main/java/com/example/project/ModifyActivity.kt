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
        model.loadFlower(receivedIntent.getStringExtra("name")!!)

        //Fill the information
        model.flowers.observe(this) {
            if (it.size == 1){
                Toast.makeText(this, it[0].name, Toast.LENGTH_SHORT).show()
                binding.flowerName.setText(it[0].name)
                binding.edPicture.setText(it[0].picture)
                binding.edPeriod.setText(it[0].period)
                binding.edNextWatering.setText(it[0].nextWatering)
                binding.edFrequency.setText(it[0].frequency.toString())
            }
        }

        binding.bModifyFlower.setOnClickListener(){
            val name = binding.flowerName.text.toString()
            val picture = binding.edPicture.text.toString()
            val period = binding.edPeriod.text.toString()
            val nextWatering = binding.edNextWatering.text.toString()
            val frequency = binding.edFrequency.text.toString()


            if (name == "" || picture == "" || period == "" || nextWatering == "" || frequency == "" || frequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                val flower = Flower(name, picture, period, nextWatering, frequency.toInt())
                model.updateFlower (flower)

                var goToRecherche : Intent = Intent (this, RechercheActivity:: class.java)
                setResult(RESULT_OK,goToRecherche)
                finish()
            }
        }

        binding.bDeleteFlower.setOnClickListener(){
            model.deleteFlower(model.flowers.value?.get(0)!!)
            var goToRecherche : Intent = Intent (this, RechercheActivity:: class.java)
            setResult(RESULT_OK,goToRecherche)
            finish()
        }

    }
}