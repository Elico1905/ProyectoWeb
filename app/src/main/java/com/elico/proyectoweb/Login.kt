package com.elico.proyectoweb

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val GOOGLE_SING_IN = 100
    private val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_login)
        leer()
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

    private fun leer() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var correo: String? = prefs.getString("correo", "null")
        var nombre: String? = prefs.getString("nombre", "null")

        if (!nombre.equals("null") || !correo.equals("null")) {
            val intent: Intent = Intent(this, ActivityMenu::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            bd.collection("users").document(account.email.toString()).set(
                                    hashMapOf("correo" to account.email.toString(),
                                            "nombre" to account.givenName.toString(),
                                            "user" to "U"))
                            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                            prefs.putString("email", account.email.toString())
                            prefs.putString("nombre", account.givenName.toString())
                            prefs.putString("user", "U")
                            prefs.apply()
                            leer()
                        }
                    }
                }
            } catch (e: ApiException) {}
        }
    }

    private fun mostrarIntegrantes(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.negro_true);
        integrantes.visibility = View.VISIBLE
    }
    private fun ocultarIntegrantes(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.negro_false);
        integrantes.visibility = View.GONE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if(integrantes.visibility == View.VISIBLE){
            ocultarIntegrantes()
        }
    }
}