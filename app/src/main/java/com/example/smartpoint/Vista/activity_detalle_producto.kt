package com.example.smartpoint.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.smartpoint.Adaptador.GaleriaProductoAdapter
import com.example.smartpoint.Modelo.SesionManager
import com.example.smartpoint.Presentador.clsDetalleProductoPresentador
import com.example.smartpoint.R
import com.example.smartpoint.Red.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL
import java.text.NumberFormat
import java.util.Locale

class activity_detalle_producto : AppCompatActivity() {

    private lateinit var presenter: clsDetalleProductoPresentador
    private lateinit var vpGaleria: ViewPager2
    private lateinit var adapterGaleria: GaleriaProductoAdapter
    private lateinit var sesionManager: SesionManager
    private lateinit var txtStock: TextView
    private lateinit var btnAgregar: Button

    private var productoId: Int = 0
    private var stockActual: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sesionManager = SesionManager(this)

        val txtNombre: TextView = findViewById(R.id.txtdetalleProducto)
        txtStock = findViewById(R.id.txtdetalleStock)
        val txtPrecio: TextView = findViewById(R.id.txtdetallePrecio)
        val txtDescripcion: TextView = findViewById(R.id.txtdetalleDescripcion)
        val txtDescripcionMas: TextView = findViewById(R.id.txtdetalleDescripcionMas)
        btnAgregar = findViewById(R.id.btnAgregarCarrito)
        val btnRegresar: ImageButton = findViewById(R.id.btnRegresarDetalle)

        vpGaleria = findViewById(R.id.vpGaleria)

        productoId = intent.getIntExtra("PRODUCTO_ID", 0)
        val nombre = intent.getStringExtra("NOMBRE") ?: ""
        val descripcion = intent.getStringExtra("DESCRIPCION_CORTA") ?: ""
        val especificaciones = intent.getStringExtra("ESPECIFICACIONES") ?: ""
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        stockActual = intent.getIntExtra("STOCK", 0)
        val urlPrincipal = intent.getStringExtra("URL_IMAGEN_PRINCIPAL") ?: ""

        val formatoMoneda = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        txtNombre.text = nombre
        txtPrecio.text = "MXN$${formatoMoneda.format(precio)}"
        txtDescripcion.text = descripcion
        txtDescripcionMas.text = especificaciones
        actualizarVistaStock(stockActual)

        btnRegresar.setOnClickListener {
            finish()
        }

        adapterGaleria = GaleriaProductoAdapter(emptyList())
        vpGaleria.adapter = adapterGaleria

        presenter = clsDetalleProductoPresentador(this)
        presenter.cargarGaleria(productoId, urlPrincipal)

        btnAgregar.setOnClickListener {
            if (stockActual <= 0) {
                mostrarMensaje("Producto agotado")
                return@setOnClickListener
            }
            val usuarioActualId = if (sesionManager.estaLogueado()) sesionManager.obtenerId() else 1
            presenter.agregarAlCarrito(usuarioActualId, productoId)
        }
    }

    override fun onResume() {
        super.onResume()
        if (productoId > 0) {
            refrescarStockDesdeServidor()
        }
    }

    private fun refrescarStockDesdeServidor() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val urlConsulta = "${RetrofitCliente.BASE_URL}obtener_productos.php?categoria_id=1"
                val respuesta = URL(urlConsulta).readText()
                val arreglo = JSONArray(respuesta)
                for (i in 0 until arreglo.length()) {
                    val obj = arreglo.getJSONObject(i)
                    if (obj.getInt("id") == productoId) {
                        val nuevoStock = obj.getInt("stock")
                        withContext(Dispatchers.Main) {
                            stockActual = nuevoStock
                            actualizarVistaStock(stockActual)
                        }
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun actualizarVistaStock(stock: Int) {
        txtStock.text = "Disponible: $stock"
        if (stock <= 0) {
            btnAgregar.isEnabled = false
            btnAgregar.text = "Agotado"
        } else {
            btnAgregar.isEnabled = true
            btnAgregar.text = "Agregar al carrito"
        }
    }

    fun mostrarGaleria(urls: List<String>) {
        adapterGaleria.actualizarImagenes(urls)
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}