package com.example.damanhacker.intefaces

import com.example.damanhacker.model.outPutResponse


interface onResultListCustom {
    fun onItemText(data: ArrayList<outPutResponse>)
    fun onPatternSelection(pattern: Int)
}
