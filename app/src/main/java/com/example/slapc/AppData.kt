package com.example.slapc

val usuarios = mutableListOf(
    Usuario(
        nombre = "Admin",
        correo = "admin@example.com",
        contraseña = "admin123",
        direccionEntrega = "N/A",
        horaInicio = "00:00",
        horaFin = "00:00",
        esAdmin = true
    ),
    Usuario(
        nombre = "usuario pruebas",
        correo = "test@example.com",
        contraseña = "12345",
        direccionEntrega = "laboratorio de pruebas",
        horarioEntrega = "24/7",
        esAdmin = false
    )
)