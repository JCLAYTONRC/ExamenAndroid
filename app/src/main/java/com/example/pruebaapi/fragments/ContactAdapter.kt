package com.example.pruebaapi.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaapi.R
import com.example.pruebaapi.database.EmpleadoEntity
import com.example.pruebaapi.databinding.ContactsItemBinding

class ContactAdapter(private var empleados:MutableList<EmpleadoEntity>): RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ContactsItemBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.contacts_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val empleado = empleados.get(position)

        with(holder){
            binding.tvname.text =empleado.nombre
        }

    }


    override fun getItemCount(): Int = empleados.size
    fun setEmpleado(empleados: MutableList<EmpleadoEntity>) {
        this.empleados = empleados
        notifyDataSetChanged()

    }
}