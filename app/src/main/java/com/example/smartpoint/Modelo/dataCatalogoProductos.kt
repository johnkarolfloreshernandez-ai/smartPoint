package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCatalogoProductos(
    @SerialName("id") val id: Int = 0,
    @SerialName("categoria_id") val categoriaId: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("descripcion_corta") val descripcionCorta: String = "",
    @SerialName("especificaciones") val especificaciones: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("stock") val stock: Int = 0,
    @SerialName("url_imagen_principal") val urlImagenPrincipal: String = ""
)
