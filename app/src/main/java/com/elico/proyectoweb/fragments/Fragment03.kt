package com.elico.proyectoweb.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.elico.proyectoweb.Login
import com.elico.proyectoweb.R
import kotlinx.android.synthetic.main.fragment_03.view.*
import java.text.SimpleDateFormat
import java.util.*

class Fragment03 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_03, container, false)

        view.user.text = "User: ${leer()}"

        view.user_name.text = "Nombre: ${getNombre()}"
        Glide.with(view.context).load(getPhoto()).into(view.mensajes_foto)

        view.op_si.setOnClickListener {
            val prefs =
                activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                    ?.edit()
            prefs?.clear()
            prefs?.apply()
            val intent: Intent = Intent(activity, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }

        view.op_no.setOnClickListener {
            view.mensaje_salir.visibility = View.GONE
        }

        view.fragment03_salir.setOnClickListener {
            view.mensaje_salir.visibility = View.VISIBLE
        }
        return view
    }

    private fun leer(): String {
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre: String = "null"
        nombre = prefs?.getString("correo", "null")!!
        var i: Int = 0
        var aux: String = ""
        while (i < nombre.length) {
            if (nombre[i].equals('@')) {
                break
            } else {
                if (i == 0) {
                    aux += nombre[i].toChar().toUpperCase()
                } else {
                    aux += nombre[i]
                }
            }
            i++
        }
        return aux
    }

    private fun getPhoto(): String {
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var photo: String? = prefs?.getString("photo", "null")
        return photo!!
    }

    private fun getNombre(): String {
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre: String = prefs?.getString("nombre", "null")!!
        return nombre!!
    }

}