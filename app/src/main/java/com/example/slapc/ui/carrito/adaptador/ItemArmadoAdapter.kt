package com.example.slapc.ui.carrito.adaptador

import android.content.Context
import android.graphics.Bitmap
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
import java.net.HttpURLConnection
import java.net.URL

class ItemArmadoAdapter( private val productos: List<Componente>) : RecyclerView.Adapter<ItemArmadoAdapter.ItemArmadoViewHolder>() {

    inner class ItemArmadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgComponente: ImageView = itemView.findViewById(R.id.imgComponente)
        val tvNombreComponente: TextView = itemView.findViewById(R.id.tvNombreComponente)
        val tvCategoriaComponente: TextView = itemView.findViewById(R.id.tvCategoriaComponente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArmadoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_componente_armado, parent, false)
        return ItemArmadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemArmadoViewHolder, position: Int) {
        val producto = productos[position]

        if (producto.refImagen.isNotEmpty()) {
            if (producto.refImagen.startsWith("http") || producto.refImagen.startsWith("https")) {
                LoadImageTask(holder.imgComponente).execute(producto.refImagen)
            } else {
                val imageResId = obtenerIdImagen(producto.refImagen, holder.itemView.context)
                if (imageResId != 0) {
                    holder.imgComponente.setImageResource(imageResId)
                } else {
                    holder.imgComponente.setImageResource(R.drawable.full_logo)
                }
            }
        } else {
            holder.imgComponente.setImageResource(R.drawable.full_logo)
        }

        holder.tvNombreComponente.text = producto.nombre
        holder.tvCategoriaComponente.text = Componente.obtenerNombreDeCategoria(producto.categoria)
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