package com.simplemobiletools.thankyou.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.simplemobiletools.commons.extensions.appLaunched
import com.simplemobiletools.commons.extensions.checkWhatsNew
import com.simplemobiletools.commons.extensions.restartActivity
import com.simplemobiletools.commons.extensions.updateTextColors
import com.simplemobiletools.commons.helpers.LICENSE_KOTLIN
import com.simplemobiletools.commons.models.Release
import com.simplemobiletools.thankyou.BuildConfig
import com.simplemobiletools.thankyou.R
import com.simplemobiletools.thankyou.extensions.config
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SimpleActivity() {
    var storedUseEnglish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched()
        checkWhatsNewDialog()
        storeStateVariables()
    }

    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            restartActivity()
            return
        }

        updateTextColors(activity_main)
    }

    override fun onPause() {
        super.onPause()
        storeStateVariables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> launchSettings()
            R.id.about -> launchAbout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun storeStateVariables() {
        config.apply {
            storedUseEnglish = useEnglish
        }
    }

    private fun launchSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        startAboutActivity(R.string.app_name, LICENSE_KOTLIN, BuildConfig.VERSION_NAME)
    }

    private fun checkWhatsNewDialog() {
        arrayListOf<Release>().apply {
            add(Release(3, R.string.release_3))
            checkWhatsNew(this, BuildConfig.VERSION_CODE)
        }
    }
}
