package com.example.slapc.ui.catalogo

data class Producto(
    val id: Int,
    val nombre: String,
    val reflimagen: Int, // ID del recurso de la imagen
    val precio: Double,
    val categoria: String,
    val detallesTecnicos: String
)
