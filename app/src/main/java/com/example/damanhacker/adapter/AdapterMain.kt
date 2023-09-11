package com.example.damanhacker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.MainListItemBinding
import com.example.damanhacker.model.DataModelMain

class AdapterMain(
    private val animalList: ArrayList<DataModelMain>
) :
    RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = MainListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(animalList[position])
        holder.itemView.setOnClickListener {
            //listener(animalList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    class ViewHolder(var listItemBinding: MainListItemBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(data: DataModelMain) {
            listItemBinding.textPeriod.text= data.period.toString()
            listItemBinding.textNumber.text = data.number.toString()
            listItemBinding.textColor.text = data.color

        }
    }
}