package com.example.damanhacker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.R
import com.example.damanhacker.databinding.MainListItemBinding
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.Mapping
import com.example.damanhacker.utlities.PatternCheck


class AdapterViewMainScreen(
    public val list: ArrayList<DataModelMainData>, private val context: Context
) : RecyclerView.Adapter<AdapterViewMainScreen.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = MainListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position, list.size, list)
        holder.itemView.setOnClickListener {
            //listener(animalList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        private var listItemBinding: MainListItemBinding, private var context: Context
    ) : RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(
            data: DataModelMainData, position_: Int, size_: Int, list: ArrayList<DataModelMainData>
        ) {
            listItemBinding.textTime.text = DateUtilities().getTime(data.period)
            listItemBinding.textPeriod.text = data.period.toString()
            listItemBinding.textPeriodp.text = ((size_ - 1) - position_).toString()

            listItemBinding.textNumber.text = data.number.toString()
            val color = Mapping().getColor(data.number)

            if (chk(list, data.period)) {
                listItemBinding.constHeader.setBackgroundColor(context.resources.getColor(R.color.item_select_back))
            } else {
                listItemBinding.constHeader.setBackgroundColor(context.resources.getColor(R.color.white))
            }


            if (data.number % 2 == 0) listItemBinding.textNumber.setTextColor(
                ContextCompat.getColor(
                    context, R.color.red
                )
            )
            else listItemBinding.textNumber.setTextColor(
                ContextCompat.getColor(
                    context, R.color.green
                )
            )
/*


            val paint = listItemBinding.textNumber.paint
            val width = paint.measureText(listItemBinding.textNumber.text.toString())
            val textShader: Shader = LinearGradient(
                0f, 0f, width, listItemBinding.textNumber.textSize, intArrayOf(
                    Color.parseColor("#F97C3C"),
                    Color.parseColor("#FDB54E"),
                    */
/*Color.parseColor("#64B678"),
                    Color.parseColor("#478AEA"),*//*

                    Color.parseColor("#8446CC")
                ), null, Shader.TileMode.REPEAT
            )
*/

            //  listItemBinding.textNumber.paint.shader = textShader
            when (data.number) {
                5, 6, 7, 8, 9 -> {
                    listItemBinding.textColor.text = "Big"
                }

                0, 1, 2, 3, 4 -> {
                    listItemBinding.textColor.text = "Small"
                }
            }

            when (color) {
                "RV" -> {
                    listItemBinding.ivGreen.visibility = View.GONE
                    listItemBinding.ivRed.visibility = View.VISIBLE
                    listItemBinding.ivViolet.visibility = View.VISIBLE
                }

                "GV" -> {
                    listItemBinding.ivGreen.visibility = View.VISIBLE
                    listItemBinding.ivRed.visibility = View.GONE
                    listItemBinding.ivViolet.visibility = View.VISIBLE

                }

                "R" -> {
                    listItemBinding.ivGreen.visibility = View.GONE
                    listItemBinding.ivRed.visibility = View.VISIBLE
                    listItemBinding.ivViolet.visibility = View.GONE
                }

                "G" -> {
                    listItemBinding.ivGreen.visibility = View.VISIBLE
                    listItemBinding.ivRed.visibility = View.GONE
                    listItemBinding.ivViolet.visibility = View.GONE
                }
            }

        }

        fun chk(list: ArrayList<DataModelMainData>, period: Int): Boolean {

            var check = false

            val listAttached = PatternCheck().numberAttachedValue__(list)

            listAttached.forEachIndexed { index, data_ ->
                if (data_.period == period) {
                    check = true
                }
                //println("CHECK-->" + data_.period + ":" + period + ":" + check)
            }
            return check
        }
    }

}