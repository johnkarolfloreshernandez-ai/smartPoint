package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VentaRequest(
    @SerialName("usuario_id") val usuarioId: Int
)
