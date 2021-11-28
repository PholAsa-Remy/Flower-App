package com.example.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRechercheBinding

class RechercheActivity : AppCompatActivity() {

    lateinit var binding : ActivityRechercheBinding
    lateinit var model : FlowerViewModel

    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
        ActivityResultContracts.StartActivityForResult())
    { //listener
        if (it.resultCode == Activity.RESULT_OK){
            model.loadAllFlower()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechercheBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.buttonAdd.setOnClickListener(){
            val goToAdd : Intent = Intent(this@RechercheActivity, AddActivity :: class.java)
            launcher.launch(goToAdd)
        }

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.loadAllFlower()

        var adapter = RechercheRecycledAdapter(model,launcher , this@RechercheActivity)

        binding.recyclerView.hasFixedSize()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //recherche edit text
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(editable : Editable) {
                model.loadPartialFlower(editable.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        binding.rechercheFlower.addTextChangedListener(textWatcher)


        model.flowers.value?.let {adapter.maj_flower(it)}
        model.flowers.observe(this) {
            adapter.maj_flower(it)
        }

    }
}