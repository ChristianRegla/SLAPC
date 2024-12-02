package com.example.slapc

// TODO: Cambiar stub por clase real
class Armado {
    var nombre: String = "Algun armado"
    var descuento: Int = 50

    fun obtenerPrecio(): Double {
        return 999.99 * descuento / 100
    }
}