package com.example.slapc.ui.catalogo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.R
import com.example.slapc.databinding.FragmentCatalogoBinding
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager

class CatalogoFragment : Fragment() {

    private lateinit var productos: List<Producto>
    private lateinit var adapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatalogoBinding.inflate(inflater, container, false)

        // Inicializar
        recyclerView = binding.recyclerView
        productos = obtenerProductos() // Función para obtener los productos
        adapter = ProductoAdapter(productos)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        // SearchView
        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarProductos(newText)
                return true
            }
        })

        //Spinner para categorías
        spinner = binding.spinnerCategorias
        val categorias = listOf("Categoria", "Gaming", "Oficina", "Domestico")
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

    private fun obtenerProductos(): List<Producto> {
        // Lista
        return listOf(
            Producto(1, "Ram DD4",R.drawable.ram,90.7, "Domestico","Increible y veloz Ram"),
            Producto(2, "Monitor LG",R.drawable.monitor,90.7, "Oficina","Último modelo de LG"),
            Producto(3, "Gabinete Gamer",R.drawable.gabinete,90.7, "Gaming","Último modelo de Gabinetes")
        )
    }

    private fun filtrarProductos(query: String?) {
        val filteredList = productos.filter { it.nombre.contains(query ?: "", ignoreCase = true) }
        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }

    private fun filtrarPorCategoria(categoria: String) {
        val filteredList = if (categoria == "Categoria") {
            productos
        } else {
            productos.filter { it.categoria == categoria }
        }
        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }
}
