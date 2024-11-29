package com.example.slapc

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ArmadosActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud_armados)

        categorySpinner = findViewById(R.id.categorySpinner)
        createButton = findViewById(R.id.saveButton)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        createButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()

            if (selectedCategory == "Categoria del armado") {
                Toast.makeText(this, "Debes seleccionar una categoría válida", Toast.LENGTH_SHORT).show()
            } else {
                // Lógica para crear el armado
                // Aquí puedes proceder con la creación del armado
            }
        }
    }
}