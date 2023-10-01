package com.example.damanhacker.ui.findDuplicateNumber

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentFindDuplicateBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.FindDuplicateNumberCount
import java.util.*

class DuplicateNumberFragment : Fragment(), onResultList {

    private lateinit var binding: FragmentFindDuplicateBinding
    private var listData = ArrayList<DataModelMainData>()
    private var position: Int = 0
    private lateinit var dbHandler: DBHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dbHandler = DBHandler(context)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_find_duplicate, container, false
        )
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())
        onPatternSelection()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemText(data: ArrayList<String>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterResult(data, requireContext())
            addItemDecoration(itemDecoration)
        }
    }

    override fun onPatternSelection(pattern: Int) {

    }

    private fun onPatternSelection() {
        FindDuplicateNumberCount().patternCheckBasedOnSerialNumber(
            listData, this@DuplicateNumberFragment
        )
    }

    private fun openCalender() {
        val c = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        var MONTH = "";
        var DAY = "";
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                MONTH = if (monthOfYear < 9) {
                    "0" + (monthOfYear + 1).toString()
                } else {
                    (monthOfYear + 1).toString()
                }
                DAY = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth.toString()
                }
                binding.TextViewDate.text = ("$DAY-$MONTH-$year").also {
                    println(it)
                }
                listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())
                onPatternSelection()
            },
            year, month, day
        )
        datePickerDialog.show()

    }

}