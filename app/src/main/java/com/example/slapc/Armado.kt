package com.example.slapc

data class Armado(
    val id: Int,
    var nombre: String,
    var descuento: Double,
    var categoria: String,
    var descripcion: String,
    var componentes: List<Componente>,
    var precio: Double
) {
    fun obtenerPrecio(): Double {
        var precioAcumulado: Double = 0.0
        componentes.forEach { c -> precioAcumulado += c.precio }
        precio = precioAcumulado * (100 - descuento) / 100
        return precio
    }
}
