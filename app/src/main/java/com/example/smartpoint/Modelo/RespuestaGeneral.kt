package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RespuestaGeneral(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = ""
)
