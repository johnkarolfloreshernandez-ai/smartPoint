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
        val edtTelefono: EditText = findViewById(R.id.edtTelefonoRegistro)
        val edtPassword: EditText = findViewById(R.id.edtPasswordRegistro)
        val edtConfirmar: EditText = findViewById(R.id.edtConfirmarPasswordRegistro)
        val btnCrear: Button = findViewById(R.id.btnCrearCuenta)
        val txtVolver: TextView = findViewById(R.id.txtVolverLogin)

        btnCrear.setOnClickListener {
            if (!validarFormulario(edtUsuario, edtCorreo, edtTelefono, edtPassword, edtConfirmar)) {
                return@setOnClickListener
            }

            val usuario = edtUsuario.text.toString().trim()
            val correo = edtCorreo.text.toString().trim()
            val telefono = edtTelefono.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmarPassword = edtConfirmar.text.toString().trim()

            presenter.registrarUsuario(
                usuario = usuario,
                correo = correo,
                telefono = telefono,
                password = password,
                confirmarPassword = confirmarPassword,
                onExito = { res ->
                    sesionManager.guardarSesion(
                        id = res.id,
                        usuario = res.usuario,
                        correo = res.correo,
                        telefono = res.telefono ?: "Sin teléfono"
                    )
                    Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onError = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            )
        }

        txtVolver.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validarFormulario(
        edtUsuario: EditText,
        edtCorreo: EditText,
        edtTelefono: EditText,
        edtPassword: EditText,
        edtConfirmar: EditText
    ): Boolean {
        val usuario = edtUsuario.text.toString().trim()
        val correo = edtCorreo.text.toString().trim()
        val telefono = edtTelefono.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmar = edtConfirmar.text.toString().trim()

        // 1. Usuario: no vacío y debe contener letras (no solo números)
        val regexUsuario = Regex("^(?=.*[a-zA-ZáéíóúÁÉÍÓÚñÑ])[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ_\\s]{3,30}$")
        if (usuario.isEmpty()) {
            edtUsuario.error = "Ingresa un nombre de usuario"
            edtUsuario.requestFocus()
            return false
        }
        if (!regexUsuario.matches(usuario)) {
            edtUsuario.error = "El usuario debe incluir letras (mínimo 3 caracteres)"
            edtUsuario.requestFocus()
            return false
        }

        // 2. Correo: no vacío, solo minúsculas, formato válido con @ y dominio
        val regexCorreo = Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        if (correo.isEmpty()) {
            edtCorreo.error = "Ingresa un correo electrónico"
            edtCorreo.requestFocus()
            return false
        }
        if (correo != correo.lowercase()) {
            edtCorreo.error = "El correo debe estar en minúsculas"
            edtCorreo.requestFocus()
            return false
        }
        if (!regexCorreo.matches(correo)) {
            edtCorreo.error = "Ingresa un correo válido (ejemplo@dominio.com)"
            edtCorreo.requestFocus()
            return false
        }

        // 3. Teléfono: no vacío y exactamente 10 dígitos
        if (telefono.isEmpty()) {
            edtTelefono.error = "Ingresa un número de teléfono"
            edtTelefono.requestFocus()
            return false
        }
        if (!telefono.matches(Regex("^\\d{10}$"))) {
            edtTelefono.error = "El teléfono debe tener exactamente 10 dígitos"
            edtTelefono.requestFocus()
            return false
        }

        // 4. Contraseña: no vacía y mínimo 6 caracteres
        if (password.isEmpty()) {
            edtPassword.error = "Ingresa una contraseña"
            edtPassword.requestFocus()
            return false
        }
        if (password.length < 6) {
            edtPassword.error = "La contraseña debe tener al menos 6 caracteres"
            edtPassword.requestFocus()
            return false
        }

        // 5. Confirmar contraseña: no vacía y coincidente
        if (confirmar.isEmpty()) {
            edtConfirmar.error = "Confirma tu contraseña"
            edtConfirmar.requestFocus()
            return false
        }
        if (password != confirmar) {
            edtConfirmar.error = "Las contraseñas no coinciden"
            edtConfirmar.requestFocus()
            return false
        }

        return true
    }
}