package com.example.damanhacker.ui.slideshow

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterPattern
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentSlideshowBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.Mapping
import com.example.damanhacker.utlities.SerialNumberThreePattern
import kotlinx.coroutines.launch
import java.util.*

class SlideshowFragment : Fragment(), onResultList {

    private lateinit var binding: FragmentSlideshowBinding
    private var listData = ArrayList<DataModelMainData>()
    private var position: Int = 0
    private lateinit var dbHandler: DBHandler

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dbHandler = DBHandler(context)
        val slideshowViewModel = ViewModelProvider(this)[NumericViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_slideshow, container, false
        )
        // binding.TextViewDate.text = DateUtilities().getCurrentDate()
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())

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
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun preparedata(data: ArrayList<DataModelMainData>): ArrayList<DataModelMainData> {
        listData = ArrayList()
        data.forEachIndexed { _, element ->
            listData.add(
                DataModelMainData(
                    sno = element.sno,
                    period = element.period,
                    number = element.number,
                    value = Mapping().getValue(element.number),
                    color = Mapping().getColor(element.number),
                    date = element.date,
                    flag = 0
                )
            )
        }
        return listData
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
        SerialNumberThreePattern().patternCheckBasedOnSerialNumber(
            listData, this@SlideshowFragment, pattern
        )
    }

    private fun patternRecyclerView(data: ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPattern(
                list = data, context = requireContext(), onResultList_ = this@SlideshowFragment
            )
            addItemDecoration(itemDecoration)
        }
    }

    private fun openCalender() {
        // on below line we are getting
        // the instance of our calendar.
        val c = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        var year: Int = c.get(Calendar.YEAR)
        var month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        var MONTH = "";
        var DAY = "";
        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
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
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()

    }

}