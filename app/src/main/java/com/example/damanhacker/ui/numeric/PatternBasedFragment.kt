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
import com.example.damanhacker.databinding.PatternFragmentBinding
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.utlities.PatternCheck

class PatternBasedFragment : Fragment(), onResultList {

    private lateinit var binding: PatternFragmentBinding
    private lateinit var dbHandler: DBHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.pattern_fragment, container, false
        )
        dbHandler = DBHandler(context)
        binding.btnView.setOnClickListener {
            patternCheck(binding.editPattern.text.toString())
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

    }

    override fun onResume() {
        super.onResume()
    }

    private fun patternCheck(pattern: String) {
         PatternCheck().pickDataP(dbHandler, pattern, this@PatternBasedFragment)
       // PatternCheck().pickDataDuplicateNumber(dbHandler)
    }
}