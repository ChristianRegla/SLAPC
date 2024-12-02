package com.example.slapc.ui.carrito

import com.example.slapc.Armado
import com.example.slapc.R
import com.example.slapc.RepositorioArmados

class ArmadoEnCarrito(idReferencia: Int, cantidad: Int) : ItemEnCarrito(idReferencia, "Armado") {
    private val armado: Armado

    init {
        armado = RepositorioArmados.obtenerArmado(idReferencia)!!
        this.cantidad = cantidad
        subtotal = calcularSubtotal()
        incluirGarantia = false
    }

    override fun calcularSubtotal(): Double {
        return armado.obtenerPrecio() * cantidad
    }

    override fun obtenerImagen(): Int {
        return R.drawable.baseline_build_circle_24
    }

    override fun obtenerNombre(): String {
        return armado.nombre
    }
}