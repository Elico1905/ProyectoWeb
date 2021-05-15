package com.elico.proyectoweb

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elico.proyectoweb.fragments.Fragment01
import com.elico.proyectoweb.fragments.Fragment02
import com.elico.proyectoweb.fragments.Fragment03
import kotlinx.android.synthetic.main.activity_menu.*

class ActivityMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_menu)

        setUpTabs()
    }


    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment01(),"Inicio")
        adapter.addFragment(Fragment03(),"Informacion")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_settings)

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}