package com.example.smartpoint.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartpoint.Modelo.SesionManager
import com.example.smartpoint.Presentador.clsUsuarioPresentador
import com.example.smartpoint.R

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: clsUsuarioPresentador
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter = clsUsuarioPresentador()
        sesionManager = SesionManager(this)

        val edtUsuario: EditText = findViewById(R.id.edtUsuarioLogin)
        val edtPassword: EditText = findViewById(R.id.edtPasswordLogin)
        val btnIniciar: Button = findViewById(R.id.btnIniciarSesion)
        val txtCrearCuenta: TextView = findViewById(R.id.txtIrCrearCuenta)

        btnIniciar.setOnClickListener {
            presenter.iniciarSesion(
                usuario = edtUsuario.text.toString().trim(),
                password = edtPassword.text.toString().trim(),
                onExito = { res ->
                    sesionManager.guardarSesion(
                        id = res.id,
                        usuario = res.usuario,
                        correo = res.correo,
                        telefono = res.telefono ?: "Sin teléfono"
                    )
                    Toast.makeText(this, "Bienvenido ${res.usuario}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PerfilActivity::class.java))
                    finish()
                },
                onError = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            )
        }

        txtCrearCuenta.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}