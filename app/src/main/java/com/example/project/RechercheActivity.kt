package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRechercheBinding

class RechercheActivity : AppCompatActivity() {

    lateinit var binding : ActivityRechercheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechercheBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.buttonAdd.setOnClickListener(){
            val goToAdd : Intent = Intent(this@RechercheActivity, AddActivity :: class.java)
            startActivity(goToAdd)
        }

        //TODO : Changer ici avec la liste des plantes a arroser
        var list : MutableList<String> = mutableListOf<String>()
        list.add("Rose")
        list.add("Tullippe")
        list.add("Cabracan")
        //Fin Du changement
        var adapter = RechercheRecycledAdapter(list)
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}