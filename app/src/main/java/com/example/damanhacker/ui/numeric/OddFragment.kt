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
import com.example.damanhacker.adapter.AdapterViewMainScreenSmall
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.OddFragmentBinding
import com.example.damanhacker.intefaces.ItemOnClickListenerView
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.utlities.CheckSerialNumberBasics
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.SortingDate
import kotlinx.coroutines.launch
import java.util.*

class OddFragment : Fragment(), onResultList, ItemOnClickListenerView {

    private lateinit var binding: OddFragmentBinding
    private var listData = ArrayList<DataModelMainData>()
    private lateinit var dbHandler: DBHandler
    var selectedNumber = 6
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.odd_fragment, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {
            openCalender()
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

            // values = result.body()?.values!!
            //setupRecyclerView(dbHandler.getData(date))

        }
        //report()

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
        CheckSerialNumberBasics().patternCheckBasedOnSerialNumber(
            listData, this@OddFragment, pattern
        )
        selectedNumber = pattern
        setupRecyclerView(selectedNumber)
        setSelection(0)
    }

    private fun patternRecyclerView(data: ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPattern(
                list = data, context = requireContext(), onResultList_ = this@OddFragment
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
                selectedNumber = 6
                listData = dbHandler.getDataProcess(binding.TextViewDate.text.toString())
                CheckSerialNumberBasics().patternCheckBasedOnSerialNumber(
                    listData, this@OddFragment, selectedNumber
                )
                setupRecyclerView(selectedNumber)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onItemView(id: Int) {


    }

    override fun onResume() {
        CheckSerialNumberBasics().patternCheckBasedOnSerialNumber(
            listData, this@OddFragment, selectedNumber
        )

        setupRecyclerView(selectedNumber)
        super.onResume()

    }

    private fun setSelection(position: Int) {
        binding.recyclerViewView.postDelayed(Runnable {
            binding.recyclerViewView.smoothScrollToPosition(position)
        }, 1_000)
    }



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

}

