package br.com.example.simplenote.model

import androidx.arch.core.internal.SafeIterableMap.SupportRemove

data class NoteModel (
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val onRemove:(NoteModel) -> Unit = {}){
}