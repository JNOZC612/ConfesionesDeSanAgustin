package com.example.confiesionesdesanagustin.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.confiesionesdesanagustin.utils.AdapterReader
import com.example.confiesionesdesanagustin.utils.DatabaseHelper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.nozc.confiesionesdesanagustin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ad mob

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            MobileAds.initialize(this@ReaderActivity) {}
        }
        val adView: AdView = findViewById(R.id.readerBanner)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        //

        val toolbar: Toolbar = findViewById(R.id.toolbarReader)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val viewPager: ViewPager2 = findViewById(R.id.viewPagerReader)
        val helper = DatabaseHelper(this)
        val intent = intent
        val book = intent.getIntExtra("book", 0)
        val chapter = intent.getIntExtra("chapter", 0)
        val list = helper.getCompleteChapters(book)
        val adapter = AdapterReader(list)
        viewPager.adapter = adapter
        viewPager.currentItem = chapter
    }
}