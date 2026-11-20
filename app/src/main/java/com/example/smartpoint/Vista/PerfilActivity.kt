package com.example.smartpoint.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpoint.Adaptador.ComprasAdapter
import com.example.smartpoint.Modelo.SesionManager
import com.example.smartpoint.Presentador.clsUsuarioPresentador
import com.example.smartpoint.R

class PerfilActivity : AppCompatActivity() {

    private lateinit var presenter: clsUsuarioPresentador
    private lateinit var sesionManager: SesionManager
    private lateinit var adapterCompras: ComprasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPerfil)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sesionManager = SesionManager(this)
        presenter = clsUsuarioPresentador()

        if (!sesionManager.estaLogueado()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val txtSaludo: TextView = findViewById(R.id.txtSaludoPerfil)
        val txtUsuario: TextView = findViewById(R.id.txtUsuarioPerfil)
        val txtCorreo: TextView = findViewById(R.id.txtCorreoPerfil)
        val txtTelefono: TextView = findViewById(R.id.txtTelefonoPerfil)
        val txtSinCompras: TextView = findViewById(R.id.txtSinComprasPerfil)
        val rvCompras: RecyclerView = findViewById(R.id.rvComprasPerfil)
        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesion)
        val btnVolverInicio: Button = findViewById(R.id.btnVolverInicioPerfil)

        rvCompras.layoutManager = LinearLayoutManager(this)
        adapterCompras = ComprasAdapter(emptyList())
        rvCompras.adapter = adapterCompras

        txtSaludo.text = "¡Hola ${sesionManager.obtenerUsuario()} !"
        txtUsuario.text = sesionManager.obtenerUsuario()
        txtCorreo.text = sesionManager.obtenerCorreo()
        txtTelefono.text = sesionManager.obtenerTelefono().ifEmpty { "Sin teléfono registrado" }

        val usuarioId = sesionManager.obtenerId()

        presenter.cargarPerfil(
            usuarioId = usuarioId,
            onExito = { res ->
                txtSaludo.text = "¡Hola ${res.usuario} !"
                txtUsuario.text = res.usuario
                txtCorreo.text = res.correo
                txtTelefono.text = res.telefono?.ifEmpty { "Sin teléfono registrado" } ?: "Sin teléfono registrado"
            },
            onError = { }
        )

        presenter.cargarHistorialCompras(
            usuarioId = usuarioId,
            onExito = { compras ->
                if (compras.isEmpty()) {
                    txtSinCompras.visibility = View.VISIBLE
                } else {
                    txtSinCompras.visibility = View.GONE
                    adapterCompras.actualizarLista(compras)
                }
            },
            onError = { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        )

        btnCerrarSesion.setOnClickListener {
            sesionManager.cerrarSesion()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnVolverInicio.setOnClickListener { finish() }
    }
}