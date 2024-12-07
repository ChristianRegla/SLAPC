package com.example.slapc.ui.catalogo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.Componente
import com.example.slapc.R
import com.example.slapc.ui.detalles.ProductoDetailActivity
import java.net.HttpURLConnection
import java.net.URL

class ProductoAdapter( private val productos: List<Componente>,
                       private val activityWithResultLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        // Cargar la imagen desde la URL o desde recursos `drawable`
        if (producto.refImagen.isNotEmpty()) {
            if (producto.refImagen.startsWith("http") || producto.refImagen.startsWith("https")) {
                // Cargar imagen desde URL
                LoadImageTask(holder.imgProducto).execute(producto.refImagen)
            } else {
                // Cargar imagen desde los recursos `drawable` con el nombre proporcionado
                val imageResId = obtenerIdImagen(producto.refImagen, holder.itemView.context)
                if (imageResId != 0) {
                    holder.imgProducto.setImageResource(imageResId)
                } else {
                    // Si no se encuentra la imagen, usar una imagen predeterminada
                    holder.imgProducto.setImageResource(R.drawable.full_logo)
                }
            }
        } else {
            // Imagen predeterminada si `refImagen` está vacía
            holder.imgProducto.setImageResource(R.drawable.full_logo)
        }

        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "$${producto.precio}"

        // Configuración del click listener para abrir la actividad de detalle del producto
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductoDetailActivity::class.java)
            intent.putExtra("id", producto.id)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("reflimagen", producto.refImagen)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("categoria", Componente.obtenerNombreDeCategoria(producto.categoria))
            intent.putExtra("detallesTecnicos", producto.detallesTecnicos)
            activityWithResultLauncher.launch(intent)
        }
    }

    private fun obtenerIdImagen(referencia: String, context: Context): Int {
        return context.resources.getIdentifier(referencia, "drawable", context.packageName)
    }


    private class LoadImageTask(val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
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
            result?.let {
                imageView.setImageBitmap(it)
            } ?: run {
                imageView.setImageResource(R.drawable.full_logo)
            }
        }
    }

    override fun getItemCount(): Int = productos.size
}
