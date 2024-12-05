package com.example.slapc.ui.pedidos

data class Pedido (
    val id: String,
    val fechaCompra: String,
    val fechaEntrega: String,
    val horaEntrega: String,
    val componentes: List<String>,
    val total: Double,
    val garantias: List<String>
)

