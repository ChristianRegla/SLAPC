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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.databinding.FragmentCatalogoBinding
import com.example.slapc.CategoriaComponente
import com.example.slapc.Componente
import com.example.slapc.RepositorioComponentes

class CatalogoFragment : Fragment() {

    private lateinit var productos: List<Componente> // Inicializar la lista de productos
    private lateinit var adapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatalogoBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        recyclerView = binding.recyclerView

        // Se obtiene la lista más reciente de componentes directo del repositorio.
        productos = RepositorioComponentes.obtenerComponentes()
        // El repositorio ya hace la función de modelo de datos, por lo que usar un ViewModel que
        // guardara registros duplicados resultaba innecesario, además su uso principal no cuadraba en esta interacción;
        // los viewModel se usan más bien con ventanas que tienen datos que cambian dinámicamente acorde
        // a interacciones de la misma ventana, pero el view model que se quitó respondía a interacciones
        // de la ventana del CRUD; finalmente el observer estaba siempre asignando una lista vacía,
        // por lo que era una prueba más de cómo el usar view model en este caso estaba perjudicando
        // más que aportando a la interacción que se deseaba.
        //
        // Para el caso de los filtros por categoría, la funcionalidad que ya se vió que está debajo debería funcionar
        // correctamente sin necesitar un view model que pudiera reintroducir problemas.

        adapter = ProductoAdapter(productos) // Usa la lista inicializada vacía
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

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
        adapter = ProductoAdapter(filteredList)
        recyclerView.adapter = adapter
    }
}
