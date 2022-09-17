package com.example.hm7_cleanarchitecture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hm7_cleanarchitecture.adapter.LoadingViewHolder
import com.example.hm7_cleanarchitecture.adapter.PersonViewHolder
import com.example.hm7_cleanarchitecture.databinding.ItemLoadingBinding
import com.example.hm7_cleanarchitecture.databinding.ItemPersonBinding
import com.example.hm7_cleanarchitecture.domain.model.ItemType
import com.example.hm7_cleanarchitecture.domain.model.Person



class ItemAdapter(
    context: Context,
    private val onUserClicked: (Person) -> Unit,
) : ListAdapter<ItemType<Person>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemType.Content -> TYPE_CONTENT
            is ItemType.Loading -> TYPE_LOADING
            is ItemType.Error -> TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CONTENT -> {
                PersonViewHolder(
                    binding = ItemPersonBinding.inflate(layoutInflater, parent, false),
                    onUserClicked = onUserClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            // можно сделать кастомную вьюху для ошибки и показывать ее
            TYPE_ERROR -> {
                error("SMTH WENT WRONG")}

            else -> error("Incorrect ViewType = $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        when (getItemViewType(position)) {
            TYPE_CONTENT -> {

                val personVH = holder as? PersonViewHolder ?: return

                val item = getItem(position) as? ItemType.Content ?: return

                personVH.bind(item.data)
            }
        }
    }

    companion object {

        private const val TYPE_CONTENT = 0
        private const val TYPE_LOADING = 1
        private const val TYPE_ERROR = 3

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemType<Person>>() {
            override fun areItemsTheSame(
                oldItem: ItemType<Person>,
                newItem: ItemType<Person>,
            ): Boolean {

                val oldPersonItem = oldItem as? ItemType.Content ?: return false
                val newPersonItem = newItem as? ItemType.Content ?: return false

                return oldPersonItem.data.id == newPersonItem.data.id
            }

            override fun areContentsTheSame(
                oldItem: ItemType<Person>,
                newItem: ItemType<Person>,
            ): Boolean {

                val oldPersonItem = oldItem as? ItemType.Content ?: return false
                val newPersonItem = newItem as? ItemType.Content ?: return false

                return (oldPersonItem.data.imageUrl == newPersonItem.data.imageUrl
                        && oldPersonItem.data.imageUrl == newPersonItem.data.imageUrl)
            }
        }
    }
}