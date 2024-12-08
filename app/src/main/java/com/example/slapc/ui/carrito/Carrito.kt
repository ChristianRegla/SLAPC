package com.example.slapc.ui.carrito

import android.icu.util.TimeZone
import com.example.slapc.ui.pedidos.Pedido
import com.example.slapc.ui.pedidos.RepositorioPedidos
import java.util.Calendar

object Carrito {
    private val items = mutableListOf<ItemEnCarrito>()
    var subtotal: Double = 0.0
    var costoGarantias: Double = 0.0
    var total: Double = 0.0
    var garantias = mutableListOf<String>()
    var iva: Double = 0.0

    private var onItemEliminado: (() -> Unit)? = null
    private var onRecalculoDeAcumulados: (() -> Unit)? = null

    fun estaVacio(): Boolean {
        return items.isEmpty()
    }

    fun agregarItem(item: ItemEnCarrito) {
        val itemRepetido = items.any { i -> i.mismaReferenciaA(item) }

        if (!itemRepetido) {
            items.add(item)
            recalcularAcumulados()
        }
    }

    fun obtenerItems(): List<ItemEnCarrito> {
        return items
    }

    fun cambiarCantidadDeItem(itemNum: Int, nuevaCantidad: Int): ItemEnCarrito {
        items[itemNum].cambiarCantidad(nuevaCantidad)
        recalcularAcumulados()
        return items[itemNum]
    }

    fun alternarGarantiaDeItem(itemNum: Int) {
        items[itemNum].alternarGarantia()
        recalcularAcumulados()
    }

    fun agregarCallbackDeEliminacion(callback: () -> Unit) {
        onItemEliminado = callback
    }

    fun quitarCallbackDeEliminacion() {
        onItemEliminado = null
    }

    fun eliminarItem(itemNum: Int) {
        items.removeAt(itemNum)
        recalcularAcumulados()
        onItemEliminado?.invoke()
    }

    fun agregarCallbackDeRecalculos(callback: () -> Unit) {
        onRecalculoDeAcumulados = callback
    }

    fun quitarCallbackDeRecalculos() {
        onRecalculoDeAcumulados = null
    }

    fun generarDetallesDeItemParaPedido(): List<String> {
        val nombres = items.map { item -> "${item.obtenerNombre()} x ${item.cantidad}" }
        return nombres
    }

    fun copiarGarantias(): List<String> {
        val copiaGarantias = garantias.map { g -> g }
        return copiaGarantias
    }

    fun comprar() {
        // Obtener datos preexistentes
        val detallesItems = generarDetallesDeItemParaPedido()
        val garantias = copiarGarantias()
        val total = Carrito.total

        // Obtener datos de tiempo actuales
        val calendario = android.icu.util.Calendar.getInstance(TimeZone.getTimeZone("GMT-06:00"))
        val dia = dosCifrasDe(calendario.get(Calendar.DAY_OF_MONTH))
        val mes = dosCifrasDe(calendario.get(Calendar.MONTH) + 1)
        val anio = calendario.get(Calendar.YEAR)

        // Obtener datos de tiempo de entrega
        calendario.add(Calendar.DATE, 5)
        val diaEntrega = dosCifrasDe(calendario.get(Calendar.DAY_OF_MONTH))
        val mesEntrega = dosCifrasDe(calendario.get(Calendar.MONTH) + 1)
        val anioEntrega = dosCifrasDe(calendario.get(Calendar.YEAR))
        val horas = dosCifrasDe(calendario.get(Calendar.HOUR_OF_DAY))
        val minutos = dosCifrasDe(calendario.get(Calendar.MINUTE))
        val segundos = dosCifrasDe(calendario.get(Calendar.SECOND))

        // Crear strings informativas
        val identificador = "$anio$mes$dia$horas$minutos$segundos"
        val fechaCompra = "$dia/$mes/$anio"
        val horaEntrega = "$horas:$minutos"
        val fechaEntrega = "$diaEntrega/$mesEntrega/$anioEntrega"

        // Crear pedido
        RepositorioPedidos.agregarPedido(
            Pedido(
                identificador,
                fechaCompra,
                fechaEntrega,
                horaEntrega,
                detallesItems,
                total,
                garantias
            )
        )

        // Vaciar listado del carrito
        reiniciar()
    }

    private fun dosCifrasDe(numero: Int): String {
        return String.format("%02d", numero)
    }

    fun reiniciar() {
        items.clear()
        recalcularAcumulados()
        onItemEliminado?.invoke()
    }

    private fun recalcularAcumulados() {
        subtotal = 0.0
        iva = 0.0
        total = 0.0
        costoGarantias = 0.0
        garantias.clear()

        items.forEach { item -> run {
            subtotal += item.subtotal

            if (item.incluirGarantia) {
                costoGarantias += item.subtotal * 0.1
                garantias.add(item.obtenerNombre())
            }
        }}

        iva = (subtotal + costoGarantias) * 0.16
        total = subtotal + costoGarantias + iva

        onRecalculoDeAcumulados?.invoke()
    }
}