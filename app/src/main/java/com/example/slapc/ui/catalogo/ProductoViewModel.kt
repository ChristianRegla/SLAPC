package com.example.slapc.ui.catalogo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductoViewModel : ViewModel() {
    private val _productos = MutableLiveData<List<Producto>>(emptyList())
    val productos: LiveData<List<Producto>> get() = _productos

    fun agregarProductoDesdeComponente(componente: Producto) {
        val producto = Producto(
            id = componente.id.toString(),
            nombre = componente.nombre,
            refImagen = componente.refImagen,
            precio = componente.precio,
            categoria = componente.categoria,
            detallesTecnicos = componente.detallesTecnicos
        )
        val listaActual = _productos.value?.toMutableList() ?: mutableListOf()
        listaActual.add(producto)
        _productos.value = listaActual
    }
    fun setProductos(nuevaLista: List<Producto>) {
        _productos.value = nuevaLista
    }
}
