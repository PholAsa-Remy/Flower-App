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
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityModifyBinding
    private lateinit var model :FlowerViewModel
    private lateinit var flower : Flower
    private lateinit var imageBitmap : Bitmap

    // Return for taking picture
    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
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
        model.loadFlower(receivedIntent.getIntExtra("id", 0))

        //Fill the information with the selected flower
        model.flowers.observe(this) {
            if (it.size == 1){
                flower = model.flowers.value?.get(0)!!
                val season : List<String> = flower.frequency.split(",")
                binding.edName.setText(flower.name)
                binding.edLatinName.setText(flower.latinName)
                binding.edSpring.setText(season[1])
                binding.edSummer.setText(season[2])
                binding.edAutumn.setText(season[3])
                binding.edWinter.setText(season[0])

                binding.edNutrimentFrequency.setText(flower.nutrimentFrequency.toString())
                if (flower.picture != "None"){
                    imageBitmap = PhotoManager.loadPhoto(flower.picture, this)
                    binding.flowerPicture.setImageBitmap(imageBitmap)
                }
                reload (savedInstanceState)
            }
        }
        // Take Picture
        binding.flowerPicture.setOnClickListener{
            PhotoManager.takePhoto(launcher)
        }
        // Modify Flower
        binding.bModifyFlower.setOnClickListener{
            modifyButtonAction ()
        }
        //Delete Flower
        binding.bDeleteFlower.setOnClickListener{
            model.deleteFlower(flower)
            val goToResearch = Intent (this, ResearchActivity:: class.java)
            setResult(RESULT_OK,goToResearch)
            finish()
        }
    }

    private fun modifyButtonAction (){
        val latinName = binding.edLatinName.text.toString()
        val name = if (binding.edName.text.toString() == "") latinName else binding.edName.text.toString()
        val spring = binding.edSpring.text.toString()
        val summer = binding.edSummer.text.toString()
        val autumn = binding.edAutumn.text.toString()
        val winter = binding.edWinter.text.toString()
        val nutrimentFrequency = binding.edNutrimentFrequency.text.toString()

        if (name == "" || spring == "" || summer == "" || autumn == "" || winter == "" || nutrimentFrequency == "" || nutrimentFrequency.toInt() < 0){
            Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
        }else{
            //val flower = model.flowers.value?.get(0)!!
            flower.name = name
            flower.latinName = latinName
            flower.frequency = "$winter,$spring,$summer,$autumn"
            flower.nutrimentFrequency = nutrimentFrequency.toInt()
            if (this::imageBitmap.isInitialized) {
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.FRANCE).format(Date())
                PhotoManager.savePhoto (timeStamp, imageBitmap,this)
                flower.picture = "$timeStamp.jpg"
            }else{
                flower.picture = "None"
            }
            model.updateFlower (flower)

            val goToResearch = Intent (this, ResearchActivity:: class.java)
            setResult(RESULT_OK,goToResearch)
            finish()
        }
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

    private fun reload (savedInstanceState: Bundle?){
        val season : List <String> = flower.frequency.split(",")
        binding.edName.setText(savedInstanceState?.getString("name") ?: flower.name)
        binding.edLatinName.setText(savedInstanceState?.getString("latinName") ?: flower.latinName)
        binding.edSpring.setText(savedInstanceState?.getString("spring") ?: season[1])
        binding.edSummer.setText(savedInstanceState?.getString("summer") ?: season[2])
        binding.edAutumn.setText(savedInstanceState?.getString("autumn") ?: season[3])
        binding.edWinter.setText(savedInstanceState?.getString("winter") ?: season[0])
        binding.edNutrimentFrequency.setText(savedInstanceState?.getString("nutrimentFrequency") ?: flower.nutrimentFrequency.toString())

        val photoSaved = savedInstanceState?.getBoolean("photo_saved") ?: false
        if (photoSaved) {
            imageBitmap = PhotoManager.loadPhoto("save.jpg", this)
            binding.flowerPicture.setImageBitmap(imageBitmap)
        }else{
            if (flower.picture != "None"){
                binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(flower.picture, this))
            }
        }
    }
}