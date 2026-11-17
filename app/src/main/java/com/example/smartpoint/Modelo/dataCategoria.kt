package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCategoria(
    @SerialName("id") val idCategoria: Int = 0,
    @SerialName("categoria") val categoria: String = ""
) {
    override fun toString(): String {
        return categoria
    }
}