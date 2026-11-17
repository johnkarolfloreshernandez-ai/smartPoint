package com.example.smartpoint.Presentador

import com.example.smartpoint.Modelo.clsCatalogoProductosModelo
import com.example.smartpoint.Modelo.clsCategoriasModelo
import com.example.smartpoint.Modelo.dataCatalogoProductos
import com.example.smartpoint.Modelo.dataCategoria
import com.example.smartpoint.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsProductosPresentador(private val vista: MainActivity) {

    private val modeloCatalogoProductos = clsCatalogoProductosModelo()
    private val modeloCategorias = clsCategoriasModelo()
    private var listaCategorias: List<dataCategoria> = emptyList()
    private var categoriaActualId: Int = 1

    fun iniciar() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaCategorias = modeloCategorias.obtenerCategorias()
                vista.mostrarCategorias(listaCategorias)

                val todosLosProductos = modeloCatalogoProductos.obtenerProductos(1, "")
                vista.mostrarProductos(todosLosProductos)

                val nombresSugerencias = todosLosProductos.map { it.nombre }
                vista.configurarAutocompletado(nombresSugerencias)
            } catch (e: Exception) {
                vista.mostrarError("Error al iniciar: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCategoria(posicion: Int, textoBuscar: String = "") {
        if (posicion >= 0 && posicion < listaCategorias.size) {
            val categoria = listaCategorias[posicion]
            categoriaActualId = categoria.idCategoria
            cargarProductos(categoriaActualId, textoBuscar)
        }
    }

    fun buscarProductos(textoBuscar: String) {
        cargarProductos(categoriaActualId, textoBuscar)
    }

    private fun cargarProductos(idCategoria: Int, buscar: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val productos = modeloCatalogoProductos.obtenerProductos(idCategoria, buscar)
                vista.mostrarProductos(productos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar los productos: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarProducto(producto: dataCatalogoProductos) {
        vista.seleccionarProducto(producto)
    }
}