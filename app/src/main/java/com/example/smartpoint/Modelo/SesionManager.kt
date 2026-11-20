package com.example.smartpoint.Modelo

import android.content.Context

class SesionManager(context: Context) {

    private val prefs = context.getSharedPreferences("SmartPointSesion", Context.MODE_PRIVATE)

    fun guardarSesion(id: Int, usuario: String, correo: String, telefono: String) {
        prefs.edit().apply {
            putBoolean("LOGUEADO", true)
            putInt("USUARIO_ID", id)
            putString("USUARIO_NOMBRE", usuario)
            putString("USUARIO_CORREO", correo)
            putString("USUARIO_TELEFONO", telefono)
            apply()
        }
    }

    fun estaLogueado(): Boolean = prefs.getBoolean("LOGUEADO", false)
    fun obtenerId(): Int = prefs.getInt("USUARIO_ID", 0)
    fun obtenerUsuario(): String = prefs.getString("USUARIO_NOMBRE", "Usuario") ?: "Usuario"
    fun obtenerCorreo(): String = prefs.getString("USUARIO_CORREO", "") ?: ""
    fun obtenerTelefono(): String = prefs.getString("USUARIO_TELEFONO", "") ?: ""

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}