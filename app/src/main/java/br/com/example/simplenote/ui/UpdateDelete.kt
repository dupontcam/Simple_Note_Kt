package br.com.example.simplenote.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.example.simplenote.R
import br.com.example.simplenote.model.NoteModel
import java.util.Calendar


class UpdateDelete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_delete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.materialToolbar3)
        setSupportActionBar(toolbar)
        val title = intent.getStringExtra("noteTitle")
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextTextUp: EditText = findViewById(br.com.example.simplenote.R.id.editTextTextUp)
        val editTextTextMultiLineUp: EditText = findViewById(br.com.example.simplenote.R.id.editTextTextMultiLineUp)

        val intent = intent
        if (intent != null) {

            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")

            // Defina os valores nos campos
            editTextTextUp.setText(noteTitle)
            editTextTextMultiLineUp.setText(noteDescription)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_update_delete, menu)

        // Obtenha os itens do menu
        val itemDelete = menu.findItem(R.id.delete)
        val itemUpdate = menu.findItem(R.id.edit)

        // Verifique o tema atual e defina a cor apropriada
        var iconColor = resources.getColor(R.color.black, theme) // Tema claro
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) === Configuration.UI_MODE_NIGHT_YES) {
            iconColor = resources.getColor(R.color.white, theme) // Tema escuro
        }

        // Aplique a cor aos ícones usando AppCompatResources para garantir compatibilidade
        val iconDelete = AppCompatResources.getDrawable(this, R.drawable.baseline_delete_24)
        val iconUpdate = AppCompatResources.getDrawable(this, R.drawable.baseline_check_24)

        if (iconDelete != null && iconUpdate != null) {
            iconDelete.setTint(iconColor)
            iconUpdate.setTint(iconColor)
            itemDelete.setIcon(iconDelete)
            itemUpdate.setIcon(iconUpdate)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val editTextTextUp: EditText = findViewById(br.com.example.simplenote.R.id.editTextTextUp)
        val editTextTextMultiLineUp: EditText = findViewById(br.com.example.simplenote.R.id.editTextTextMultiLineUp)
        val position = intent.getIntExtra("position", -1)

        val c: Calendar
        c = Calendar.getInstance()

        if (item.itemId == R.id.edit) {

            if (editTextTextUp.text.toString().isEmpty()) {
                editTextTextUp.error = "O título não pode estar vazio."
                return false
            }
            if (editTextTextMultiLineUp.text.toString().isEmpty()) {
                editTextTextMultiLineUp.error = "A descrição não pode estar vazia."
                return false
            }
            editTextTextUp.isEnabled = true
            editTextTextMultiLineUp.isEnabled = true

            AlertDialog.Builder(this)
                .setTitle("Confirmação da atualização")
                .setMessage("Tem certeza de que deseja atualizar esta anotação?")
                .setPositiveButton("Sim") { _, _ ->
                    val note = NoteModel(
                        title = editTextTextUp.text.toString(),
                        description = editTextTextMultiLineUp.text.toString(),
                        date = "${c.get(Calendar.DAY_OF_MONTH)}/${c.get(Calendar.MONTH) + 1}/${c.get(Calendar.YEAR)}",
                        time = "${pad(c.get(Calendar.HOUR_OF_DAY))}:${pad(c.get(Calendar.MINUTE))}"
                    )

                    val itemsAdapter = ItemsAdapter.ItemsAdapterSingleton.itemsAdapter // Certifique-se de que itemsAdapter seja acessível
                    itemsAdapter.updateItem(position, note)

                    Toast.makeText(this, "Anotação atualizada", Toast.LENGTH_SHORT).show()
                    goToMain()
                }
                .setNegativeButton("Não") { _, _ ->
                    goToMain()
                }.show()

        } else if (item.itemId == R.id.delete) {

            AlertDialog.Builder(this)
                .setTitle("Confirmação da Exclusão")
                .setMessage("Tem certeza de que deseja excluir esta anotação?")
                .setPositiveButton("Sim") { _, _ ->
                    val itemsAdapter = ItemsAdapter.ItemsAdapterSingleton.itemsAdapter // Certifique-se de que itemsAdapter seja acessível
                    itemsAdapter.removeItem(position)
                    goToMain()
                    Toast.makeText(this, "Anotação excluída", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Não") { _, _ ->
                    goToMain()
                }.show()

        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun pad(num: Int): String {
        if (num <= 9) {
            return "0$num"
        }
        return num.toString()
    }
}