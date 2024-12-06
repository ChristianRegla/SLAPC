package com.example.slapc

// TODO: Reemplazar stub con la clase real
class Pedido (
    val identificador: String,
    val fechaCompra: String,
    val fechaEntrega: String,
    val horaEntrega: String,
    val componentes: List<String>,
    val total : Double,
    val garantias: List<String>
)