package com.example.slapc

class Componente(
    var nombre: String,
    var refImagen: String,
    var precio: Double,
    var categoria: CategoriaComponente,
    var detallesTecnicos: String
) {
    var id: Int = obtenerId()

    companion object {
        private var contadorComponentes: Int = 0

        private fun obtenerId(): Int {
            val id = contadorComponentes
            contadorComponentes++
            return id
        }

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

