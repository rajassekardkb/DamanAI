package com.example.damanhacker.ui.numeric

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
import com.example.damanhacker.databinding.NumericFragmentBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.ui.slideshow.NumericViewModel
import com.example.damanhacker.utlities.*
import kotlinx.coroutines.launch
import java.util.*

class NumericFragment : Fragment(), onResultList {

    private lateinit var binding: NumericFragmentBinding
    private var listData = ArrayList<DataModelMainData>()
    private var position: Int = 0
    private lateinit var dbHandler: DBHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel = ViewModelProvider(this)[NumericViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater, R.layout.numeric_fragment, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {
            openCalender()
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        viewLifecycleOwner.lifecycleScope.launch {
            val pattern = arrayListOf(
                patternData("S6", 0),
                patternData("9", 1),
                patternData("6", 2),
                patternData("VR", 3),
                patternData("VG", 4),
                patternData("0:3", 5),
                patternData("5:3", 6),
                patternData("0:3", 7),
                patternData("1:3", 8),
                patternData("2:3", 9),
                patternData("3:3", 10),
                patternData("4:3", 11),
                patternData("5:3", 12),
                patternData("6:3", 13),
                patternData("7:3", 14),
                patternData("8:3", 15),
                patternData("9:3", 16),
            )

            patternRecyclerView(pattern)

            listData = dbHandler.getDataProcess(DateUtilities().getCurrentDate())

            // values = result.body()?.values!!
            //setupRecyclerView(dbHandler.getData(date))

        }


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
        when (pattern) {
            0 -> {
                SearialNumberClasic().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 6
                )
            }
            1 -> {
                CheckNumberBasics().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 9
                )
            }
            2 -> {
                CheckNumberBasics().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 6
                )

            }
            3 -> {
                CheckViolet().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 0
                )
            }
            4 -> {
                CheckViolet().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 5
                )
            }
            5 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 0
                )
            }
            6 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 5
                )
            }
            7 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 0
                )
            }
            8 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 1
                )
            }
            9 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 2
                )
            }
            10 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 3
                )
            }
            11 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 4
                )
            }
            12 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 5
                )
            }
            13 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 6
                )
            }
            14 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 7
                )
            }
            15 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 8
                )
            }
            16 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment, 9
                )
            }
        }
    }

    private fun patternRecyclerView(data: ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPattern(
                list = data, context = requireContext(), onResultList_ = this@NumericFragment
            )
            addItemDecoration(itemDecoration)
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
                listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())
            }, year, month, day
        )
        datePickerDialog.show()
    }

}

