package com.example.damanhacker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.ResultListItemBinding

import com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp

class AdapterResult_custs(
    private val list: ArrayList<CheckSerialNumberRelatedOptphp.outPutResponse>, private val context: Context
    // private val listener: ItemOnClickListenerView
) : RecyclerView.Adapter<AdapterResult_custs.ViewHolder>() {

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
        private var listItemBinding: ResultListItemBinding, private var context: Context
    ) : RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(data: CheckSerialNumberRelatedOptphp.outPutResponse) {
            val listChild: java.util.ArrayList<CheckSerialNumberRelatedOptphp.ReportData> = data.list
            var str = ""
            val value = StringBuilder()
            var output: String

            for (l in listChild.indices) {

                val data = listChild[l]
                output =
                    data.period.toString() + ": " + data.number.toString() + " :(" + data.time + "):" + data.level + ": " + data.gap + "\n"
                value.append(output)
            }
            str = data.date + "\n" + value.toString()
            listItemBinding.textResult.text = str
        }
    }

}