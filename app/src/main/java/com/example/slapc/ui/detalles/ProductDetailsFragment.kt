package com.example.slapc.ui.detalles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.slapc.R

import com.example.slapc.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        arguments?.let {
            val nombre = it.getString("nombre")
            val precio = it.getString("precio")
            val categoria = it.getString("categoria")
            val imagenResId = it.getInt("imagen")
            val detallesTecnicos = it.getString("detallesTecnicos")

            // Verifica que los valores no sean nulos y usa los datos para poblar la vista
            binding.tvNombre.text = nombre ?: "Nombre no disponible"
            binding.tvPrecio.text = precio ?: "Precio no disponible"
            binding.tvCategoria.text = categoria ?: "Categoría no disponible"
            binding.imgProducto.setImageResource(imagenResId)
            binding.tvDetallesTecnicos.text = detallesTecnicos ?: "Detalles técnicos no disponibles"
        } ?: run {
            Log.e("ProductDetailsFragment", "No se recibieron argumentos")
        }

        return binding.root
    }
}
