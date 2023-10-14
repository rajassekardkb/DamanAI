package com.example.damanhacker.ui.numeric

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
import com.example.damanhacker.databinding.ReportFragmentBinding
import com.example.damanhacker.intefaces.ItemOnClickListenerView
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.CheckSerialNumberBasics
import com.example.damanhacker.utlities.CheckSerialNumberBasicsReport
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.SortingDate

class ReportFragment : Fragment(), onResultList, ItemOnClickListenerView {

    private lateinit var binding: ReportFragmentBinding
    private var listData = ArrayList<DataModelMainData>()
    private lateinit var dbHandler: DBHandler
    var selectedNumber = 6
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.report_fragment, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {
        }
        binding.TextViewDate.text = DateUtilities().getCurrentDate()

        report()

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
            listData, this@ReportFragment, pattern
        )
        selectedNumber = pattern
    }

    override fun onItemView(id: Int) {


    }

    override fun onResume() {
        super.onResume()
    }

    private fun report() {
        val list = SortingDate().sort(dbHandler.dateList)

        CheckSerialNumberBasicsReport().patternCheckBasedOnSerialNumberReport(
            list, this@ReportFragment, 9, requireContext()
        )




    }

}
