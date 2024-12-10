package br.com.example.simplenote.ui

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

    class ListViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(item: NoteModel){
            itemView.findViewById<TextView>(R.id.noteTitle).text = item.title
            itemView.findViewById<TextView>(R.id.nDate).text = item.date
            itemView.findViewById<TextView>(R.id.nTime).text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_layout, parent, false)
        return ListViewHolder(view)
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