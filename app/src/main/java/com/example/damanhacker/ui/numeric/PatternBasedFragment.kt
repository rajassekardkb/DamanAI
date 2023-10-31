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
import com.example.damanhacker.utlities.PatternCheck

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
            this.onPatternSelection(binding.editPattern.text.toString().trim())
        }
        val pattern = arrayListOf(
            patternData("SSSSSSS", 1),
            patternData("BBBBBBB", 1),
            patternData("SBSBSBS", 1),
            patternData("BSSSSBBBBBB", 2),
            patternData("BSSSSSBBBBBB", 3),
            patternData("SBBBBSSSSSS", 4),
            patternData("SBBBBBSSSSSS", 5),
            patternData("SSBBBSSS", 6),
        )
        patternRecyclerView(pattern)
        dbHandler = DBHandler(context)
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

    override fun onPatternSelection(pattern: String) {
        if (binding.editPatternflag.text.toString() == "0") {
            PatternCheck().checkValuePattern(dbHandler, pattern, this@PatternBasedFragment)
        } else {
            PatternCheck().checkColorPattern(dbHandler, pattern, this@PatternBasedFragment)

        }
    }
}