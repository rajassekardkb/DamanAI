package com.example.damanhacker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.ResultListItemPatternValueBinding
import com.example.damanhacker.intefaces.onPatternSelection
import com.example.damanhacker.model.patternData


class AdapterPatternValue(
    private val list: ArrayList<patternData>,
    private val context: Context,
    private val onResultList_: onPatternSelection
) : RecyclerView.Adapter<AdapterPatternValue.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            ResultListItemPatternValueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(v, context, onResultList_)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position].name, list[position].id)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        private var listItemBinding: ResultListItemPatternValueBinding,
        private var context: Context,
        var onResultList_: onPatternSelection
    ) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(data: String, id: Int) {
            listItemBinding.textResult.text = data
            listItemBinding.textResult.setOnClickListener {
                onResultList_.onPatternSelection(data, id)
            }

        }
    }

}