// CatalogoFragment.kt
package com.example.slapc.ui.catalogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R
import com.example.slapc.databinding.FragmentCatalogoBinding
import com.example.slapc.CategoriaComponente

class CatalogoFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel
    private var productos: List<Producto> = emptyList() // Inicializar la lista de productos
    private lateinit var adapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatalogoBinding.inflate(inflater, container, false)

        // Inicializar ViewModel
        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        // Configurar RecyclerView
        recyclerView = binding.recyclerView
        adapter = ProductoAdapter(productos) // Usa la lista inicializada vacía
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        // Observar cambios en los productos
        productoViewModel.productos.observe(viewLifecycleOwner, Observer { listaProductos ->
            listaProductos?.let {
                productos = it // Asegura que la lista no sea null
                adapter = ProductoAdapter(productos)
                recyclerView.adapter = adapter
            }
        })

        // Configurar el Spinner
        spinner = binding.spinnerCategorias
        val categorias = listOf("Categoria") + CategoriaComponente.values().map { Producto.obtenerNombreDeCategoria(it) }
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = categorias[position]
                filtrarPorCategoria(categoriaSeleccionada)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        return binding.root
    }

    private fun filtrarPorCategoria(categoria: String) {
        val filteredList = if (categoria == "Categoria") {
            productos
        } else {
            productos.filter { Producto.obtenerNombreDeCategoria(it.categoria) == categoria }
        }
        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }
}
