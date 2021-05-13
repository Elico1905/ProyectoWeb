package com.elico.proyectoweb


import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject

class Login : AppCompatActivity() {

    private val GOOGLE_SING_IN = 100

    private val bd = FirebaseFirestore.getInstance()

    private lateinit var animacion: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_login)
        leerPrefs()
        cargarAnimacion()

        boton_registrar.setOnClickListener {
            val intent: Intent = Intent (this, ActivityMenu::class.java)
            startActivity(intent)
            finish()
        }
        cardView_google.setOnClickListener {
            login()
        }
        txt1.setOnClickListener {
            mostrarIntegrantes()
        }
        salir_integrantes.setOnClickListener {
            ocultarIntegrantes()
        }
    }
    private fun login() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    mostrarAnimacion()
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        getData(account.email.toString())
                    }
                }else{
                    //error de que no hay cuenta
                }
            } catch (e: ApiException) {}
        }
    }

    private fun mostrarIntegrantes(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.negro_true)
        integrantes.visibility = View.VISIBLE
    }
    private fun ocultarIntegrantes(){
        window.decorView.systemUiVisibility = View.STATUS_BAR_VISIBLE
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant);
        integrantes.visibility = View.GONE
    }



    private fun cargarAnimacion(){
        login_animacion.setAnimation(R.raw.loading_3)
    }

    private fun mostrarAnimacion(){
        login_load.visibility = View.VISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.negro_true)
        login_animacion.loop(true)
        login_animacion.repeatCount
        login_animacion.playAnimation()
    }

    private fun ocultarAnimacion(){
        login_load.visibility = View.GONE
        window.decorView.systemUiVisibility = View.STATUS_BAR_VISIBLE
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant);
        login_animacion.pauseAnimation()
    }

    private fun getData(dato:String){
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.68/smarthomerest/GetData.php?key=1&user_name=${dato}"

        val stringRequest = StringRequest(Request.Method.GET,url, Response.Listener {response  ->
            if (response.equals("ok")){
                val prefs =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.putString("correo", dato)
                prefs.apply()

                val intent: Intent = Intent (this, ActivityMenu::class.java)
                startActivity(intent)
                finish()
            }
            if (response.equals("registrado")){
                val prefs =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.putString("correo", dato)
                prefs.apply()
                ocultarAnimacion()

                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.negro_true)
                mensaje_registrar.visibility = View.VISIBLE
            }
            if (response.equals("error")){
                Toast.makeText(this, "algo salio mal, intenta de nuevo", Toast.LENGTH_SHORT).show()
                ocultarAnimacion()
            }
        },Response.ErrorListener {
            Toast.makeText(this, "error: ${it.message}", Toast.LENGTH_SHORT).show()
        })
        queue.add(stringRequest)


    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if(integrantes.visibility == View.VISIBLE){
            ocultarIntegrantes()
        }

    }


    private fun leerPrefs(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val Correo: String? = prefs.getString("correo", "2021ff")

        if (!Correo.equals("2021ff")){
            val intent: Intent = Intent (this, ActivityMenu::class.java)
            startActivity(intent)
            finish()
        }
    }

}