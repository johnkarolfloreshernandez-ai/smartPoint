package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("usuario") val usuario: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String? = ""
)
