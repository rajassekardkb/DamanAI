package com.example.damanhacker.listeners

import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.example.damanhacker.model.DataModelMainData

interface simpleListener {
    fun onItemClicked(data: DataModelMainData, number: Int, editText: EditText)
    fun onUpdateData(data: DataModelMainData, number: Int, btn: AppCompatButton)

}