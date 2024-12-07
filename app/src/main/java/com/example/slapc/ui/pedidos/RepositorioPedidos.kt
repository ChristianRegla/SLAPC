package com.example.slapc.ui.pedidos

import com.example.slapc.CategoriaComponente
import com.example.slapc.ui.pedidos.componentePedido

object RepositorioPedidos {
    private val listaPedidos = mutableListOf<Pedido>()



    // Agregar un nuevo pedido
    fun agregarPedido(pedido: Pedido) {
        listaPedidos.add(pedido)
    }

    // Obtener todos los pedidos
    fun obtenerPedidos(): List<Pedido> {
        return listaPedidos
    }

    // Buscar un pedido por ID
    fun buscarPedidoPorId(id: String): Pedido? {
        return listaPedidos.find { it.id == id }
    }

}
