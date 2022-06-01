package com.example.pruebaapi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(EmpleadoEntity::class), version = 1)
abstract class EmpleadoDatabase :RoomDatabase(){

    abstract fun empleadoDao():EmpleadoDao
}