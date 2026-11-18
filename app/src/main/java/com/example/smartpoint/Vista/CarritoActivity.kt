package com.example.smartpoint.Vista

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpoint.Adaptador.CarritoAdapter
import com.example.smartpoint.Modelo.dataCarritoItem
import com.example.smartpoint.Presentador.clsCarritoPresentador
import com.example.smartpoint.R
import java.text.NumberFormat
import java.util.Locale

class CarritoActivity : AppCompatActivity() {

    private lateinit var presenter: clsCarritoPresentador
    private lateinit var rvProductosCarrito: RecyclerView
    private lateinit var adapterCarrito: CarritoAdapter
    private lateinit var txtSubtotal: TextView
    private lateinit var txtTotalFinal: TextView

    private val usuarioId: Int = 1 // Usuario por defecto hasta conectar Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCarrito)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar: ImageView = findViewById(R.id.btnRegresarCarrito)
        rvProductosCarrito = findViewById(R.id.rvProductosCarrito)
        txtSubtotal = findViewById(R.id.txtSubtotalCarrito)
        txtTotalFinal = findViewById(R.id.txtTotalFinalCarrito)

        rvProductosCarrito.layoutManager = LinearLayoutManager(this)
        adapterCarrito = CarritoAdapter(emptyList())
        rvProductosCarrito.adapter = adapterCarrito

        btnRegresar.setOnClickListener { finish() }

        presenter = clsCarritoPresentador(this)
        presenter.cargarCarrito(usuarioId)
    }

    fun mostrarProductosCarrito(items: List<dataCarritoItem>) {
        adapterCarrito.actualizarLista(items)
    }

    fun mostrarTotales(subtotal: Double, totalFinal: Double) {
        val formatoMoneda = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        txtSubtotal.text = "$${formatoMoneda.format(subtotal)}"
        txtTotalFinal.text = "$${formatoMoneda.format(totalFinal)}"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}