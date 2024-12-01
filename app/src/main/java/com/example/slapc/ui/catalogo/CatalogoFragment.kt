package com.example.slapc.ui.catalogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.slapc.databinding.FragmentCatalogoBinding
import com.example.slapc.ui.ProductoViewModel

class CatalogoFragment : Fragment() {
    private val viewModel: ProductoViewModel by activityViewModels()
    private lateinit var binding: FragmentCatalogoBinding
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogoBinding.inflate(inflater, container, false)

        // Configuración del RecyclerView
        adapter = ProductoAdapter(emptyList())
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter

        // Observa cambios en los productos
        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.actualizarProductos(productos)
        }

        // Configuración del Spinner
        val categorias = listOf("Categoria", "Gaming", "Oficina", "Domestico")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategorias.adapter = spinnerAdapter

        binding.spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = categorias[position]
                viewModel.filtrarPorCategoria(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Configuración del SearchView
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filtrarProductos(newText)
                return true
            }
        })

        return binding.root
    }
}
