package com.example.damanhacker.ui.numeric

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterPattern
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.NumberFindFragmentBinding
import com.example.damanhacker.intefaces.ItemOnClickListenerView
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.NumberFindPatternColor
import kotlinx.coroutines.launch
import java.util.*

class NumberFindPatternFragment : Fragment(), onResultList, ItemOnClickListenerView {

    private lateinit var binding: NumberFindFragmentBinding
    private var listData = ArrayList<DataModelMainData>()
    private lateinit var dbHandler: DBHandler
    var dataResult = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.number_find_fragment, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }
        binding.TextView.setOnClickListener {
            // setupRecyclerView(selectedNumber)
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        viewLifecycleOwner.lifecycleScope.launch {
            val pattern = arrayListOf(
                patternData("0", 0),
                patternData("1", 1),
                patternData("2", 2),
                patternData("3", 3),
                patternData("4", 4),
                patternData("5", 5),
                patternData("6", 6),
                patternData("7", 7),
                patternData("8", 8),
                patternData("9", 9)
            )

            patternRecyclerView(pattern)

            listData = dbHandler.getDataProcess(DateUtilities().getCurrentDate())
            //onPatternSelectionNumberAll()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemText(data: ArrayList<String>) {
        dataResult.addAll(data)
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterResult(dataResult, requireContext())
            addItemDecoration(itemDecoration)
        }
        binding.recyclerViewView.visibility = View.GONE
    }

    override fun onPatternSelection(pattern: Int) {
        dataResult = ArrayList<String>()
        val number = getMachNumber(pattern)
        NumberFindPatternColor().patternCheckBasedOnSerialNumber(
            listData, this@NumberFindPatternFragment, pattern, number
        )

    }

    private fun onPatternSelectionNumberAll() {
        dataResult = ArrayList<String>()
        for (i in 0..9) {
            NumberFindPatternColor().patternCheckBasedOnSerialNumber(
                listData, this@NumberFindPatternFragment, i, 0
            )
        }
    }

    private fun patternRecyclerView(data: ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPattern(
                list = data,
                context = requireContext(),
                onResultList_ = this@NumberFindPatternFragment
            )
            addItemDecoration(itemDecoration)
        }
    }

    private fun openCalender() {
        val c = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        var MONTH: String
        var DAY: String;
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
                //onPatternSelectionNumberAll()
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onItemView(id: Int) {
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getMachNumber(num: Int): Int {
        var number = 0
        when (num) {
            0 -> {
                number = 5
            }
            1 -> {
                number = 2
            }
            2 -> {
                number = 3
            }
            3 -> {
                number = 4
            }
            4 -> {
                number = 5
            }
            5 -> {
                number = 0
            }
            6 -> {
                number = 7
            }
            7 -> {
                number = 8
            }
            8 -> {
                number = 9
            }
            9 -> {
                number = 0
            }
        }

        return number
    }

/*
    private fun setupRecyclerView(number: Int) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterViewMainScreenSmall(
                dbHandler.getData(binding.TextViewDate.text.toString()), context, number
            )
            addItemDecoration(itemDecoration)
        }
    }
*/
}