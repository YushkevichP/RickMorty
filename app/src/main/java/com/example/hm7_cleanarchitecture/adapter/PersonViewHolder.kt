package com.example.hm7_cleanarchitecture.adapter

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.hm7_cleanarchitecture.databinding.ItemPersonBinding
import com.example.hm7_cleanarchitecture.domain.model.Person


class PersonViewHolder(
    private val binding: ItemPersonBinding,
    private val onUserClicked: (Person) -> Unit,

) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {

        with(binding) {

            imageView.load(person.imageUrl) {
                scale(Scale.FILL)
                size(ViewSizeResolver(root))
            }

            idPerson.text = person.id.toString()
            textNameView.text = person.name
            root.setOnClickListener {
                onUserClicked(person)
            }
        }
    }
}