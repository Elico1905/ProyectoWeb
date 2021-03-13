package com.elico.proyectoweb.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elico.proyectoweb.R
import kotlinx.android.synthetic.main.fragment_01.view.*

class Fragment01 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_01, container, false)
        view.fragment01_mensaje.text = "¡Hola!, ${leer()}"
        return view
    }

    private fun leer():String{
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre: String? = prefs?.getString("nombre", "null")
        return nombre!!
    }

}