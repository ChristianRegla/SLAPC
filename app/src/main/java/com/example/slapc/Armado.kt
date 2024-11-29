package com.example.slapc

data class Armado(
    val id: Int,
    var nombre: String,
    var descuento: Double,
    var categoria: String,
    var descripcion: String,
    var componentes: List<Componente>
)
