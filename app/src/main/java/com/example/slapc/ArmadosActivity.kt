package com.example.slapc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ArmadosActivity : AppCompatActivity() {

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
        // Obtener componentes de la categoría específica desde el repositorio
        val componentNames = RepositorioComponentes.obtenerComponentes().filter { it.categoria == categoria }
            .map { it.nombre }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, componentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun saveArmado() {
        val selectedCategory = categorySpinner.selectedItem.toString()

        if (selectedCategory == getString(R.string.default_category_label)) {
            Toast.makeText(this, "Debes seleccionar una categoría de armado válida", Toast.LENGTH_SHORT).show()
        }
        else if(
            nameEditText.text.isEmpty() || nameEditText.text.isBlank() ||
            discountEditText.text.isEmpty() || discountEditText.text.isBlank() ||
            descriptionEditText.text.isEmpty() || descriptionEditText.text.isBlank()
        ){
            Toast.makeText(this, "Debes llenar todos los datos", Toast.LENGTH_SHORT).show()
        }
        else {

            val selectedMotherboard = spinnerMotherboard.selectedItem as String
            val selectedPowerSupply = spinnerPowerSupply.selectedItem as String
            val selectedRam = spinnerRam.selectedItem as String
            val selectedStorage = spinnerStorage.selectedItem as String
            val selectedProcessor = spinnerProcessor.selectedItem as String
            val selectedCabinet = spinnerCabinet.selectedItem as String
            val selectedGraphicsCard = spinnerGraphicsCard.selectedItem as String

            val discount = discountEditText.text.toString().toDoubleOrNull() ?: 0.0

            // Obtener precios de los componentes seleccionados
            val totalPrice = calculateTotalPrice(
                selectedMotherboard, selectedPowerSupply, selectedRam,
                selectedStorage, selectedProcessor, selectedCabinet,
                selectedGraphicsCard
            )

            val finalPrice = totalPrice - (totalPrice * (discount / 100))

            // Crear el objeto Armado
            val armado = Armado(
                id = generateArmadoId(),  // Genera un ID único
                nombre = nameEditText.text.toString(),
                descuento = discount,
                categoria = "Gaming",  // Por ejemplo
                descripcion = descriptionEditText.text.toString(),
                componentes = listOf(), // Deberías asignar los objetos correspondientes de Componente
                precio = finalPrice
            )

            // Guardar el armado (por ejemplo, en una lista o base de datos)
            Toast.makeText(this, "Armado guardado correctamente", Toast.LENGTH_SHORT).show()

            // Limpiar el formulario después de guardar
            clearForm()
            Toast.makeText(this, "Total Calculado: $finalPrice", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateTotalPrice(vararg componentNames: String): Double {
        var total = 0.0
        for (componentName in componentNames) {
            val component = RepositorioComponentes.obtenerComponentes().find { it.nombre == componentName }
            total += component?.precio ?: 0.0
        }
        return total
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
            discountEditText.setText(firstArmado.descuento.toString())
            descriptionEditText.setText(firstArmado.descripcion)

            // Llenar los spinners con los componentes seleccionados
            setSpinnerSelection(spinnerMotherboard, firstArmado.componentes)
            setSpinnerSelection(spinnerPowerSupply, firstArmado.componentes)
            setSpinnerSelection(spinnerRam, firstArmado.componentes)
            setSpinnerSelection(spinnerStorage, firstArmado.componentes)
            setSpinnerSelection(spinnerProcessor, firstArmado.componentes)
            setSpinnerSelection(spinnerCabinet, firstArmado.componentes)
            setSpinnerSelection(spinnerGraphicsCard, firstArmado.componentes)

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
        // Establece el valor del Spinner basándote en los componentes
        val selectedComponent = componentes.find { it.nombre == spinner.selectedItem }
        spinner.setSelection(componentes.indexOf(selectedComponent))
    }

    private fun deleteArmado() {
        // Aquí va la lógica para borrar el armado
    }

    private fun generateArmadoId(): Int {
        return (1..1000).random()  // Solo un ejemplo de generación de ID
    }
}
