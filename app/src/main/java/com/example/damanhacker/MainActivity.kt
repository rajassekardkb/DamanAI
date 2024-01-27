package com.example.damanhacker

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.databinding.ActivityMainBinding
import com.example.damanhacker.model.ListDateCount
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.viewModel.DataDownloading
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHandler: DBHandler
    private val viewModel by lazy { DataDownloading(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        dbHandler = DBHandler(this)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_numberSeries,
                R.id.nav_duplicate,
                R.id.nav_clasic,
                R.id.nav_odd,
                R.id.nav_report,
                R.id.nav_single,
                R.id.nav_downloading,
                R.id.nav_numeric_color,
                R.id.nav_number_find,
                R.id.nav_number
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        getDataDownloading()
        super.onResume()
    }

    private fun getDataDownloading() {
        lifecycleScope.launch(Dispatchers.IO) {
            with(viewModel.getApiDateList(RequestGetData(CHK = "GET_DAMAN_LIST_DATA", DATE = ""))) {
                if (isSuccessful) {
                    body()?.values?.let {
                        println("getDataDownloading-->TOTAL RECORD-->" + it.size)
                        val values = ArrayList<ListDateCount>()
                        it.forEachIndexed { _, element ->
                            val cnt = dbHandler.dataDownloadingCompare(element.DM_DATE)

                            val flag = element.DM_COUNT.toInt() != cnt
                            if (flag) {
                                values.add(
                                    ListDateCount(
                                        DM_DATE = element.DM_DATE,
                                        DM_COUNT = element.DM_COUNT,
                                        DM_FLAG = flag
                                    )
                                )
                            }
                        }
                        println("getDataDownloading-->PREPARED RECORD-->" + values.size)
                        values.forEachIndexed { _, element ->
                            if (element.DM_FLAG) {
                                viewModel.getDataDownload(date = element.DM_DATE, dbHandler = dbHandler)
                            }
                            //delay(timeMillis = 5000)
                        }
                    }
                } else {
                    // Handle request error
                    // println("DataDownloading-> Request Error->" + request)
                }
            }
        }
    }
}
