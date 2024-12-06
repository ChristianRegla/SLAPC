package com.example.slapc

val usuarios = mutableListOf(
    Usuario(
        nombre = "Admin",
        correo = "admin@example.com",
        contraseña = "admin123",
        direccionEntrega = "N/A",
        horarioEntrega = "N/A",
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