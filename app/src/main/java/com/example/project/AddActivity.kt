package com.example.project

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityAddBinding
import com.example.project.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddBinding
    lateinit var flower : Flower
    lateinit var model : FlowerViewModel

    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
        ActivityResultContracts.StartActivityForResult())
    { //listener
        if (it.resultCode == Activity.RESULT_OK){
            val imageBitmap = it.data?.extras?.get("data") as Bitmap
            binding.flowerPicture.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate( layoutInflater )
        setContentView( binding.root)

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.insertInfo.observe(this){
            if (it == 1) {
                Toast.makeText(this, "Add new flower", Toast.LENGTH_SHORT).show()
                var goToRecherche : Intent = Intent (this, RechercheActivity:: class.java)
                setResult(RESULT_OK,goToRecherche)
                finish()
            }
        }

        binding.flowerPicture.setOnClickListener(){
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(takePictureIntent)

        }
        binding.bAddFlower.setOnClickListener(){
            val name = binding.edName.text.toString()
            val picture = binding.edPicture.text.toString()
            val period = binding.edPeriod.text.toString()
            val nextWatering = binding.edNextWatering.text.toString()
            val frequency = binding.edFrequency.text.toString()



            if (name == "" || picture == "" || period == "" || nextWatering == "" || frequency == "" || frequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                flower = Flower(name, picture, period, nextWatering, frequency.toInt())
                model.insertFlower(flower)
            }
        }
    }
}