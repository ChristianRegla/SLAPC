package com.example.slapc

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.slapc.ui.ProductoViewModel
import com.example.slapc.ui.catalogo.Producto

class CRUDComponentesActivity : AppCompatActivity() {
    private val viewModel: ProductoViewModel by viewModels()
    private lateinit var edtImagen: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtPrecio: EditText
    private lateinit var edtDetalles: EditText
    private lateinit var spnCategoria: Spinner
    private lateinit var btnSalir: Button
    private lateinit var ibtnGuardar: ImageButton
    private lateinit var ibtnBuscar: ImageButton
    private lateinit var ibtnBorrar: ImageButton

    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private var categoriaSeleccionada: String = "CPU"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crudcomponentes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencia de componentes gráficos
        edtImagen = findViewById(R.id.edtCompImagen)
        edtNombre = findViewById(R.id.edtCompNombre)
        edtPrecio = findViewById(R.id.edtCompPrecio)
        edtDetalles = findViewById(R.id.edtCompDetalles)
        spnCategoria = findViewById(R.id.spnCompCategoria)
        btnSalir = findViewById(R.id.btnCompSalir)
        ibtnGuardar = findViewById(R.id.ibtnCompGuardar)
        ibtnBuscar = findViewById(R.id.ibtnCompBuscar)
        ibtnBorrar = findViewById(R.id.ibtnCompBorrar)

        // Configuración del spinner de categorías

        spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.categorias_componentes)
        )
        spnCategoria.adapter = spinnerAdapter
        spnCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                categoriaSeleccionada = parent!!.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // Configuración de botones de acción.
        btnSalir.setOnClickListener { volverAPanelDeAdmins() }
        ibtnGuardar.setOnClickListener { guardarComponente() }
        ibtnBuscar.setOnClickListener { buscarComponente() }
        ibtnBorrar.setOnClickListener { borrarComponente() }
    }

    private fun volverAPanelDeAdmins() {
        finish()
    }

    private fun guardarComponente() {
        if (edtNombre.text.isBlank() || edtImagen.text.isBlank() || edtPrecio.text.isBlank() || edtDetalles.text.isBlank()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoComponente = Producto(
            id = viewModel.productos.value?.size?.plus(1) ?: 1,
            nombre = edtNombre.text.toString(),
            reflimagen = edtImagen, // Cambiar por lógica real de imágenes
            precio = edtPrecio.text.toString().toDouble(),
            categoria = categoriaSeleccionada,
            detallesTecnicos = edtDetalles.text.toString()
        )

        viewModel.agregarProducto(nuevoComponente)
        Toast.makeText(this, "Componente agregado correctamente", Toast.LENGTH_SHORT).show()
    }
    private fun buscarComponente() {
        // Si no hay un nombre ingresado para buscar se muestra una advertencia.
        if (edtNombre.text.isEmpty() || edtNombre.text.isBlank()) {
            Toast.makeText(this, "Ingrese un nombre de componente para buscar", Toast.LENGTH_SHORT).show()
        }
        // Si hay un nombre, se avanza a la búsqueda.
        else {
            val componenteEncontrado = RepositorioComponentes.buscarComponente(edtNombre.text.toString())

            // Si no se encuentra coincidencia de nombre se indica.
            if(componenteEncontrado == null) {
                Toast.makeText(this, "No se encontró el componente '${edtNombre.text}'", Toast.LENGTH_SHORT).show()
            }
            // Si se encuentra una coincidencia se muestran los campos.
            else {
                edtImagen.setText(componenteEncontrado.refImagen)
                edtPrecio.setText("${componenteEncontrado.precio}")
                edtDetalles.setText(componenteEncontrado.detallesTecnicos)

                // El spinner se ajusta recuperando la posición de la categoria desde el adaptador.
                val posicionSeleccionada =
                    spinnerAdapter.getPosition(Componente.obtenerNombreDeCategoria(componenteEncontrado.categoria))
                spnCategoria.setSelection(posicionSeleccionada)

                Toast.makeText(this, "Componente encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun borrarComponente() {
        // Si no hay un nombre escrito se muestra una advertencia.
        if (edtNombre.text.isEmpty() || edtNombre.text.isBlank()) {
            Toast.makeText(this, "Ingrese un nombre de componente para borrar", Toast.LENGTH_SHORT).show()
        }
        // Si hay un nombre escrito se procede a averiguar si el componente existe.
        else {
            val componenteEncontrado = RepositorioComponentes.buscarComponente(edtNombre.text.toString())

            // Si el componente no existe se informa, en caso contrario se borra
            if(componenteEncontrado == null) {
                Toast.makeText(this, "No se encontró el componente '${edtNombre.text}' a borrar", Toast.LENGTH_SHORT).show()
            }
            else {
                RepositorioComponentes.quitarComponente(componenteEncontrado.id)
                Toast.makeText(this, "Componente '${componenteEncontrado.nombre}' borrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

}