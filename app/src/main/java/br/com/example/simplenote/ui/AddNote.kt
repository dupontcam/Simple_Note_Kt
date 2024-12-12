package br.com.example.simplenote.ui

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


class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val toolbar: Toolbar

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.materialToolbar2)
        setSupportActionBar(toolbar);
        supportActionBar?.title = "New Note";
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    }

    private fun pad(num: Int): String {
        if (num <= 9) {
            return "0$num"
        }
        return num.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)

        // Obtenha os itens do menu
        val itemSave = menu.findItem(R.id.save)

        // Verifique o tema atual e defina a cor apropriada
        var iconColor = resources.getColor(R.color.black, theme) // Tema claro
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) === Configuration.UI_MODE_NIGHT_YES) {
            iconColor = resources.getColor(R.color.white, theme) // Tema escuro
        }

        // Aplique a cor aos ícones usando AppCompatResources para garantir compatibilidade
        val iconSave = AppCompatResources.getDrawable(this, R.drawable.baseline_check_24)

        if (iconSave != null) {
            iconSave.setTint(iconColor)
            itemSave.setIcon(iconSave)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val c: Calendar
        c = Calendar.getInstance()
        if (item.itemId == R.id.save) {
            val noteTitle = findViewById<EditText>(R.id.noteTitle)
            val noteDetails = findViewById<EditText>(R.id.noteDetails)

            if (noteTitle.text.isEmpty() || noteDetails.text.isEmpty()) {
                noteTitle.error = "Preencha o título"
                noteDetails.error = "Preencha a descrição"
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return true
            }

            val title = noteTitle.text.toString()
            val description = noteDetails.text.toString()

            // Crie uma instância de NoteModel com os dados do formulário
            val note = NoteModel(
                title = title,
                description = description,
                date = "${c.get(Calendar.DAY_OF_MONTH)}/${c.get(Calendar.MONTH) + 1}/${c.get(Calendar.YEAR)}", // Substitua por data real
                time = "${pad(c.get(Calendar.HOUR_OF_DAY))}:${pad(c.get(Calendar.MINUTE))}"  // Substitua por hora real
            )

            val itemsAdapter = ItemsAdapter.ItemsAdapterSingleton.itemsAdapter // Certifique-se de que itemsAdapter seja acessível
            itemsAdapter.addItem(note)

            Toast.makeText(this, "Anotação salva.", Toast.LENGTH_SHORT).show()
            goToMain()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
