package com.example.damanhacker.ui.numeric

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
import com.example.damanhacker.adapter.AdapterResult_custs
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.NumberReportCurrentDateBinding
import com.example.damanhacker.intefaces.ItemOnClickListenerView
import com.example.damanhacker.intefaces.onResultListCustoms
import com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp
import com.example.damanhacker.utlities.DateUtilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportForCurrentDateFragment : Fragment(), onResultListCustoms, ItemOnClickListenerView {

    private lateinit var binding: NumberReportCurrentDateBinding
    private lateinit var dbHandler: DBHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.number_report_current_date, container, false
        )
        dbHandler = DBHandler(context)
        binding.TextViewDate.setOnClickListener {}
        binding.TextViewDate.text = DateUtilities().getCurrentDate()
        report()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemText(data: ArrayList<CheckSerialNumberRelatedOptphp.outPutResponse>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterResult_custs(data, requireContext())
            addItemDecoration(itemDecoration)
        }
    }
    override fun onPatternSelection(pattern: Int) {

    }


    override fun onItemView(id: Int) {
    }

    private fun report() {
        lifecycleScope.launch(Dispatchers.Main) {
            CheckSerialNumberRelatedOptphp().init(
                dbHandler, this@ReportForCurrentDateFragment
            )
        }
    }

}

