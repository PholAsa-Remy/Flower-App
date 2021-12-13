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
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddBinding
    lateinit var flower : Flower
    lateinit var model : FlowerViewModel
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
        binding = ActivityAddBinding.inflate( layoutInflater )
        setContentView( binding.root)

        setSupportActionBar( binding.toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.insertInfo.observe(this){
            if (it == 1) {
                Toast.makeText(this, "Add new flower", Toast.LENGTH_SHORT).show()
                var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
                setResult(RESULT_OK,goToResearch)
                finish()
            }
        }

        binding.flowerPicture.setOnClickListener(){
            PhotoManager.takePhoto(launcher)
        }

        binding.bAddFlower.setOnClickListener(){
            val latinName = binding.edLatinName.text.toString()
            val name = if (binding.edName.text.toString() == "") latinName else binding.edName.text.toString()
            var frequency = ""
            val spring = binding.edSpring.text.toString()
            val summer = binding.edSummer.text.toString()
            val autumn = binding.edAutumn.text.toString()
            val winter = binding.edWinter.text.toString()
            val nutrimentFrequency = binding.edNutrimentFrequency.text.toString()
            val nextWatering = SimpleDateFormat("yyyy-MM-dd").format(Date())

            if (name == "" || spring == "" || summer == "" || autumn == "" || winter == "" || nutrimentFrequency == "" || nutrimentFrequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                var photo : String
                if (this::imageBitmap.isInitialized) {
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    PhotoManager.savePhoto (timeStamp, imageBitmap,this)
                    photo = "$timeStamp.jpg"
                }else{
                    photo = "None"
                }
                frequency = "$winter,$spring,$summer,$autumn"

                flower = Flower(name, latinName, frequency, nutrimentFrequency.toInt(),photo )
                flower.nextWatering = nextWatering
                model.insertFlower(flower)
            }
        }

        reload (savedInstanceState)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", binding.edName.text.toString())
        outState.putString("latinName", binding.edLatinName.text.toString())
        outState.putString("spring", binding.edSpring.text.toString())
        outState.putString("summer", binding.edSummer.text.toString())
        outState.putString("autumn", binding.edAutumn.text.toString())
        outState.putString("winter", binding.edWinter.text.toString())
        outState.putString("nutrimentFrequency", binding.edNutrimentFrequency.text.toString())
        if (this::imageBitmap.isInitialized) {
            PhotoManager.savePhoto ("save", imageBitmap,this)
            outState.putBoolean("photo_saved", true)
        }else{
            outState.putBoolean("photo_saved", false)
        }
    }

    fun reload (savedInstanceState: Bundle?){
        binding.edName.setText(savedInstanceState?.getString("name") ?: "")
        binding.edLatinName.setText(savedInstanceState?.getString("latinName") ?: "")
        binding.edSpring.setText(savedInstanceState?.getString("spring") ?: "")
        binding.edSummer.setText(savedInstanceState?.getString("summer") ?: "")
        binding.edAutumn.setText(savedInstanceState?.getString("autumn") ?: "")
        binding.edWinter.setText(savedInstanceState?.getString("winter") ?: "")
        binding.edNutrimentFrequency.setText(savedInstanceState?.getString("nutrimentFrequency") ?: "")

        val photo_saved = savedInstanceState?.getBoolean("photo_saved") ?: false
        if (photo_saved) {
            imageBitmap = PhotoManager.loadPhoto("save.jpg", this)
            binding.flowerPicture.setImageBitmap(imageBitmap)
        }
    }

}