package com.example.slapc

data class Usuario(
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val direccionEntrega: String,
    val horaInicio: String,
    val horaFin: String,
    val esAdmin: Boolean
)