package com.example.damanhacker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
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
        // getDataDownloading()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed")

                return@OnCompleteListener
            }
            // fetching the token
            val token = task.result
            println("Token saved successfully!=>$token")

        })

        val intent = Intent(this, MainActivity::class.java)
       // intent.putExtras(bundle)
        //showNotification(context = this, title = "Hello", message = "Helo All", intent =intent , reqCode = 0)
        //sendTestNotification(context =this)

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
                                viewModel.getDataDownload(
                                    date = element.DM_DATE,
                                    dbHandler = dbHandler
                                )
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


    private fun sendTestNotification(context: Context) {
        val CHANNEL_ID = "test_channel_id"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Test Channel", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Test Channel Description"
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Test Notification")
            .setContentText("This is a test notification.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
            .setAutoCancel(true)

        // Show the notification
        notificationManager.notify(1, builder.build())
    }
    private fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        intent: Intent?,
        reqCode: Int
    ) {
        val pendingIntent =
            PendingIntent.getActivity(context, reqCode, intent, FLAG_IMMUTABLE)
        val CHANNEL_ID = "channel_name" // The id of the channel.
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(
            reqCode,
            notificationBuilder.build()
        ) // 0 is the request code, it should be unique id
        Log.d("showNotification", "showNotification: $reqCode")
    }



}
