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
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R
import com.example.slapc.ui.detalles.ProductoDetailActivity
import java.net.HttpURLConnection
import java.net.URL

class ProductoAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

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

        if (producto.reflimagen is Int) {
            holder.imgProducto.setImageResource(producto.reflimagen)
        }
        holder.tvNombre.text = producto.nombre
        holder.tvDescription.text = producto.detallesTecnicos

        // Click
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductoDetailActivity::class.java)
            intent.putExtra("id", producto.id)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("reflimagen", producto.reflimagen.toString())
            intent.putExtra("precio", producto.precio)
            intent.putExtra("categoria", producto.categoria)
            intent.putExtra("detallesTecnicos", producto.detallesTecnicos)
            context.startActivity(intent)
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