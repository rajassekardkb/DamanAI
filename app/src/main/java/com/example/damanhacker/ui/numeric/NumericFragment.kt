package com.example.damanhacker.ui.numeric

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
import com.example.damanhacker.api.apiInterface.Repository
import com.example.damanhacker.api.retrofit.RetrofitHelper
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.NumericFragmentBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.model.patternData
import com.example.damanhacker.ui.slideshow.NumericViewModel
import com.example.damanhacker.utlities.*
import com.example.damanhacker.utlities.UtlString.Companion.DATE
import kotlinx.coroutines.launch

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
        // listData = dbHandler.getDataRaw(DateUtilities().getCurrentDate())

        //CheckNumberBasics().patternCheckBasedOnSerialNumber(listData, this@NumericFragment)
        viewLifecycleOwner.lifecycleScope.launch {
            // println("rajasekar--->: " + values.size)
            try {
                val pattern = arrayListOf(
                    patternData("number 6", 1),
                    patternData("number 9", 2),
                    patternData("Violet", 3),
                    patternData("5 or 0->3", 4),
                    patternData("SNo", 5),
                    patternData("Sno", 6),
                    patternData("number 6", 7),
                )

                patternRecyclerView(pattern)
                val quotesApi = RetrofitHelper.getInstance().create(Repository::class.java)
                val requestData = RequestGetData(
                    CHK = "GET_DAMAN_LIST", DATE = DATE
                );
                val response = quotesApi.getData(requestData)
                listData = preparedata(response.body()?.values!!)

                listData.forEachIndexed { index, data ->
                    // val data = listData[index]
                    if (dbHandler.getCheck(data.date, data.period.toString()) == 0) {
                        dbHandler.InsertDataMaster(data)
                    }

                }
                listData = ArrayList()

                listData = dbHandler.getDataProcess(DateUtilities().getCurrentDate())


            } catch (e: Exception) {
                e.printStackTrace()
            }

            // values = result.body()?.values!!
            //setupRecyclerView(dbHandler.getData(date))

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
        when (pattern) {

            1 -> {
                CheckNumberBasics().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment
                )
            }
            2 -> {

            }
            3 -> {
                CheckViolet().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment
                )
            }
            4 -> {
                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(
                    listData, this@NumericFragment
                )
            }
            5 -> {

            }
            6 -> {

            }
            7 -> {
            }
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
                onResultList_ = this@NumericFragment
            )
            addItemDecoration(itemDecoration)
        }
    }
}

