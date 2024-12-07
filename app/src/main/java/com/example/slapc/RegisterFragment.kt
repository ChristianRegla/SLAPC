package com.example.slapc

import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.Calendar
import java.util.Locale


class RegisterFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val emailEditText = view.findViewById<EditText>(R.id.email)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val addressEditText = view.findViewById<EditText>(R.id.address)
        val startTimeEditText = view.findViewById<EditText>(R.id.start_time)
        val endTimeEditText = view.findViewById<EditText>(R.id.end_time)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        startTimeEditText.setOnClickListener {
            showTimePicker { selectedHour, selectedMinute ->
                startTimeEditText.setText(
                    String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                )
            }
        }

        endTimeEditText.setOnClickListener {
            showTimePicker { selectedHour, selectedMinute ->
                endTimeEditText.setText(
                    String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                )
            }
        }

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val startTime = startTimeEditText.text.toString()
            val endTime = endTimeEditText.text.toString()

            val LIMITE_INICIO = 8 * 60 // Hora mínima a las 8 AM
            val LIMITE_FIN = 20 * 60 // Hora máxima a las 8 PM

            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!startTime.matches(Regex("\\d{2}:\\d{2}")) || !endTime.matches(Regex("\\d{2}:\\d{2}"))){
                Toast.makeText(requireContext(), "El formato de la hora debe ser HH:mm", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val startTimeParts = startTime.split(":").map { it.toInt() }
            val endTimeParts = endTime.split(":").map { it.toInt() }

            // Verificar que las partes sea numéricas y en rangos válidos
            if(startTimeParts.any { it == null } || endTimeParts.any { it == null } ||
                startTimeParts[0] !in 0..23 || startTimeParts[1] !in 0..59 ||
                endTimeParts[0] !in 0..23 || endTimeParts[1] !in 0..59
            ) {
                Toast.makeText(requireContext(), "El formato de la hora debe ser HH:mm", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir las horas de inicio y fin a minutos totales
            val startHour = startTimeParts[0] * 60 + startTimeParts[1] // Minutostotales
            val endHour = endTimeParts[0] * 60 + endTimeParts[1] // Minutostotales



            // Verificar que la hora de inicio sea menor que la hora de fin
            if(startHour in LIMITE_INICIO..LIMITE_FIN && endHour in LIMITE_INICIO..LIMITE_FIN){
                if(startHour >= endHour) {
                    Toast.makeText(requireContext(), "La hora de inicio debe ser menor que la hora de fin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else{
                Toast.makeText(requireContext(), "La hora de inicio debe estar entre las 8 AM y las 8 PM", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("RegisterFragment", "Hora de inicio: $startTime" + " " + "Hora de fin: $endTime")
            registrarUsuario(username, email, password, address, startTime, endTime)
        }
    }

    private fun registrarUsuario(
        nombre: String,
        correo: String,
        contraseña: String,
        direccionEntrega: String,
        horaInicio: String,
        horaFin: String
    ) {
        if(usuarios.any { it.correo == correo }) {
            Toast.makeText(requireContext(), "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
            return
        } else {
            val nuevoUsuario = Usuario(
                nombre = nombre,
                correo = correo,
                contraseña = contraseña,
                direccionEntrega = direccionEntrega,
                horaInicio = horaInicio,
                horaFin = horaFin,
                esAdmin = false
            )
            usuarios.add(nuevoUsuario)
            Toast.makeText(requireContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login)
        }
    }

    private fun showTimePicker(onTimeSelected: (Int, Int) -> Unit) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                onTimeSelected(selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )

        timePicker.setOnShowListener {
            val positiveButton = timePicker.getButton(TimePickerDialog.BUTTON_POSITIVE)
            val negativeButton = timePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE)

            positiveButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            negativeButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }

        timePicker.show()
    }
}