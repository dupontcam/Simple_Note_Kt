package br.com.example.simplenote.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.example.simplenote.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val materialToolbar = findViewById<MaterialToolbar>(R.id.materialToolbar)
        setSupportActionBar(materialToolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.listOfNotes)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        val itemsAdapter = ItemsAdapter.ItemsAdapterSingleton.itemsAdapter
        recyclerView.adapter = itemsAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAddNote)
        fab.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@MainActivity,
                    AddNote::class.java
                )
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.grid -> {
                val recyclerView = findViewById<RecyclerView>(R.id.listOfNotes)
                recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
            }
            R.id.list -> {
                val recyclerView = findViewById<RecyclerView>(R.id.listOfNotes)
                recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            }
            R.id.submenu_themes -> {
                //startActivity(Intent(this, Themes::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}