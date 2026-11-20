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

class RegistroActivity : AppCompatActivity() {

    private lateinit var presenter: clsUsuarioPresentador
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainRegistro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter = clsUsuarioPresentador()
        sesionManager = SesionManager(this)

        val edtUsuario: EditText = findViewById(R.id.edtUsuarioRegistro)
        val edtCorreo: EditText = findViewById(R.id.edtCorreoRegistro)
        val edtPassword: EditText = findViewById(R.id.edtPasswordRegistro)
        val edtConfirmar: EditText = findViewById(R.id.edtConfirmarPasswordRegistro)
        val btnCrear: Button = findViewById(R.id.btnCrearCuenta)
        val txtVolver: TextView = findViewById(R.id.txtVolverLogin)

        btnCrear.setOnClickListener {
            presenter.registrarUsuario(
                usuario = edtUsuario.text.toString().trim(),
                correo = edtCorreo.text.toString().trim(),
                password = edtPassword.text.toString().trim(),
                confirmarPassword = edtConfirmar.text.toString().trim(),
                onExito = { res ->
                    sesionManager.guardarSesion(
                        id = res.id,
                        usuario = res.usuario,
                        correo = res.correo,
                        telefono = res.telefono ?: "Sin teléfono"
                    )
                    Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PerfilActivity::class.java))
                    finish()
                },
                onError = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            )
        }

        txtVolver.setOnClickListener { finish() }
    }
}