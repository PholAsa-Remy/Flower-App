package com.example.project

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityAddBinding
import com.example.project.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
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
            savePhoto ("a", imageBitmap)
            binding.imageView.setImageBitmap(loadPhoto("a.jpg"))
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

    private fun loadPhoto (filename : String) : Bitmap {
        var file = getFilesDir()
        var f = File(file, filename)
        var b : Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
        return b
    }
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