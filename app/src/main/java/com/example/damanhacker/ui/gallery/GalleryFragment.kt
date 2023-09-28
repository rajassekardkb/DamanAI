package com.example.damanhacker.ui.gallery


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.damanhacker.R
import com.example.damanhacker.adapter.AdapterUpdate
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.FragmentGalleryBinding
import com.example.damanhacker.listeners.simpleListener
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.utlities.DateUtilities
import kotlinx.coroutines.launch
import java.util.Calendar

class GalleryFragment : Fragment(), simpleListener {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var dbHandler: DBHandler
    private var valuesList = ArrayList<DataModelMainData>()
    private var prepareList = ArrayList<DataModelMainData>()
    private var selectedDate = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_gallery, container, false
        )
        dbHandler = DBHandler(context)

        val homeViewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            valuesList = dbHandler.getData(DateUtilities().getCurrentDate())
            setupRecyclerView(valuesList)
        }
        binding.buttonSubmit.setOnClickListener {

           // dbHandler.updateCourse(valuesList, DateUtilities().getCurrentDate())
            //openCalender()

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
            adapter = AdapterUpdate(value, this@GalleryFragment)
            addItemDecoration(itemDecoration)
        }
    }

    private fun openCalender() {
        // on below line we are getting
        // the instance of our calendar.
        val c = Calendar.getInstance()

        // on below line we are getting
        // our day, month and year.
        var year: Int = c.get(Calendar.YEAR)
        var month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        var MONTH = "";
        var DAY = "";
        // on below line we are creating a
        // variable for date picker dialog.
        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                MONTH = if (monthOfYear < 10) {
                    "0" + (monthOfYear + 1).toString()
                } else {
                    (monthOfYear + 1).toString()
                }
                DAY = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth.toString()
                }
                selectedDate = ("$DAY-$MONTH-$year").also {
                    println(it)
                }
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()

    }

    fun onItemClicked(data: String, number: Int, editText: EditText) {
        editText.isFocusableInTouchMode = true
        println("Hello Bro--->" + data)
    }

    override fun onItemClicked(data: DataModelMainData, number: Int, editText: EditText) {
        println("Hello Bro--->" + data.period + ":" + number)
        //dbHandler.updateCourseSingle(data,number)

    }

    override fun onUpdateData(data: DataModelMainData, number: Int, btn: AppCompatButton) {
        dbHandler.updateCourseSingle(data, number)
        btn.setBackgroundResource(R.drawable.round_btn_selected)
    }
}