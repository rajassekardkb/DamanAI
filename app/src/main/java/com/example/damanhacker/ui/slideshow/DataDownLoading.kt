package com.example.damanhacker.ui.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterViewMainScreen
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentDataDownlodingBinding
import com.example.damanhacker.intefaces.onResponse
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.utlities.*
import com.example.damanhacker.viewModel.MainViewModel
import java.util.*


class DataDownLoading : Fragment(), onResponse, onResultList {

    private lateinit var binding: FragmentDataDownlodingBinding
    private lateinit var dbHandler: DBHandler
    private var values = ArrayList<DataModelMainData>()
    private var date = "";
    private var prepareList = ArrayList<DataModelMainData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_data_downloding, container, false
        )
        dbHandler = DBHandler(context)

        getData()
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
    }

    override fun onSuccess(list: ArrayList<DataModelMainData>) {

        //binding.progress.visibility = View.GONE
        setupRecyclerView(list)

    }


    override fun Error(data: String) {
        //binding.progress.visibility = View.GONE
    }

    private fun getData() {
        try {
            val requesList = ArrayList<RequestGetData>()
            val dateList = SortingDate().sort(PatternCheck().last15Days)
            dateList.forEachIndexed { _, element ->
                requesList.add(
                    RequestGetData(
                        CHK = "GET_DAMAN_LIST", DATE = element
                    )
                )
            }
            MainViewModel(
                requireContext(), this
            ).getDataDownloading(dbHandler, requesList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemText(data: ArrayList<String>) {


    }

    override fun onPatternSelection(pattern: Int) {


    }

}