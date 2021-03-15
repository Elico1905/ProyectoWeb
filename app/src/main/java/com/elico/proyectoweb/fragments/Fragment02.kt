package com.elico.proyectoweb.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elico.proyectoweb.R
import kotlinx.android.synthetic.main.fragment_02.view.*

class Fragment02 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_02, container, false)
        view.fragment02_mensaje.text = "${leer()}"
        return view
    }

    private fun leer():String{
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var user: String? = prefs?.getString("user", "null")
        var tipo = "Tipo de usuario: ";
        return if(user.equals("U")){"${tipo}Normal"}else{"${tipo}Administrador"}
    }

}