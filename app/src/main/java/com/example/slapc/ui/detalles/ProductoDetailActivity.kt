package com.example.slapc.ui.detalles

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.slapc.ui.carrito.Carrito
import com.example.slapc.ui.carrito.ComponenteEnCarrito
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slapc.R
import java.net.HttpURLConnection
import java.net.URL

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
        val fabAddToCart: FloatingActionButton = findViewById(R.id.fabAddToCart)

        // Log para verificar los datos recibidos
        Log.d(
            "ProductoDetailActivity",
            "ID: $id, Nombre: $nombre, Imagen: $reflimagen, Precio: $precio, Categoría: $categoria, Detalles: $detallesTecnicos"
        )

        // Vista
        val imgProducto: ImageView = findViewById(R.id.imgProductoDetail)
        val tvNombre: TextView = findViewById(R.id.tvNombreDetail)
        val tvPrecio: TextView = findViewById(R.id.tvPrecioDetail)
        val tvCategoria: TextView = findViewById(R.id.tvCategoriaDetail)
        val tvDetalles: TextView = findViewById(R.id.tvDetallesDetail)

        // Configurar vista con datos
        if (reflimagen != null) {
            if (reflimagen.startsWith("http://") || reflimagen.startsWith("https://")) {
                // Cargar imagen desde URL
                LoadImageTask(imgProducto).execute(reflimagen)
            } else {
                // Intentar cargar desde recursos del drawable
                val resId = resources.getIdentifier(reflimagen, "drawable", packageName)
                if (resId != 0) {
                    imgProducto.setImageResource(resId)
                } else {
                    Log.e("ProductoDetailActivity", "Referencia de imagen no válida: $reflimagen")
                    imgProducto.setImageResource(R.drawable.full_logo) // Imagen predeterminada
                }
            }
        } else {
            imgProducto.setImageResource(R.drawable.full_logo) // Imagen predeterminada
        }

        tvNombre.text = nombre ?: "Nombre no disponible"
        tvPrecio.text = "$$precio"
        tvCategoria.text = categoria ?: "Categoría no disponible"
        tvDetalles.text = detallesTecnicos ?: "Detalles técnicos no disponibles"

        fabAddToCart.setOnClickListener { validarSesionParaCompra(id) }
    }

    private fun validarSesionParaCompra(idComponente: Int) {
        val sharedPreferences = getSharedPreferences("slapc.prefs", Context.MODE_PRIVATE)
        val sesionIniciada = sharedPreferences.getBoolean("sesion_iniciada", false)

        if (sesionIniciada) {
            Carrito.agregarItem(ComponenteEnCarrito(idComponente, 1))
        }

        val intent = Intent()
        intent.putExtra("agregado_al_carrito", sesionIniciada)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private class LoadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            return try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.inputStream.use {
                    BitmapFactory.decodeStream(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            } else {
                imageView.setImageResource(R.drawable.full_logo) // Imagen predeterminada en caso de error
            }
        }
    }
}
