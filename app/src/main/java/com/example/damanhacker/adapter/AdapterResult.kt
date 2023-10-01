package com.example.damanhacker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.ResultListItemBinding


class AdapterResult(
    private val list: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<AdapterResult.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ResultListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        private var listItemBinding: ResultListItemBinding,
        private var context: Context
    ) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(data: String) {
            listItemBinding.textResult.text = data
        }
    }

}