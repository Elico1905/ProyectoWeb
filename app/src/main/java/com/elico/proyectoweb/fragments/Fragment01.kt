package com.elico.proyectoweb.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginTop
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.elico.proyectoweb.ActivityMenu
import com.elico.proyectoweb.R
import kotlinx.android.synthetic.main.fragment_01.*
import kotlinx.android.synthetic.main.fragment_01.view.*
import java.text.SimpleDateFormat
import java.util.*

class Fragment01 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_01, container, false)
        consultar(view)
        view.actualizar.setOnClickListener {
            consultar(view)
        }
        //view.fragment01_mensaje.text = "¡Hola!, ${leer()}"
        return view
    }

    private fun consultar(view: View) {
        var dato:Int = 10

        val queue = Volley.newRequestQueue(context)
        val url = "https://elicoproyectoweb.000webhostapp.com/GetData.php?key=2"
        val stringRequest = StringRequest(Request.Method.GET,url, Response.Listener { response  ->
            if (response.equals("error")){
                Toast.makeText(context, "algo salio mal, intenta de nuevo", Toast.LENGTH_SHORT).show()
            }else{
                dato = response.toInt()
                var params = view.termometro.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = Transformar(dato)
                view.termometro.layoutParams = params
                view.temperatura.text = "${dato}°C"
                view.datos.text = "${getDataHora()}"
            }
        }, Response.ErrorListener {
            Toast.makeText(context, "Error al conectar al servidor", Toast.LENGTH_LONG).show()
        })
        queue.add(stringRequest)

    }

    private fun getDataHora():String{
        val fecha = SimpleDateFormat("dd/M/yyyy")
        val Datefecha = fecha.format(Date())

        val hora = SimpleDateFormat("HH:mm:ss")
        val Datehora = hora.format(Date())

        var datos:String = "Ultima actualizacion: ${Datefecha} a las ${Datehora}"
        return  datos
    }

    private fun leer(): String {
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre: String? = prefs?.getString("nombre", "null")
        return nombre!!
    }



    private fun Transformar(dato:Int):Int{
        when(dato){
            50 -> { return 70 }
            49 -> { return 84 }
            48 -> { return 99 }
            47 -> { return 114 }
            46 -> { return 129 }
            45 -> { return 144 }
            44 -> { return 158 }
            43 -> { return 173 }
            42 -> { return 188 }
            41 -> { return 203 }
            40 -> { return 218 }
            39 -> { return 232 }
            38 -> { return 247 }
            37 -> { return 262 }
            36 -> { return 277 }
            35 -> { return 292 }
            34 -> { return 306 }
            33 -> { return 321 }
            32 -> { return 336 }
            31 -> { return 351 }
            30 -> { return 366 }
            29 -> { return 380 }
            28 -> { return 395 }
            27 -> { return 410 }
            26 -> { return 425 }
            25 -> { return 440 }
            24 -> { return 455 }
            23 -> { return 470 }
            22 -> { return 485 }
            21 -> { return 500 }
            20 -> { return 515 }
            19 -> { return 530 }
            18 -> { return 545 }
            17 -> { return 560 }
            16 -> { return 575 }
            15 -> { return 590 }
            14 -> { return 605 }
            13 -> { return 620 }
            12 -> { return 635 }
            11 -> { return 650 }
            10 -> { return 665 }
            9 -> { return 680 }
            8 -> { return 695 }
            7 -> { return 710 }
            6 -> { return 725 }
            5 -> { return 740 }
            4 -> { return 755 }
            3 -> { return 770 }
            2 -> { return 785 }
            1 -> { return 800 }
            else->{return 800}
        }
    }

}