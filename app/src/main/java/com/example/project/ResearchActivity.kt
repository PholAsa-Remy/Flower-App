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

    private lateinit var binding : ActivityResearchBinding
    lateinit var model : FlowerViewModel

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult (
        ActivityResultContracts.StartActivityForResult())
    { //listener
        if (it.resultCode == Activity.RESULT_OK){
            model.loadAllFlower()
        }
    }

    //research edit text
    private val textWatcher = object : TextWatcher {
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

        binding.buttonAdd.setOnClickListener{
            val goToAdd = Intent(this@ResearchActivity, AddActivity :: class.java)
            launcher.launch(goToAdd)
        }

        model = ViewModelProvider(this).get(FlowerViewModel::class.java)
        model.loadAllFlower()

        val adapter = ResearchRecycledAdapter(launcher , this@ResearchActivity)

        binding.recyclerView.hasFixedSize()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.researchFlower.addTextChangedListener(textWatcher)

        model.flowers.value?.let {adapter.majFlower(it)}
        model.flowers.observe(this) {
            adapter.majFlower(it)
        }

        reload (savedInstanceState)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("research_text", binding.researchFlower.text.toString())
    }

    private fun reload (savedInstanceState: Bundle?){
        binding.researchFlower.setText(savedInstanceState?.getString("research_text") ?: "")
    }
}