package com.example.damanhacker.intefaces

import com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp


interface onResultListCustoms {
    fun onItemText(data: ArrayList<CheckSerialNumberRelatedOptphp.outPutResponse>)
    fun onPatternSelection(pattern: Int)
}
