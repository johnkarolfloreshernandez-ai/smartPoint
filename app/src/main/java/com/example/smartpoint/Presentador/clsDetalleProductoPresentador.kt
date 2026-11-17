package com.example.smartpoint.Presentador

import com.example.smartpoint.Modelo.clsDetalleProductoModelo
import com.example.smartpoint.Vista.activity_detalle_producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleProductoPresentador (private val vista: activity_detalle_producto) {

    private val modelo = clsDetalleProductoModelo()

    fun cargarGaleria(productoId: Int, urlImagenPrincipal: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imagenesSecundarias = modelo.obtenerGaleria(productoId)
                val urls = if (imagenesSecundarias.isNotEmpty()) {
                    imagenesSecundarias.map { it.urlImagen }
                } else {
                    listOf(urlImagenPrincipal)
                }
                vista.mostrarGaleria(urls)
            } catch (e: Exception) {
                vista.mostrarGaleria(listOf(urlImagenPrincipal))
                vista.mostrarMensaje("Error al cargar galería: " + e.localizedMessage)
            }
        }
    }

    fun agregarAlCarrito(usuarioId: Int, productoId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.agregarAlCarrito(usuarioId, productoId, 1)
                vista.mostrarMensaje(respuesta.mensaje)
            } catch (e: Exception) {
                vista.mostrarMensaje("Error al agregar al carrito: " + e.localizedMessage)
            }
        }
    }
}