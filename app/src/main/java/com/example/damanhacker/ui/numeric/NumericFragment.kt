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
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.api.apiInterface.Repository
import com.example.damanhacker.api.retrofit.RetrofitHelper
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.NumericFragmentBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.ui.slideshow.NumericViewModel
import com.example.damanhacker.utlities.CheckViolet
import com.example.damanhacker.utlities.CheckVioletThreePattern
import com.example.damanhacker.utlities.DateUtilities
import com.example.damanhacker.utlities.Mapping
import com.example.damanhacker.utlities.UtlString.Companion.DATE
import kotlinx.coroutines.launch

class NumericFragment : Fragment(), onResultList {

    private lateinit var binding: NumericFragmentBinding
    private var listData = ArrayList<DataModelMainData>()
    private var position: Int = 0
    private lateinit var dbHandler: DBHandler

    // This property is only valid between onCreateView and
    // onDestroyView.

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

                CheckVioletThreePattern().patternCheckBasedOnSerialNumber(listData, this@NumericFragment)

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

    fun preparedata(data: ArrayList<DataModelMainData>): ArrayList<DataModelMainData> {
        listData = ArrayList()
        data.forEachIndexed { index, element ->
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
}

