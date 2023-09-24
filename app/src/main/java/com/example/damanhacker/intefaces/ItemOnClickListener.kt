package com.example.damanhacker.intefaces

import android.widget.TextView

interface ItemOnClickListener {
    fun onItemClicked()
    fun onItemText(data: TextView, id: String)
}
