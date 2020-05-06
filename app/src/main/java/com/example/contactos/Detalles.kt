package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Detalles : AppCompatActivity() {

    var idContact:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        //toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        //item regresar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //recibiendo id de contacto
        idContact = intent.getStringExtra(EXTRA_MESSAGE_ID).toInt()
        mapearDatos()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalles,menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun mapearDatos(){

        val contacto = MainActivity.obtenerContacto(idContact)

        val nombre = findViewById<TextView>(R.id.et_new_name)
        val apellidos = findViewById<TextView>(R.id.et_new_surname)
        val empresa = findViewById<TextView>(R.id.et_new_company)
        val edad = findViewById<TextView>(R.id.et_new_age)
        val peso = findViewById<TextView>(R.id.et_new_weight)
        val telefono = findViewById<TextView>(R.id.et_new_phone)
        val direccion = findViewById<TextView>(R.id.et_new_address)
        val correo = findViewById<TextView>(R.id.et_new_email)
        val iv_foto = findViewById<ImageView>(R.id.iv_new_photo)

        nombre?.text = contacto.nombre
        apellidos?.text = contacto.apellidos
        empresa?.text = contacto.empresa
        edad?.text = contacto.edad.toString()
        peso?.text = contacto.peso.toString()
        telefono?.text = contacto.telefono
        direccion?.text = contacto.direccion
        correo?.text = contacto.correo
        iv_foto?.setImageResource(contacto.foto)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
            R.id.item_edit_contact->{
                val intent = Intent(this,Nuevo::class.java)
                intent.putExtra(EXTRA_MESSAGE_ID,idContact.toString())
                startActivity(intent)
                return true
            }
            R.id.item_delete_contact->{
                MainActivity.eliminarContacto(idContact)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}
