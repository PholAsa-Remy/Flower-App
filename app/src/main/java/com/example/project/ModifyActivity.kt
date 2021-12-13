package com.example.project

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityModifyBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityModifyBinding
    lateinit var model :FlowerViewModel
    lateinit var flower : Flower
    lateinit var imageBitmap : Bitmap

    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
        ActivityResultContracts.StartActivityForResult())
    { //listener
        if (it.resultCode == Activity.RESULT_OK){
            imageBitmap = it.data?.extras?.get("data") as Bitmap
            binding.flowerPicture.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate( layoutInflater )
        setContentView( binding.root)

        setSupportActionBar( binding.toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val receivedIntent = intent
        //Get the flower with the primary key
        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.loadFlower(receivedIntent.getIntExtra("id", 0)!!)

        //Fill the information
        model.flowers.observe(this) {
            if (it.size == 1){
                flower = model.flowers.value?.get(0)!!
                binding.edName.setText(flower.name)
                binding.edLatinName.setText(flower.latinName)
                binding.edFrequency.setText(flower.frequency)
                binding.edNutrimentFrequency.setText(flower.nutrimentFrequency.toString())
                if (flower.picture != "None"){
                    imageBitmap = PhotoManager.loadPhoto(flower.picture, this)
                    binding.flowerPicture.setImageBitmap(imageBitmap)
                }
                reload (savedInstanceState)
            }
        }
        binding.flowerPicture.setOnClickListener(){
            PhotoManager.takePhoto(launcher)
        }

        binding.bModifyFlower.setOnClickListener(){
            val name = binding.edName.text.toString()
            val latinName = binding.edLatinName.text.toString()
            val frequency = binding.edFrequency.text.toString()
            val nutrimentFrequency = binding.edNutrimentFrequency.text.toString()

            if (name == "" || latinName == "" || frequency == "" || nutrimentFrequency == "" || nutrimentFrequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                //val flower = model.flowers.value?.get(0)!!
                flower.name = name
                flower.latinName = latinName
                flower.frequency = frequency
                flower.nutrimentFrequency = nutrimentFrequency.toInt()
                if (this::imageBitmap.isInitialized) {
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    PhotoManager.savePhoto (timeStamp, imageBitmap,this)
                    flower.picture = "$timeStamp.jpg"
                }else{
                    flower.picture = "None"
                }
                model.updateFlower (flower)

                var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
                setResult(RESULT_OK,goToResearch)
                finish()
            }
        }

        binding.bDeleteFlower.setOnClickListener(){
            //model.deleteFlower(model.flowers.value?.get(0)!!)
            model.deleteFlower(flower)
            var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
            setResult(RESULT_OK,goToResearch)
            finish()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.edName.text.toString())
        outState.putString("latinName", binding.edLatinName.text.toString())
        outState.putString("frequency", binding.edFrequency.text.toString())
        outState.putString("nutrimentFrequency", binding.edNutrimentFrequency.text.toString())
        if (this::imageBitmap.isInitialized) {
            PhotoManager.savePhoto ("save", imageBitmap,this)
            outState.putBoolean("photo_saved", true)
        }else{
            outState.putBoolean("photo_saved", false)
        }
    }

    fun reload (savedInstanceState: Bundle?){
        binding.edName.setText(savedInstanceState?.getString("name") ?: flower.name)
        binding.edLatinName.setText(savedInstanceState?.getString("latinName") ?: flower.latinName)
        binding.edFrequency.setText(savedInstanceState?.getString("frequency") ?: flower.frequency)
        binding.edNutrimentFrequency.setText(savedInstanceState?.getString("nutrimentFrequency") ?: flower.nutrimentFrequency.toString())

        val photo_saved = savedInstanceState?.getBoolean("photo_saved") ?: false
        if (photo_saved) {
            Toast.makeText(this, "picture", Toast.LENGTH_SHORT).show()
            imageBitmap = PhotoManager.loadPhoto("save.jpg", this)
            binding.flowerPicture.setImageBitmap(imageBitmap)
        }else{
            if (flower.picture != "None"){
                binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(flower.picture, this))
            }
        }
    }
}