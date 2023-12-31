package com.example.damanhacker.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterViewMainScreen
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentHomeBinding
import com.example.damanhacker.intefaces.onResponse
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.*
import com.example.damanhacker.viewModel.MainViewModel
import java.util.*


class HomeFragment : Fragment(), onResponse, onResultList {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dbHandler: DBHandler
    private var values = ArrayList<DataModelMainData>()
    private var date = "";
    private var prepareList = ArrayList<DataModelMainData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()

        getData()
        /*  CheckNumberBasicsServer().patternCheckBasedOnSerialNumber(
              dbHandler.getDataProcess(DateUtilities().getCurrentDate())
          )*/
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
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupRecyclerView(value: ArrayList<DataModelMainData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterViewMainScreen(value, context)
            addItemDecoration(itemDecoration)
        }
        Toast.makeText(
            requireContext(),
            "Max of level of same Number->" + PatternCheck().numberAttachedValue_(value).size,
            Toast.LENGTH_LONG
        ).show()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onSuccess(list: ArrayList<DataModelMainData>) {
        binding.progress.visibility = View.GONE
        setupRecyclerView(list)

        var listInt = ArrayList<Int>()
        list.forEachIndexed { index, dataModelMainData ->
            listInt.add(dataModelMainData.number)
        }
        NumberGap().main(listInt)


    }


    override fun Error(data: String) {
        binding.progress.visibility = View.GONE
    }

    private fun getData() {
        try {
            binding.progress.visibility = View.VISIBLE
            MainViewModel(
                requireContext(), this@HomeFragment
            ).getData(binding.TextViewDate.text.toString())
            // val dateList = SortingDate().sort(dbHandler.dateList)
            // for (i in 0 until dateList.size) {
            //     println("Report Date-->" + dateList[i])
            /*       val listData = dbHandler.getDataProcess(DateUtilities().getCurrentDate())


                   // val listData = dbHandler.getDataProcess("02-11-2023")
                   val list_ = PatternCheck().numberAttachedValue(listData)
                   CheckSerialNumberRelated().patternCheckBasedOnSerialNumber(
                       list_, listData, this@HomeFragment
                   )*/
            // }
            //val list__ = PatternCheck().numberAttachedValue(listData)

            // PatternCheck().findContinuousOddEvenSequences(listData)

            /*    CheckWithMinNumberWithRelatedNumber().patternCheckBasedOnSerialNumber(
                    PatternCheck().findMinNumber(), listData, this@HomeFragment
                )
    */
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
                getData()
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onItemText(data: ArrayList<String>) {

    }

    override fun onPatternSelection(pattern: Int) {

    }

}