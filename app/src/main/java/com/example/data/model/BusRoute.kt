package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_routes")
data class BusRoute(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val origen: String,
    val destino: String,
    val fuente: String, // formal, informal, linea, salida
    val salida: String,
    val llegada: String,
    val operador: String,
    val cooperativa: String,
    val tipo: String,
    val capacidad: String,
    val placa: String,
    val clasificacion: String,
    val modalidad: String,
    val dias: String,
    val tiempo: String,
    val terminal: String,
    val contacto: String,
    val precio: Int? = null,
    val permiso: String = "",
    val obs: String = "",
    val territorio: String = ""
)
