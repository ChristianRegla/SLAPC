package com.example.slapc

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slapc.databinding.FragmentCatalogoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CatalogoArmadosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatalogoArmadosFragment : Fragment() {

    private lateinit var armados: List<Armado>
    private lateinit var adapter: ArmadoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private val activityWithResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult -> interpretarResultadoDeDetalles(result)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatalogoBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView

        armados = RepositorioArmados.obtenerTodosLosArmados()

        adapter = ArmadoAdapter(armados, activityWithResultLauncher)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter

        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarArmados(newText)
                return true
            }
        })

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
            armados
        } else {
            armados.filter { it.categoria == categoria }
        }
        adapter = ArmadoAdapter(filteredList, activityWithResultLauncher)
        recyclerView.adapter = adapter
    }
    private fun filtrarArmados(query: String?) {
        val filteredList = armados.filter { it.nombre.contains(query ?: "", ignoreCase = true) }
        adapter = ArmadoAdapter(filteredList, activityWithResultLauncher)
        recyclerView.adapter = adapter
    }

    private fun interpretarResultadoDeDetalles(resultado: ActivityResult) {
        if(resultado.resultCode == Activity.RESULT_OK) {
            val intent = resultado.data
            val agregadoAlCarrito = intent!!.getBooleanExtra("agregado_al_carrito", false)
            if (agregadoAlCarrito) {
                Toast.makeText(context, "Armado agregado al carrito", Toast.LENGTH_SHORT).show()
            }
            else {
                findNavController().navigate(R.id.nav_login)
                Toast.makeText(context, "Inicie sesión para usar el carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}