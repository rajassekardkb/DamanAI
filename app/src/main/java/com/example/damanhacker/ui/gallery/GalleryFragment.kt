package com.example.damanhacker.ui.gallery


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterPattern
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentGalleryBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.ui.slideshow.NumericViewModel
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.Mapping
import com.example.damanhacker.utlities.SerialNumberThreeColorPattern
import java.util.*

class GalleryFragment : Fragment(), onResultList {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var dbHandler: DBHandler
    private var valuesList = ArrayList<DataModelMainData>()
    private var prepareList = ArrayList<DataModelMainData>()
    private var selectedDate = ""
    private var listData = ArrayList<DataModelMainData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_gallery, container, false
        )
        dbHandler = DBHandler(context)

        val slideshowViewModel = ViewModelProvider(this)[NumericViewModel::class.java]
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }

        dbHandler = DBHandler(context)
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())

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

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding = null
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
        SerialNumberThreeColorPattern().patternCheckBasedOnSerialNumber(
            listData, this@GalleryFragment, pattern
        )
    }

    private fun patternRecyclerView(data: ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPattern(
                list = data, context = requireContext(), onResultList_ = this@GalleryFragment
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