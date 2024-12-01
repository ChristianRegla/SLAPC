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
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente

class CatalogoFragment : Fragment() {

    private lateinit var productos: List<Componente>
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
        val categorias = resources.getStringArray(R.array.categorias_componentes)
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = categorias[position].toString()
                filtrarPorCategoria(categoriaSeleccionada)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        return binding.root
    }

    private fun obtenerProductos(): List<Componente> {
        // Lista
        return listOf(
            Componente("Ram DD4","R.drawable.ram,90.7, "RAM","Increible y veloz Ram"),
            Componente(2, "Monitor LG",R.drawable.monitor,90.7, "Monitor","Último modelo de LG"),
            Componente(3, "Gabinete Gamer",R.drawable.gabinete,90.7, "Gabinete","Último modelo de Gabinetes")
        ) as List<Componente>
    }

    private fun filtrarProductos(query: String?) {
        val filteredList = productos.filter { it.nombre.contains(query ?: "", ignoreCase = true) }
        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }

    private fun filtrarPorCategoria(categoria: String) {
        val categoriaEnum = stringToCategoria(categoria)

        val filteredList = if (categoria == "Todos" || categoriaEnum == null) {
            productos
        } else {
            productos.filter { it.categoria == categoriaEnum }
        }

        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }
    private fun stringToCategoria(categoria: String): CategoriaComponente? {
        return CategoriaComponente.values().firstOrNull { it.name.equals(categoria, ignoreCase = true) }
    }


}
