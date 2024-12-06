// CatalogoFragment.kt
package com.example.slapc.ui.catalogo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.databinding.FragmentCatalogoBinding
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente
import com.example.slapc.R
import com.example.slapc.RepositorioComponentes

class CatalogoFragment : Fragment() {

    private lateinit var productos: List<Componente> // Inicializar la lista de productos
    private lateinit var adapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
    private val activityWithResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult -> interpretarResultadoDeDetalles(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatalogoBinding.inflate(inflater, container, false)


        // Configurar RecyclerView
        recyclerView = binding.recyclerView

        // Se obtiene la lista más reciente de componentes directo del repositorio.
        productos = RepositorioComponentes.obtenerComponentes()

        adapter = ProductoAdapter(productos, activityWithResultLauncher) // Usa la lista inicializada vacía
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        // Configurar el SearchView
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

        // Configurar el Spinner
        spinner = binding.spinnerCategorias
        val categorias = listOf("Categoria") + CategoriaComponente.values().map { Componente.obtenerNombreDeCategoria(it) }
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
            productos.filter { Componente.obtenerNombreDeCategoria(it.categoria) == categoria }
        }
        adapter = ProductoAdapter(filteredList, activityWithResultLauncher)
        recyclerView.adapter = adapter
    }
    private fun filtrarProductos(query: String?) {
        val filteredList = productos.filter { it.nombre.contains(query ?: "", ignoreCase = true) }
        adapter = ProductoAdapter(filteredList, activityWithResultLauncher)
        recyclerView.adapter = adapter
    }

    private fun interpretarResultadoDeDetalles(resultado: ActivityResult) {
        if(resultado.resultCode == Activity.RESULT_OK) {
            val intent = resultado.data
            val agregadoAlCarrito = intent!!.getBooleanExtra("agregado_al_carrito", false)

            if (agregadoAlCarrito) {
                Toast.makeText(context, "Componente agregado al carrito", Toast.LENGTH_SHORT).show()
            }
            else {
                findNavController().navigate(R.id.nav_login)
                Toast.makeText(context, "Inicie sesión para usar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
