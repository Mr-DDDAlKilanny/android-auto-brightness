package com.kilanny.autobrightness

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import com.kilanny.autobrightness.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(this)) {
            AlertDialog.Builder(this)
                .setTitle("Required Permission")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Please give the app the permission in order to automatically change screen settings")
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, which ->
                    startActivity(
                        Intent(
                            Settings.ACTION_MANAGE_WRITE_SETTINGS,
                            Uri.parse("package:" + packageName)
                        )
                    )
                }
                .show()
        }

        //refreshStatus()

        binding.fab.setOnClickListener { view ->
            refreshBrightness(this)
//            if (!Settings.System.canWrite(this)) {
//                startActivity(
//                    Intent(
//                        Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                        Uri.parse("package:" + packageName)
//                    )
//                )
//            } else {
//                Settings.System.putInt(
//                    contentResolver,
//                    Settings.System.SCREEN_BRIGHTNESS_MODE,
//                    if (Settings.System.getInt(
//                            contentResolver,
//                            Settings.System.SCREEN_BRIGHTNESS_MODE
//                        ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
//                    )
//                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL else Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
//                )
//                Snackbar.make(view, "Toggle Done", Snackbar.LENGTH_LONG).show()
//                refreshStatus()
//            }
        }
    }

//    fun refreshStatus() {
//        val imgStatus = findViewById<ImageView>(R.id.imgStatus)
//        try {
//            imgStatus.setImageResource(
//                if (Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
//                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
//                ) android.R.drawable.presence_online else android.R.drawable.presence_busy
//            )
//        } catch (ex: Throwable) {
//            ex.printStackTrace()
//        }
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}