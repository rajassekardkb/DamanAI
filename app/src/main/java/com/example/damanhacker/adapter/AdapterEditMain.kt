package com.example.damanhacker.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.damanhacker.databinding.EditListItemBinding
import com.example.damanhacker.listeners.simpleListener
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities


class AdapterEditMain(
    private val list: ArrayList<DataModelMainData>,
    private val listener: simpleListener
) : RecyclerView.Adapter<AdapterEditMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = EditListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v, CustomEditTextListener())
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
        private var listItemBinding: EditListItemBinding,
        private val customEditTextListener: CustomEditTextListener
    ) : RecyclerView.ViewHolder(listItemBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bindItem(data: DataModelMainData, position: Int, listener: simpleListener) {

            // this.myCustomEditTextListener = myCustomEditTextListener;


            listItemBinding.editText.addTextChangedListener(customEditTextListener);
            listItemBinding.textPeriod.text =
                DateUtilities().getCurrentDate() + " " + data.period.toString()
            listItemBinding.editText.hint = data.number.toString()
            //   listItemBinding.editText.setOnClickListener {
            //       println("onclick Edittext-->")
            //  }
            /*
                        listItemBinding.editText.setOnTouchListener { _, event ->
                            if (MotionEvent.ACTION_UP == event.action) {
                                listItemBinding.editText.isFocusableInTouchMode = true
                                println("fdgsjdkhfgjdklshfgjfdkslhfbg")

                            }
                            true // return is important...
                        }
            */
            customEditTextListener.assign(listener)
            customEditTextListener.updatePosition(position)
            customEditTextListener.setData(data)
            customEditTextListener.setTextext(listItemBinding.editText)
            //mEditText.setText(mDataset[position]);


        }
    }

    class CustomEditTextListener : TextWatcher {
        private var position = 0
        private lateinit var onItemClicked: simpleListener
        private lateinit var data: DataModelMainData
        private lateinit var editText: EditText
        fun updatePosition(position: Int) {
            this.position = position
        }

        fun assign(onItemClicked: simpleListener) {
            this.onItemClicked = onItemClicked
        }

        fun setData(data: DataModelMainData) {
            this.data = data
        }

        fun setTextext(editText: EditText) {
            this.editText = editText
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // println("beforeTextChanged->" + charSequence + "")

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            //mDataset.get(position) = charSequence.toString()
            if (charSequence.toString().isNotEmpty()) {
                onItemClicked.onItemClicked(data, charSequence.toString().toInt(), editText)
            }

            println("onTextChanged->" + charSequence + "")
        }

        override fun afterTextChanged(editable: Editable) {
            // println("afterTextChanged->" + editable + "")
            // no op
        }
    }


}