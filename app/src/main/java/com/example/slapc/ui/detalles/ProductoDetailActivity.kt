package com.example.slapc.ui.detalles

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slapc.R
import com.example.slapc.Componente

class ProductoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detail)

        // Extraer datos del Intent
        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre")
        val reflimagen = intent.getStringExtra("reflimagen")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val categoria = intent.getStringExtra("categoria")
        val detallesTecnicos = intent.getStringExtra("detallesTecnicos")

        // Log para verificar los datos recibidos
        Log.d("ProductoDetailActivity", "ID: $id, Nombre: $nombre, Imagen: $reflimagen, Precio: $precio, Categoría: $categoria, Detalles: $detallesTecnicos")

        // Vista
        val imgProducto: ImageView = findViewById(R.id.imgProductoDetail)
        val tvNombre: TextView = findViewById(R.id.tvNombreDetail)
        val tvPrecio: TextView = findViewById(R.id.tvPrecioDetail)
        val tvCategoria: TextView = findViewById(R.id.tvCategoriaDetail)
        val tvDetalles: TextView = findViewById(R.id.tvDetallesDetail)

        // Configurar vista con datos
        if (reflimagen?.matches("\\d+".toRegex()) == true) {
            imgProducto.setImageResource(reflimagen.toInt())
        } else {
            Log.e("ProductoDetailActivity", "Referencia de imagen no válida: $reflimagen")
        }

        tvNombre.text = nombre ?: "Nombre no disponible"
        tvPrecio.text = "$$precio"
        tvCategoria.text = categoria
        tvDetalles.text = detallesTecnicos ?: "Detalles técnicos no disponibles"
    }
}
