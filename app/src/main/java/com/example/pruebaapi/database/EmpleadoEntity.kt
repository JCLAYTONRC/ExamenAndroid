package com.example.pruebaapi.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmpleadoEntity")
data class EmpleadoEntity(@PrimaryKey(autoGenerate = true) var id: Long =0,
                          var nombre: String,
                          var NumeroEmpleado: String,
                          var telefono:String,
                          var email:String,
                          var direccion: String)
