package com.example.damanhacker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterViewMainScreen
import com.example.damanhacker.api.apiInterface.DamanServerRepository
import com.example.damanhacker.api.apiInterface.Repository
import com.example.damanhacker.api.retrofit.RetrofitHelper
import com.example.damanhacker.api.retrofit.RetrofitHelperDaman
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentHomeBinding
import com.example.damanhacker.model.DamanRequestGetData
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.utlities.DateUtilities
import kotlinx.coroutines.launch
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dbHandler: DBHandler
    private var values = ArrayList<DataModelMainData>()
    private var date = "";
    private var prepareList = ArrayList<DataModelMainData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        //CheckSerialNumberBasics().patternCheckBasedOnSerialNumber()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val requestGetData= DamanRequestGetData(
            pageSize = "10",
            pageNo = "1",
            typeId = "1",
            language = "0",
            random = "af39b6ef510c4656bd742758ee1b9c49",
            signature = "E3A6C6F8041EF25EF7D8C2FF26C401E6",
            timestamp = "1695143339"
        );
        val requestData= RequestGetData(
            CHK = "GET_DAMAN_LIST",
            DATE = "24-09-2023"
        );
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val quotesApi = RetrofitHelper.getInstance().create(Repository::class.java)
        dbHandler = DBHandler(context)
        //GlobalScope.launch {
        viewLifecycleOwner.lifecycleScope.launch {
            // println("rajasekar--->: " + values.size)
            try {
              val response=  quotesApi.getData(requestData)

                setupRecyclerView(response.body()?.values!!)

             }catch (e:Exception){
                e.printStackTrace()
            }

           // date = DateUtilities().getCurrentDate()
            //prepare(date)
            // values = result.body()?.values!!
            //setupRecyclerView(dbHandler.getData(date))

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

    private fun setupRecyclerView(value: ArrayList<DataModelMainData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterViewMainScreen(value, context)
            addItemDecoration(itemDecoration)
        }

        val rawListSize = dbHandler.getDataRaw(date)
        if (rawListSize.size > 0) {
            //CheckSerialNumberBasics().patternCheckBasedOnSerialNumber(rawListSize)
            //CheckNumberBasics().patternCheckBasedOnSerialNumber(rawListSize)
            //CheckViolet().patternCheckBasedOnSerialNumber(rawListSize)
            //CheckNumberBasicsMaxCount().patternCheckBasedOnSerialNumber(rawListSize)
        }
        //requireActivity().finish()
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

}