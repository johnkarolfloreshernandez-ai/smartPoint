package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroUsuarioRequest(
    @SerialName("usuario") val usuario: String,
    @SerialName("correo") val correo: String,
    @SerialName("password") val password: String,
    @SerialName("telefono") val telefono: String = ""
)
