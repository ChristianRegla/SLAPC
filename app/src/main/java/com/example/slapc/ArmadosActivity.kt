package com.example.slapc

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ArmadosActivity : AppCompatActivity() {

    private val categoriasValidas = listOf(
        CategoriaComponente.MOTHERBOARD,
        CategoriaComponente.FUENTE_DE_PODER,
        CategoriaComponente.RAM,
        CategoriaComponente.ALMACENAMIENTO,
        CategoriaComponente.CPU,
        CategoriaComponente.GABINETE,
        CategoriaComponente.GPU
    )

    private lateinit var spinnerMotherboard: Spinner
    private lateinit var spinnerPowerSupply: Spinner
    private lateinit var spinnerRam: Spinner
    private lateinit var spinnerStorage: Spinner
    private lateinit var spinnerProcessor: Spinner
    private lateinit var spinnerCabinet: Spinner
    private lateinit var spinnerGraphicsCard: Spinner
    private lateinit var discountEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var exitButton: Button
    private lateinit var categorySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud_armados)

        // Inicializar los views
        spinnerMotherboard = findViewById(R.id.spinnerMotherboard)
        spinnerPowerSupply = findViewById(R.id.spinnerPowerSupply)
        spinnerRam = findViewById(R.id.spinnerRam)
        spinnerStorage = findViewById(R.id.spinnerStorage)
        spinnerProcessor = findViewById(R.id.spinnerProcessor)
        spinnerCabinet = findViewById(R.id.spinnerCabinet)
        spinnerGraphicsCard = findViewById(R.id.spinnerGraphicsCard)
        discountEditText = findViewById(R.id.discountEditText)
        nameEditText = findViewById(R.id.nameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)
        searchButton = findViewById(R.id.searchButton)
        exitButton = findViewById(R.id.exitButton)

        // Llenar los Spinners con los componentes por categoría
        setupSpinner(spinnerMotherboard, CategoriaComponente.MOTHERBOARD)
        setupSpinner(spinnerPowerSupply, CategoriaComponente.FUENTE_DE_PODER)
        setupSpinner(spinnerRam, CategoriaComponente.RAM)
        setupSpinner(spinnerStorage, CategoriaComponente.ALMACENAMIENTO)
        setupSpinner(spinnerProcessor, CategoriaComponente.CPU)
        setupSpinner(spinnerCabinet, CategoriaComponente.GABINETE)
        setupSpinner(spinnerGraphicsCard, CategoriaComponente.GPU)

        // Botón para guardar el armado
        saveButton.setOnClickListener {
            saveArmado()
        }

        // Botón para borrar
        deleteButton.setOnClickListener {
            deleteArmado()
        }

        // Botón para buscar
        searchButton.setOnClickListener {
            searchArmado()
        }

        // Botón para salir
        exitButton.setOnClickListener {
            finish()
        }

        categorySpinner = findViewById(R.id.categorySpinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun setupSpinner(spinner: Spinner, categoria: CategoriaComponente) {
        if (categoria in categoriasValidas) {
            val componentNames = mutableListOf<String>()
            componentNames.add(when (categoria) {
                CategoriaComponente.MOTHERBOARD -> getString(R.string.select_motherboard)
                CategoriaComponente.FUENTE_DE_PODER -> getString(R.string.select_power_supply)
                CategoriaComponente.RAM -> getString(R.string.select_ram)
                CategoriaComponente.ALMACENAMIENTO -> getString(R.string.select_storage)
                CategoriaComponente.CPU -> getString(R.string.select_processor)
                CategoriaComponente.GABINETE -> getString(R.string.select_cabinet)
                CategoriaComponente.GPU -> getString(R.string.select_graphics_card)
                // No es necesario agregar ramas para MONITOR, MOUSE y TECLADO
                else -> throw IllegalArgumentException("Categoría de componente no reconocida")
            })
            componentNames.addAll(RepositorioComponentes.obtenerComponentes().filter { it.categoria == categoria }
                .map { it.nombre })

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, componentNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(0) // Seleccionar la primera opción ("Seleccione...") por defecto
        }
    }

    private fun saveArmado() {
        val selectedCategory = categorySpinner.selectedItem.toString()

        if (selectedCategory == getString(R.string.default_category_label)) {
            Toast.makeText(this, "Debes seleccionar una categoría de armado válida", Toast.LENGTH_SHORT).show()
            return
        }

        if (nameEditText.text.isBlank() || discountEditText.text.isBlank() || descriptionEditText.text.isBlank()) {
            Toast.makeText(this, "Debes llenar todos los datos", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedMotherboard = spinnerMotherboard.selectedItem as String
        val selectedPowerSupply = spinnerPowerSupply.selectedItem as String
        val selectedRam = spinnerRam.selectedItem as String
        val selectedStorage = spinnerStorage.selectedItem as String
        val selectedProcessor = spinnerProcessor.selectedItem as String
        val selectedCabinet = spinnerCabinet.selectedItem as String
        val selectedGraphicsCard = spinnerGraphicsCard.selectedItem as String

        val discount = discountEditText.text.toString().toDoubleOrNull() ?: 0.0

        // Validar que se hayan seleccionado componentes válidos (excepto la tarjeta gráfica)
        if (selectedMotherboard == getString(R.string.select_motherboard) ||
            selectedPowerSupply == getString(R.string.select_power_supply) ||
            selectedRam == getString(R.string.select_ram) ||
            selectedStorage == getString(R.string.select_storage) ||
            selectedProcessor == getString(R.string.select_processor) ||
            selectedCabinet == getString(R.string.select_cabinet)
        ) {
            Toast.makeText(this, "Debes seleccionar un componente válido en cada spinner", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear la lista de componentes, incluyendo la tarjeta gráfica solo si está seleccionada
        val componentes = mutableListOf(
            RepositorioComponentes.buscarComponente(selectedMotherboard)!!,
            RepositorioComponentes.buscarComponente(selectedPowerSupply)!!,
            RepositorioComponentes.buscarComponente(selectedRam)!!,
            RepositorioComponentes.buscarComponente(selectedStorage)!!,
            RepositorioComponentes.buscarComponente(selectedProcessor)!!,
            RepositorioComponentes.buscarComponente(selectedCabinet)!!
        )
        if (selectedGraphicsCard != getString(R.string.select_graphics_card)) {
            componentes.add(RepositorioComponentes.buscarComponente(selectedGraphicsCard)!!)
        }

        // Calcular el precio total de los componentes
        val totalPrice = componentes.sumOf { it.precio }

        // Calcular el precio final con el descuento aplicado
        val finalPrice = totalPrice - (totalPrice * (discount / 100))

        // Buscar el armado existente por su nombre
        val armadoExistente = RepositorioArmados.buscarArmadosPorNombre(nameEditText.text.toString()).firstOrNull()

        if (armadoExistente != null) {
            // Actualizar el armado existente
            armadoExistente.descuento = discount
            armadoExistente.categoria = selectedCategory
            armadoExistente.descripcion = descriptionEditText.text.toString()
            armadoExistente.componentes = componentes
            armadoExistente.precio = finalPrice

            if (RepositorioArmados.actualizarArmado(armadoExistente.id, armadoExistente)) {
                Toast.makeText(this, "Armado actualizado correctamente", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Precio final: $finalPrice", Toast.LENGTH_SHORT).show()
                clearForm()
            } else {
                Toast.makeText(this, "Error al actualizar el armado", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Crear un nuevo armado
            val armado = Armado(
                id = generateArmadoId(),
                nombre = nameEditText.text.toString(),
                descuento = discount,
                categoria = selectedCategory,
                descripcion = descriptionEditText.text.toString(),
                componentes = componentes,
                precio = finalPrice
            )

            if (RepositorioArmados.agregarArmado(armado)) {
                Toast.makeText(this, "Armado guardado correctamente", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Precio final: $finalPrice", Toast.LENGTH_SHORT).show()
                clearForm()
            } else {
                Toast.makeText(this, "El armado ya existe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearForm() {
        nameEditText.text.clear()
        discountEditText.text.clear()
        descriptionEditText.text.clear()
        spinnerMotherboard.setSelection(0)
        spinnerPowerSupply.setSelection(0)
        spinnerRam.setSelection(0)
        spinnerStorage.setSelection(0)
        spinnerProcessor.setSelection(0)
        spinnerCabinet.setSelection(0)
        spinnerGraphicsCard.setSelection(0)
        categorySpinner.setSelection(0)
    }

    private fun searchArmado() {
        val nameToSearch = nameEditText.text.toString().trim()

        if (nameToSearch.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre para buscar", Toast.LENGTH_SHORT).show()
            return
        }

        val foundArmados = searchArmadoInDatabase(nameToSearch)

        if (foundArmados.isNotEmpty()) {
            // Si encontramos armados, los mostramos en el primer resultado encontrado
            val firstArmado = foundArmados.first()

            // Rellenar el formulario con los datos del primer armado encontrado
            nameEditText.setText(firstArmado.nombre)
            discountEditText.setText(String.format(Locale.getDefault(), "%.2f", firstArmado.descuento))
            descriptionEditText.setText(firstArmado.descripcion)

            // Llenar los spinners con los componentes seleccionados
            setSpinnerSelection(spinnerMotherboard, firstArmado.componentes)
            setSpinnerSelection(spinnerPowerSupply, firstArmado.componentes)
            setSpinnerSelection(spinnerRam, firstArmado.componentes)
            setSpinnerSelection(spinnerStorage, firstArmado.componentes)
            setSpinnerSelection(spinnerProcessor, firstArmado.componentes)
            setSpinnerSelection(spinnerCabinet, firstArmado.componentes)
            setSpinnerSelection(spinnerGraphicsCard, firstArmado.componentes)

            for (i in 0 until categorySpinner.count) {
                if (categorySpinner.getItemAtPosition(i).toString() == firstArmado.categoria) {
                    categorySpinner.setSelection(i)
                    break
                }
            }

            Toast.makeText(this, "Armado encontrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Armado no encontrado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun searchArmadoInDatabase(name: String): List<Armado> {
        // Usamos el repositorio para buscar los armados que contengan el nombre dado
        return RepositorioArmados.buscarArmadosPorNombre(name)
    }

    private fun setSpinnerSelection(spinner: Spinner, componentes: List<Componente>) {
        val componentName = componentes.find { it.categoria == getComponentCategory(spinner) }?.nombre
        if (componentName != null) {
            for (i in 0 until spinner.count) {
                if (spinner.getItemAtPosition(i).toString() == componentName) {
                    Log.d("SpinnerSelection", "Seleccionando componente: $componentName en posición: $i")
                    spinner.setSelection(i)
                    break
                }
            }
        }
    }

    private fun getComponentCategory(spinner: Spinner): CategoriaComponente {
        return when (spinner.id) {
            R.id.spinnerMotherboard -> CategoriaComponente.MOTHERBOARD
            R.id.spinnerPowerSupply -> CategoriaComponente.FUENTE_DE_PODER
            R.id.spinnerRam -> CategoriaComponente.RAM
            R.id.spinnerStorage -> CategoriaComponente.ALMACENAMIENTO
            R.id.spinnerProcessor -> CategoriaComponente.CPU
            R.id.spinnerCabinet -> CategoriaComponente.GABINETE
            R.id.spinnerGraphicsCard -> CategoriaComponente.GPU
            else -> throw IllegalArgumentException("Spinner no reconocido")
        }
    }

    private fun deleteArmado() {
        val nameToDelete = nameEditText.text.toString().trim()

        if (nameToDelete.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val armado = RepositorioArmados.buscarArmadosPorNombre(nameToDelete).firstOrNull()
        if (armado != null && RepositorioArmados.quitarArmado(armado.id)) {
            Toast.makeText(this, "Armado eliminado correctamente", Toast.LENGTH_SHORT).show()
            clearForm()
        } else {
            Toast.makeText(this, "Armado no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateArmadoId(): Int {
        return (1..1000).random()
    }
}