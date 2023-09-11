package com.example.damanhacker.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterMain
import com.example.damanhacker.databinding.FragmentHomeBinding
import com.example.damanhacker.utlities.CheckSerialNumberBasics
import com.example.damanhacker.utlities.Mapping

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        CheckSerialNumberBasics().patternCheckBasedOnSerialNumber()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        //   binding = FragmentHomeBinding.inflate(inflater, container, false)

        /* val textView: TextView = binding.textHome
         homeViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }*/

        /*   val quotesApi = RetrofitHelper.getInstance().create(test::class.java)
           GlobalScope.launch {
               val result = quotesApi.getQuotes()
               Log.d("rajasekar--->: ", result.body().toString())
           }
   */
        setupRecyclerView()
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

    private fun setupRecyclerView() {

        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(this.context?.getDrawable(R.drawable.divider)!!)



        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterMain(Mapping().getDummyData())
            addItemDecoration(itemDecoration)
        }
    }

}