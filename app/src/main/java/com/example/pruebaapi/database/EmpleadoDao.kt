package com.example.pruebaapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM EmpleadoEntity")
    fun getAllEmpleado() : MutableList<EmpleadoEntity>

    @Insert
    fun addEmpleado(empleadoEntity: EmpleadoEntity)

    @Delete
    fun deleteEmpleado(empleadoEntity: EmpleadoEntity)
}