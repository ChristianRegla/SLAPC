package com.example.slapc

// TODO: Reemplazar stub con el objeto real.
object RepositorioPedidos {
    private val pedidos = mutableListOf<Pedido>()

    fun agregarPedido(pedido: Pedido) {
        pedidos.add(pedido)
    }
}