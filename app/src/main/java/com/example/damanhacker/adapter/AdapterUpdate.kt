package com.example.damanhacker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.UpdateListItemBinding
import com.example.damanhacker.listeners.simpleListener
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities


class AdapterUpdate(
    private val list: ArrayList<DataModelMainData>,
    private val listener: simpleListener
) : RecyclerView.Adapter<AdapterUpdate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = UpdateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position, listener)

        holder.itemView.setOnClickListener {
            //listener(animalList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        private var binding: UpdateListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bindItem(data: DataModelMainData, position: Int, listener: simpleListener) {
            binding.textDate.text =
                DateUtilities().getCurrentDate()
            binding.textPeriod.text = data.period.toString()
            binding.btn0.setOnClickListener {
                trigger(data, 0, listener,binding.btn0)
            }
            binding.btn1.setOnClickListener {
                trigger(data, 1, listener,binding.btn1)
            }
            binding.btn2.setOnClickListener {
                trigger(data, 2, listener,binding.btn2)
            }
            binding.btn3.setOnClickListener {
                trigger(data, 3, listener,binding.btn3)
            }
            binding.btn4.setOnClickListener {
                trigger(data, 4, listener,binding.btn4)
            }
            binding.btn5.setOnClickListener {
                trigger(data, 5, listener,binding.btn5)
            }
            binding.btn6.setOnClickListener {
                trigger(data, 6, listener,binding.btn6)
            }
            binding.btn7.setOnClickListener {
                trigger(data, 7, listener,binding.btn7)
            }
            binding.btn8.setOnClickListener {
                trigger(data, 8, listener,binding.btn8)
            }
            binding.btn9.setOnClickListener {
                trigger(data, 9, listener,binding.btn9)
            }


        }

        fun trigger(data: DataModelMainData, number: Int, listener: simpleListener,btn:AppCompatButton) {
            listener.onUpdateData(data, number,btn)
        }

    }


}