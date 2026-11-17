package com.example.smartpoint.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpoint.Adaptador.GaleriaProductoAdapter
import com.example.smartpoint.Presentador.clsDetalleProductoPresentador
import com.example.smartpoint.R
import java.text.NumberFormat
import java.util.Locale
import kotlin.collections.emptyList

class activity_detalle_producto : AppCompatActivity() {

    private lateinit var presenter: clsDetalleProductoPresentador
    private lateinit var vpGaleria: ViewPager2
    private lateinit var adapterGaleria: GaleriaProductoAdapter

    private var productoId: Int = 0
    private var usuarioId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre: TextView = findViewById(R.id.txtdetalleProducto)
        val txtPrecio: TextView = findViewById(R.id.txtdetallePrecio)
        val txtDescripcion: TextView = findViewById(R.id.txtdetalleDescripcion)
        val txtDescripcionMas: TextView = findViewById(R.id.txtdetalleDescripcionMas)
        val btnAgregar: Button = findViewById(R.id.btnAgregarCarrito)

        vpGaleria = findViewById(R.id.vpGaleria)

        productoId = intent.getIntExtra("PRODUCTO_ID", 0)
        val nombre = intent.getStringExtra("NOMBRE") ?: ""
        val descripcion = intent.getStringExtra("DESCRIPCION_CORTA") ?: ""
        val especificaciones = intent.getStringExtra("ESPECIFICACIONES") ?: ""
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        val urlPrincipal = intent.getStringExtra("URL_IMAGEN_PRINCIPAL") ?: ""

        val formatoMoneda = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        txtNombre.text = nombre
        txtPrecio.text = "MXN$${formatoMoneda.format(precio)}"
        txtDescripcion.text = descripcion
        txtDescripcionMas.text = especificaciones

        adapterGaleria = GaleriaProductoAdapter(emptyList())
        vpGaleria.adapter = adapterGaleria

        presenter = clsDetalleProductoPresentador(this)
        presenter.cargarGaleria(productoId, urlPrincipal)

        btnAgregar.setOnClickListener {
            presenter.agregarAlCarrito(usuarioId, productoId)
        }
    }

    fun mostrarGaleria(urls: List<String>) {
        adapterGaleria.actualizarImagenes(urls)
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}