package com.example.confiesionesdesanagustin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.confiesionesdesanagustin.utils.DatabaseHelper
import com.example.confiesionesdesanagustin.utils.RecyclerAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.nozc.confiesionesdesanagustin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //mob ads
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            MobileAds.initialize(this@MainActivity) {}
        }
        val adView:AdView = findViewById(R.id.mainBanner)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        //

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dbHelper = DatabaseHelper(this)
        try {
            dbHelper.createDatabase()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        dbHelper.openDataBase()
        val data = dbHelper.getAllBooks()
        recyclerView = findViewById(R.id.recyclerBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(this, data)
        recyclerView.adapter = adapter
    }
}