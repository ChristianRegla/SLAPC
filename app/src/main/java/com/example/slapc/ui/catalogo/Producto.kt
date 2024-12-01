package com.example.slapc.ui.catalogo

import android.R
import com.example.slapc.CategoriaComponente

class Producto(
    id : String,
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
        private var contadorProductos: Int = 0

        // Generar IDs únicos para cada producto
        private fun obtenerId(): Int {
            val id = contadorProductos
            contadorProductos++
            return id
        }

        // Convertir nombres de categorías a enumeración
        fun obtenerCategoriaConNombre(categoria: String): CategoriaComponente? {
            return when (categoria) {
                "CPU" -> CategoriaComponente.CPU
                "Almacenamiento" -> CategoriaComponente.ALMACENAMIENTO
                "Monitor" -> CategoriaComponente.MONITOR
                "Mouse" -> CategoriaComponente.MOUSE
                "Teclado" -> CategoriaComponente.TECLADO
                "RAM" -> CategoriaComponente.RAM
                "Fuente de Poder" -> CategoriaComponente.FUENTE_DE_PODER
                "Gabinete" -> CategoriaComponente.GABINETE
                "Tarjeta Gráfica" -> CategoriaComponente.GPU
                "Placa Madre" -> CategoriaComponente.MOTHERBOARD
                else -> null
            }
        }

        // Convertir enumeración de categorías a nombres
        fun obtenerNombreDeCategoria(categoria: CategoriaComponente): String {
            return when (categoria) {
                CategoriaComponente.CPU -> "CPU"
                CategoriaComponente.ALMACENAMIENTO -> "Almacenamiento"
                CategoriaComponente.MONITOR -> "Monitor"
                CategoriaComponente.MOUSE -> "Mouse"
                CategoriaComponente.TECLADO -> "Teclado"
                CategoriaComponente.RAM -> "RAM"
                CategoriaComponente.FUENTE_DE_PODER -> "Fuente de Poder"
                CategoriaComponente.GABINETE -> "Gabinete"
                CategoriaComponente.GPU -> "Tarjeta Gráfica"
                CategoriaComponente.MOTHERBOARD -> "Placa Madre"
            }
        }
    }
}
