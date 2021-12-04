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
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(takePictureIntent)
        }

        binding.bAddFlower.setOnClickListener(){
            val name = binding.edName.text.toString()
            val latinName = binding.edLatinName.text.toString()
            val frequency = binding.edFrequency.text.toString()
            val nutrimentFrequency = binding.edNutrimentFrequency.text.toString()

            if (name == "" || latinName == "" || frequency == "" || nutrimentFrequency == "" || nutrimentFrequency.toInt() <= 0){
                Toast.makeText(this, "Some field are missing", Toast.LENGTH_SHORT).show()
            }else{
                var photo : String
                if (this::imageBitmap.isInitialized) {
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    savePhoto (timeStamp, imageBitmap)
                    photo = "$timeStamp.jpg"
                }else{
                    photo = "None"
                }

                flower = Flower(name, latinName, frequency, nutrimentFrequency.toInt(),photo )

                if (this::imageBitmap.isInitialized) {
                    savePhoto (flower.id.toString(), imageBitmap)
                }
                model.insertFlower(flower)
            }
        }
    }

    /*
    private fun loadPhoto (filename : String) : Bitmap {
        var fileDirectory = getFilesDir()
        var f = File(fileDirectory, filename)
        var b : Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
        return b
    }
     */

    private fun savePhoto (filename : String, bmp : Bitmap) : Boolean {
        return try {
            // Need Output stream
            openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)){
                    throw IOException ("Couldn't save bitmap.")
                }
            }
            true
        }catch (e : IOException){
            false
        }
    }

}