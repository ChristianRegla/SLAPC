package com.example.slapc.ui.detalles

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.slapc.R
import com.example.slapc.databinding.ActivityProductoDetailBinding

class ProductoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detail)

        val id = intent.getIntExtra("id", 0)
        val nombre = intent.getStringExtra("nombre")
        val reflimagen = intent.getStringExtra("reflimagen")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val categoria = intent.getStringExtra("categoria")
        val detallesTecnicos = intent.getStringExtra("detallesTecnicos")

        // Vista
        val imgProducto: ImageView = findViewById(R.id.imgProductoDetail)
        val tvNombre: TextView = findViewById(R.id.tvNombreDetail)
        val tvPrecio: TextView = findViewById(R.id.tvPrecioDetail)
        val tvCategoria: TextView = findViewById(R.id.tvCategoriaDetail)
        val tvDetalles: TextView = findViewById(R.id.tvDetallesDetail)

        if (reflimagen?.matches("\\d+".toRegex()) == true) {
            imgProducto.setImageResource(reflimagen.toInt())
        }
        tvNombre.text = nombre
        tvPrecio.text = "$$precio"
        tvCategoria.text = "$categoria"
        tvDetalles.text = detallesTecnicos
    }
}
