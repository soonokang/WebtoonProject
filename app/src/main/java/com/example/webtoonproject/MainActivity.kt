package com.example.webtoonproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import com.example.webtoonproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), OnTabLayoutNameChanged {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(WebViewFragment.Companion.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tab0 = sharedPreferences.getString("tab0_name", "월요일 웹툰")
        val tab1 = sharedPreferences.getString("tab1_name", "화요일 웹툰")
        val tab2 = sharedPreferences.getString("tab2_name", "수요일 웹툰")


        binding.ViewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.ViewPager) { tab, position ->
            run {
//                val textView = TextView(this@MainActivity)
//                textView.text = when(position){
//                    0-> tab0
//                    1-> tab1
//                    else -> tab2
//                }
//                textView.gravity = Gravity.CENTER
//                tab.customView = textView
                tab.text = when (position) {
                    0-> tab0
                    1-> tab1
                    else -> tab2
                }
            }
        }.attach()


    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[binding.ViewPager.currentItem]
        if (currentFragment is WebViewFragment) {
            if (currentFragment.canGoBack()) {
                currentFragment.goBack()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_LONG).show()
    }
}