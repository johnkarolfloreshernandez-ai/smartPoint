package com.example.smartpoint.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataVentaHistorial(
    @SerialName("id_venta") val idVenta: Int = 0,
    @SerialName("fecha_venta") val fechaVenta: String = "",
    @SerialName("total") val total: Double = 0.0,
    @SerialName("estado") val estado: String = "completada",
    @SerialName("resumen_productos") val resumenProductos: String = ""
)
