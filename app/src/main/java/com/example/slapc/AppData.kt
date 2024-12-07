package com.example.slapc

val usuarios = mutableListOf(
    Usuario(
        nombre = "admin",
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
        direccionEntrega = "CETI Colomos",
        horaInicio = "08:00",
        horaFin = "18:00",
        esAdmin = false
    ),
    Usuario(
        nombre = "Slappy",
        correo = "a22310380@ceti.mx",
        contraseña = "12345",
        direccionEntrega = "CETI Colomos",
        horaInicio = "08:00",
        horaFin = "18:00",
        esAdmin = false
    )
)