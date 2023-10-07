package com.example.damanhacker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.ResultListItemPatternBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.patternData


class AdapterPatternMini(
    private val list: ArrayList<patternData>,
    private val context: Context,
    private val onResultList_: onResultList
) : RecyclerView.Adapter<AdapterPatternMini.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            ResultListItemPatternBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v, context,onResultList_)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position].name,list[position].id)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        private var listItemBinding: ResultListItemPatternBinding,
        private var context: Context,
        var onResultList_: onResultList
        ) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(data: String,id:Int) {
            listItemBinding.textResult.text = data
            listItemBinding.textResult.setOnClickListener {
                onResultList_.onPatternSelection(id)
            }

        }
    }

}