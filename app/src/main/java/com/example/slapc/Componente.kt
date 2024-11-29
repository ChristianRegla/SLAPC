package com.example.slapc

class Componente (
    nombre: String,
    refImagen: String,
    precio: Double,
    categoria: CategoriaComponente,
    detallesTecnicos: String
) {
    var id: Int = obtenerId()
    var nombre: String = nombre
    var refImagen: String = refImagen
    var precio: Double = precio
    var categoria: CategoriaComponente = categoria
    var detallesTecnicos: String = detallesTecnicos

    companion object {
        private var contadorComponentes: Int = 0

        private fun obtenerId(): Int {
            val id = contadorComponentes
            contadorComponentes++
            return id
        }
    }
};