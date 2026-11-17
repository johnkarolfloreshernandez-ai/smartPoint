package com.example.smartpoint.Vista

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartpoint.Adaptador.catalogoProducto
import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.Modelo.dataCategoria
import com.example.smartpoint.Presentador.clsProductosPresentador
import com.example.smartpoint.R

class MainActivity : AppCompatActivity() {

    lateinit var presenter: clsProductosPresentador
    private lateinit var spinnerCategorias: Spinner
    private lateinit var recycleProductos: RecyclerView
    private lateinit var adapterCatalogoProductos: catalogoProducto
    private lateinit var edtBuscarProducto: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerCategorias = findViewById(R.id.spinnerCategorias)
        recycleProductos = findViewById(R.id.Rclr_View)
        edtBuscarProducto = findViewById(R.id.edtBuscarProducto)

        recycleProductos.layoutManager = GridLayoutManager(this, 2)
        adapterCatalogoProductos = catalogoProducto(this, emptyList())
        recycleProductos.adapter = adapterCatalogoProductos

        presenter = clsProductosPresentador(this)
        configurarListeners()
        presenter.iniciar()
    }

    private fun configurarListeners() {
        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val textoBuscar = edtBuscarProducto.text.toString().trim()
                presenter.seleccionarCategoria(position, textoBuscar)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        edtBuscarProducto.setOnItemClickListener { parent, _, position, _ ->
            val seleccionado = parent.getItemAtPosition(position).toString()
            presenter.buscarProductos(seleccionado)
        }

        edtBuscarProducto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.buscarProductos(s.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        edtBuscarProducto.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val textoBuscar = edtBuscarProducto.text.toString().trim()
                presenter.buscarProductos(textoBuscar)
                true
            } else {
                false
            }
        }
    }

    fun configurarAutocompletado(nombresProductos: List<String>) {
        val adapterAuto = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            nombresProductos
        )
        edtBuscarProducto.setAdapter(adapterAuto)
    }

    fun mostrarCategorias(categorias: List<dataCategoria>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = adapter
    }

    fun mostrarProductos(productos: List<dataCatalogoProductos>) {
        adapterCatalogoProductos.actualizarLista(productos)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun seleccionarProducto(producto: dataCatalogoProductos) {
        val intent = Intent(this, activity_detalle_producto::class.java).apply {
            putExtra("PRODUCTO_ID", producto.id)
            putExtra("NOMBRE", producto.nombre)
            putExtra("DESCRIPCION_CORTA", producto.descripcionCorta)
            putExtra("ESPECIFICACIONES", producto.especificaciones)
            putExtra("PRECIO", producto.precio)
            putExtra("URL_IMAGEN_PRINCIPAL", producto.urlImagenPrincipal)
        }
        startActivity(intent)
    }
}