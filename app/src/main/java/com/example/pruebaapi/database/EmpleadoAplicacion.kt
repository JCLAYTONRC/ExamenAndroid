package com.example.pruebaapi.database

import android.app.Application
import androidx.room.Room

class EmpleadoAplicacion: Application() {
    companion object{
        lateinit var database: EmpleadoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            EmpleadoDatabase::class.java,
            "EmpleadoDatabase").build()
    }
}