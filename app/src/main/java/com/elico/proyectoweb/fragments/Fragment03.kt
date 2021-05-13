package com.elico.proyectoweb.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elico.proyectoweb.Login
import com.elico.proyectoweb.R
import kotlinx.android.synthetic.main.fragment_03.view.*
import java.text.SimpleDateFormat
import java.util.*

class Fragment03 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_03, container, false)

        view.user.text = "User: ${leer()}"

        view.fragment03_salir.setOnClickListener {
            val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
            prefs?.clear()
            prefs?.apply()
            val intent: Intent = Intent (activity, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return view
    }

    private fun leer(): String {
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre: String? = prefs?.getString("correo", "null")
        return nombre!!
    }


}