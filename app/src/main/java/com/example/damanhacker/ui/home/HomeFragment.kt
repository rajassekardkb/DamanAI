package com.example.damanhacker.ui.home

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
import com.example.damanhacker.databinding.FragmentHomeBinding
import com.example.damanhacker.intefaces.onResponse
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.utlities.UtlString.Companion.DATE
import com.example.damanhacker.viewModel.MainViewModel


class HomeFragment : Fragment(), onResponse {

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
        try {
            binding.progress.visibility = View.VISIBLE
            MainViewModel(requireContext(), this@HomeFragment).getData()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupRecyclerView(value: ArrayList<DataModelMainData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterViewMainScreen(value, context)
            addItemDecoration(itemDecoration)
        }
        binding.recyclerViewFs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterViewMainScreen(value, context)
            addItemDecoration(itemDecoration)
        }

    }

    private fun prepare(date: String) {
        prepareList = ArrayList()

        val count = dbHandler.getCount(date)

        if (count == 0) {
            for (i in 1..1440) {

                val data = DataModelMainData(
                    sno = 0, period = i, number = 0, value = "", color = "", date = date, flag = 0
                )

                prepareList.add(data)
            }
            dbHandler.addNewCourse(prepareList)
        }


    }

    override fun onSuccess(list: ArrayList<DataModelMainData>) {
        binding.progress.visibility = View.GONE
        setupRecyclerView(list)

    }

    override fun Error(data: String) {
        binding.progress.visibility = View.GONE
    }


}