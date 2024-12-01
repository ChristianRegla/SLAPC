package com.example.slapc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.slapc.ui.catalogo.Producto
import com.example.slapc.R

class ProductoViewModel : ViewModel() {
    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> get() = _productos

    private val _categoriaSeleccionada = MutableLiveData<String?>()
    val categoriaSeleccionada: LiveData<String?> get() = _categoriaSeleccionada

    init {
        // Inicializa los productos con una lista predeterminada
        _productos.value = listOf(
            Producto(1, "Ram DD4", R.drawable.ram, 90.7, "Domestico", "Increible y veloz Ram"),
            Producto(2, "Monitor LG", R.drawable.monitor, 90.7, "Oficina", "Último modelo de LG"),
            Producto(3, "Gabinete Gamer", R.drawable.gabinete, 90.7, "Gaming", "Último modelo de Gabinetes")
        )
    }

    fun agregarProducto(producto: Producto) {
        val listaActual = _productos.value.orEmpty()
        _productos.value = listaActual + producto
    }

    fun filtrarProductos(query: String?) {
        _productos.value = _productos.value.orEmpty().filter {
            it.nombre.contains(query ?: "", ignoreCase = true)
        }
    }

    fun filtrarPorCategoria(categoria: String) {
        _productos.value = if (categoria == "Categoria") {
            obtenerProductos()
        } else {
            obtenerProductos().filter { it.categoria == categoria }
        }
    }

    private fun obtenerProductos(): List<Producto> {
        return listOf(
            Producto(1, "Ram DD4", R.drawable.ram, 90.7, "Domestico", "Increible y veloz Ram"),
            Producto(2, "Monitor LG", R.drawable.monitor, 90.7, "Oficina", "Último modelo de LG"),
            Producto(3, "Gabinete Gamer", R.drawable.gabinete, 90.7, "Gaming", "Último modelo de Gabinetes")
        )
    }
}