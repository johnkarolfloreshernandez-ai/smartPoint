package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarritoRequest(
    @SerialName("usuario_id") val usuarioId: Int,
    @SerialName("producto_id") val productoId: Int,
    @SerialName("cantidad") val cantidad: Int = 1)
