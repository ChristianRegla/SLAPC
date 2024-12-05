package com.example.slapc.ui.catalogo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.Componente
import com.example.slapc.R
import com.example.slapc.ui.detalles.ProductoDetailActivity
import java.net.HttpURLConnection
import java.net.URL

class ProductoAdapter(
    private val productos: List<Componente>,
    private val activityWithResultLauncher: ActivityResultLauncher<Intent>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        // Si `reflimagen` es una URL (String), se carga la imagen de forma asincrónica.
        if (producto.refImagen.isNotEmpty()) {
            LoadImageTask(holder.imgProducto).execute(producto.refImagen)
        } else {
            // Si `reflimagen` está vacía o no es válida, se usa una imagen predeterminada.
            holder.imgProducto.setImageResource(R.drawable.ic_address)
        }

        holder.tvNombre.text = producto.nombre
        holder.tvDescription.text = producto.detallesTecnicos

        // Configuración del click listener
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
        inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
            val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
            val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescription)
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                imageView.setImageBitmap(it)
            }
        }
    }


    override fun getItemCount(): Int = productos.size

}
