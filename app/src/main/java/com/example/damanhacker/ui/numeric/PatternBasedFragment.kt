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
import com.example.damanhacker.adapter.AdapterPatternValue
import com.example.damanhacker.adapter.AdapterResult
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.PatternFragmentBinding
import com.example.damanhacker.intefaces.onPatternSelection
import com.example.damanhacker.intefaces.onResultList
import com.example.damanhacker.model.patternData
import com.example.damanhacker.utlities.MultiPatternCheck

class PatternBasedFragment : Fragment(), onResultList, onPatternSelection {

    private lateinit var binding: PatternFragmentBinding
    private lateinit var dbHandler: DBHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.pattern_fragment, container, false
        )
        binding.btnView.setOnClickListener {
            //this.onPatternSelection(binding.editPattern.text.toString().trim())
        }
        val pattern = arrayListOf(


            patternData("1101", 2),
            patternData("2202", 2),
            patternData("3303", 2),
            patternData("4404", 2),
            patternData("6656", 2),
            patternData("7757", 2),
            patternData("8858", 2),
            patternData("9959", 2),

            patternData("1100", 2),
            patternData("2200", 2),
            patternData("3300", 2),
            patternData("4400", 2),
            patternData("6655", 2),
            patternData("7755", 2),
            patternData("8855", 2),
            patternData("9955", 2),

            patternData("10101", 2),
            patternData("20202", 2),
            patternData("30303", 2),
            patternData("40404", 2),
            patternData("65656", 2),
            patternData("75757", 2),
            patternData("85858", 2),
            patternData("95959", 2),

            patternData("0000", 2),
            patternData("1111", 2),
            patternData("2222", 2),
            patternData("3333", 2),
            patternData("4444", 2),
            patternData("5555", 2),
            patternData("6666", 2),
            patternData("7777", 2),
            patternData("8888", 2),
            patternData("9999", 2),
            patternData("SSSSSSS", 1),
            patternData("BBBBBBB", 1),
            patternData("SBSBSBS", 1),
            patternData("BSSSSBBBBBB", 1),
            patternData("BSSSSSBBBBBB", 1),
            patternData("SBBBBSSSSSS", 1),
            patternData("SBBBBBSSSSSS", 1),
            patternData("SSBBBSSS", 1),
            patternData("GGGGGGG", 3),
            patternData("RRRRRRR", 3),
            patternData("RGRGRGR", 3),
            patternData("GRRGGGRRRG", 3),
            patternData("RGGGGRRRRRRR", 3),
            patternData("RGGGGGRRRRRRR", 3),
            patternData("GRRRRGGGGGGG", 3),
            patternData("GRRRRRGGGGGGG", 3),

            )
        patternRecyclerView(pattern)
        dbHandler = DBHandler(context)
        patternSelection()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemText(data: ArrayList<String>) {
        data.reverse()
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


    private fun patternRecyclerView(data: java.util.ArrayList<patternData>) {
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(context?.getDrawable(R.drawable.divider)!!)
        binding.recyclerViewPattern.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterPatternValue(
                list = data, context = requireContext(), onResultList_ = this@PatternBasedFragment
            )
            addItemDecoration(itemDecoration)
        }
    }

    override fun onPatternSelection(pattern: String, id: Int) {
        when (id) {
            1 -> {
                // MultiPatternCheck().checkValuePattern(dbHandler, pattern, this@PatternBasedFragment)
            }
            2 -> {
                //MultiPatternCheck().checkNumberPattern(dbHandler, pattern, this@PatternBasedFragment)
            }
            3 -> {
                // MultiPatternCheck().checkColorPattern(dbHandler, pattern, this@PatternBasedFragment)
            }
        }
    }

    private fun patternSelection() {
        val pattern = arrayListOf(

            patternData("1101", 2),
            patternData("2202", 2),
            patternData("3303", 2),
            patternData("4404", 2),
            patternData("6656", 2),
            patternData("7757", 2),
            patternData("8858", 2),
            patternData("9959", 2),

            patternData("1100", 2),
            patternData("2200", 2),
            patternData("3300", 2),
            patternData("4400", 2),
            patternData("6655", 2),
            patternData("7755", 2),
            patternData("8855", 2),
            patternData("9955", 2),

            patternData("10101", 2),
            patternData("20202", 2),
            patternData("30303", 2),
            patternData("40404", 2),
            patternData("65656", 2),
            patternData("75757", 2),
            patternData("85858", 2),
            patternData("95959", 2),

            patternData("1111", 2),
            patternData("2222", 2),
            patternData("3333", 2),
            patternData("4444", 2),
            patternData("6666", 2),
            patternData("7777", 2),
            patternData("8888", 2),
            patternData("9999", 2),
        )
        MultiPatternCheck().checkNumberPattern(dbHandler, this@PatternBasedFragment)
    }
}