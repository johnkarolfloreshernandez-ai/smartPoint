package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class dataCarritoItem(
    @SerialName("id") val idCarrito: Int = 0,
    @SerialName("producto_id") val productoId: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("cantidad") val cantidad: Int = 1,
    @SerialName("url_imagen_principal") val urlImagenPrincipal: String = ""
)
