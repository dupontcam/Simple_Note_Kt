package br.com.example.simplenote.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.simplenote.R
import br.com.example.simplenote.model.NoteModel

class ItemsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<NoteModel>()

    fun addItem(newItem: NoteModel){
        items.add(newItem)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, updatedItem: NoteModel) {
        items[position] = updatedItem
        notifyItemChanged(position)
    }

    class ListViewHolder(view: View, private val items: List<NoteModel>): RecyclerView.ViewHolder(view){

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = itemView.context
                    val intent = Intent(context, UpdateDelete::class.java)
                    intent.putExtra("position", position)
                    // Passe outros dados necessários, como título e descrição
                    intent.putExtra("noteTitle", items[position].title)
                    intent.putExtra("noteDescription", items[position].description)
                    context.startActivity(intent)
                }
            }
        }

        fun bind(item: NoteModel){
            itemView.findViewById<TextView>(R.id.noteTitle).text = item.title
            itemView.findViewById<TextView>(R.id.nDate).text = item.date
            itemView.findViewById<TextView>(R.id.nTime).text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_layout, parent, false)
        return ListViewHolder(view, items)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as ListViewHolder).bind(item)
    }

    override fun getItemCount(): Int = items.size

    object ItemsAdapterSingleton {
        val itemsAdapter = ItemsAdapter()
    }
}