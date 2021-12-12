package com.example.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityResearchBinding

class ResearchActivity : AppCompatActivity() {

    lateinit var binding : ActivityResearchBinding
    lateinit var model : FlowerViewModel

    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
        ActivityResultContracts.StartActivityForResult())
    { //listener
        if (it.resultCode == Activity.RESULT_OK){
            model.loadAllFlower()
        }
    }

    //research edit text
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable : Editable) {
            model.loadPartialFlower(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResearchBinding.inflate( layoutInflater )
        setContentView( binding.root)

        setSupportActionBar( binding.toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);



        binding.buttonAdd.setOnClickListener(){
            val goToAdd : Intent = Intent(this@ResearchActivity, AddActivity :: class.java)
            launcher.launch(goToAdd)
        }

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.loadAllFlower()

        var adapter = ResearchRecycledAdapter(model,launcher , this@ResearchActivity)

        binding.recyclerView.hasFixedSize()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.rechercheFlower.addTextChangedListener(textWatcher)

        model.flowers.value?.let {adapter.maj_flower(it)}
        model.flowers.observe(this) {
            adapter.maj_flower(it)
        }

        reload (savedInstanceState)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("research_text", binding.rechercheFlower.text.toString())
    }

    fun reload (savedInstanceState: Bundle?){
        binding.rechercheFlower.setText(savedInstanceState?.getString("research_text") ?: "")
    }
}