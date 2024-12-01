package com.example.slapc.ui.catalogo

data class Producto(
    val id: Int,
    val nombre: String,
    val reflimagen: Any,
    val precio: Double,
    val categoria: String,
    val detallesTecnicos: String
)
