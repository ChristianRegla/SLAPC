package com.example.slapc

class Componente (
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
        private var contadorComponentes: Int = 0

        private fun obtenerId(): Int {
            val id = contadorComponentes
            contadorComponentes++
            return id
        }

        fun obtenerCategoriaConNombre(categoria: String): CategoriaComponente? {
            return when (categoria) {
                "CPU" -> CategoriaComponente.CPU;
                "Almacenamiento" -> CategoriaComponente.ALMACENAMIENTO;
                "Monitor" -> CategoriaComponente.MONITOR;
                "Mouse" -> CategoriaComponente.MOUSE;
                "Teclado" -> CategoriaComponente.TECLADO;
                "RAM" -> CategoriaComponente.RAM;
                "Fuente de Poder" -> CategoriaComponente.FUENTE_DE_PODER;
                "Gabinete" -> CategoriaComponente.GABINETE;
                "Tarjeta Gráfica" -> CategoriaComponente.GPU;
                "Placa Madre" -> CategoriaComponente.MOTHERBOARD;
                else -> null
            }
        }

        fun obtenerNombreDeCategoria(categoria: CategoriaComponente): String {
            return when (categoria) {
                CategoriaComponente.TODOS -> "Todos";
                CategoriaComponente.CPU -> "CPU";
                CategoriaComponente.ALMACENAMIENTO -> "Almacenamiento";
                CategoriaComponente.MONITOR -> "Monitor";
                CategoriaComponente.MOUSE -> "Mouse";
                CategoriaComponente.TECLADO -> "Teclado";
                CategoriaComponente.RAM -> "RAM"
                CategoriaComponente.FUENTE_DE_PODER -> "Fuente de Poder";
                CategoriaComponente.GABINETE -> "Gabinete";
                CategoriaComponente.GPU -> "Tarjeta Gráfica";
                CategoriaComponente.MOTHERBOARD -> "Placa Madre";
            }
        }
    }

};