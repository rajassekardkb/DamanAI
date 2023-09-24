package com.example.damanhacker.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.damanhacker.R
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentSlideshowBinding
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private var listData = ArrayList<DataModelMainData>()
    private var position: Int = 0
    private lateinit var dbHandler: DBHandler

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[NumericViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_slideshow, container, false
        )
        dbHandler = DBHandler(context)
        listData = dbHandler.getDataForUpdate(DateUtilities().getCurrentDate())
        if (listData.size < 1) {
            binding.parentLinear.visibility = View.GONE
        }
        binding.btn0.setOnClickListener { updateData(0) }
        binding.btn1.setOnClickListener { updateData(1) }
        binding.btn2.setOnClickListener { updateData(2) }
        binding.btn3.setOnClickListener { updateData(3) }
        binding.btn4.setOnClickListener { updateData(4) }
        binding.btn5.setOnClickListener { updateData(5) }
        binding.btn6.setOnClickListener { updateData(6) }
        binding.btn7.setOnClickListener { updateData(7) }
        binding.btn8.setOnClickListener { updateData(8) }
        binding.btn9.setOnClickListener { updateData(9) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun updateData(number: Int) {

        val data: DataModelMainData = listData[position]
        binding.textPeriod.text = data.period.toString()
        dbHandler.updateCourseSingle(data, number)
        position++

    }

}