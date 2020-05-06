package com.example.contactos

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class Nuevo : AppCompatActivity() {

    //Para nuevo contacto
    var iv_new_photo:ImageView?=null
    var indexFoto:Int =0
    var arrayFotos = arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)

    //Para editar contacto
    var id_edit_contact = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        //Seleccionar foto
        iv_new_photo = findViewById<ImageView>(R.id.iv_new_photo)
        iv_new_photo?.setOnClickListener {
            seleccionarFoto()
        }

        //toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        //item regresar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //reconocer que viene del editar y no de el item nuevo
        if(intent.hasExtra(EXTRA_MESSAGE_ID)){
            id_edit_contact = intent.getStringExtra(EXTRA_MESSAGE_ID).toInt()
            rellenarDatos()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
            R.id.item_save_contact->{
            //Creando varaibles para guardar el nuevo elemento
                val nombre = findViewById<EditText>(R.id.et_new_name)
                val apellidos = findViewById<EditText>(R.id.et_new_surname)
                val empresa = findViewById<EditText>(R.id.et_new_company)
                val edad = findViewById<EditText>(R.id.et_new_age)
                val peso = findViewById<EditText>(R.id.et_new_weight)
                val telefono = findViewById<EditText>(R.id.et_new_phone)
                val direccion = findViewById<EditText>(R.id.et_new_address)
                val correo = findViewById<EditText>(R.id.et_new_email)

                //Validando campos vacios
                val campo = ArrayList<String>()
                campo.add(nombre.text.toString())
                campo.add(apellidos.text.toString())
                campo.add(empresa.text.toString())
                campo.add(edad.text.toString())
                campo.add(peso.text.toString())
                campo.add(correo.text.toString())
                campo.add(direccion.text.toString())
                campo.add(telefono.text.toString())


                var flag:Int = 0
                for(i in campo){
                    if(i.isNullOrEmpty()){
                        flag++
                    }
                }
                if(flag>0){
                    Toast.makeText(this,"Debe rellenar todos los campos",Toast.LENGTH_SHORT).show()
                }else{
                    if (id_edit_contact>-1){
                        MainActivity.editarContacto(id_edit_contact,Contact(nombre.text.toString(),apellidos.text.toString(),empresa.text.toString(),edad.text.toString().toInt(),peso.text.toString().toFloat(),correo.text.toString(),direccion.text.toString(),telefono.text.toString(),obtenerFoto(indexFoto!!))
                        )
                    }else{
                        MainActivity.agregarContacto(Contact(nombre.text.toString(),apellidos.text.toString(),empresa.text.toString(),edad.text.toString().toInt(),peso.text.toString().toFloat(),correo.text.toString(),direccion.text.toString(),telefono.text.toString(),obtenerFoto(indexFoto!!)))
                    }

                }


                //Log.d("Nro Agregados",MainActivity.arrayContact?.count().toString())  //para comprobar en el logcat que esta agregando
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun seleccionarFoto(){
        val builder = AlertDialog.Builder(this)

        val alertDialog = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1)
        alertDialog.add("Foto 01")
        alertDialog.add("Foto 02")
        alertDialog.add("Foto 03")
        alertDialog.add("Foto 04")
        alertDialog.add("Foto 05")
        alertDialog.add("Foto 06")



        builder.setTitle("Selecciona una foto")
        builder.setAdapter(alertDialog){
            dialog, which ->
            indexFoto = which
            iv_new_photo?.setImageResource(obtenerFoto(indexFoto!!))
        }

        builder.setNegativeButton("Cancelar"){
            dialog, which -> dialog.dismiss()
        }

        builder.show()
    }
    fun obtenerFoto(index:Int):Int = arrayFotos.get(index)

    fun rellenarDatos(){
        val edit_contacto = MainActivity.obtenerContacto(id_edit_contact)

        val nombre = findViewById<EditText>(R.id.et_new_name)
        val apellidos = findViewById<EditText>(R.id.et_new_surname)
        val empresa = findViewById<EditText>(R.id.et_new_company)
        val edad = findViewById<EditText>(R.id.et_new_age)
        val peso = findViewById<EditText>(R.id.et_new_weight)
        val telefono = findViewById<EditText>(R.id.et_new_phone)
        val direccion = findViewById<EditText>(R.id.et_new_address)
        val correo = findViewById<EditText>(R.id.et_new_email)
        val iv_foto = findViewById<ImageView>(R.id.iv_new_photo)

        nombre?.setText(edit_contacto.nombre,TextView.BufferType.EDITABLE)
        apellidos?.setText(edit_contacto.apellidos,TextView.BufferType.EDITABLE)
        empresa?.setText(edit_contacto.empresa,TextView.BufferType.EDITABLE)
        edad?.setText(edit_contacto.edad.toString(),TextView.BufferType.EDITABLE)
        peso?.setText(edit_contacto.peso.toString(),TextView.BufferType.EDITABLE)
        telefono?.setText(edit_contacto.telefono,TextView.BufferType.EDITABLE)
        direccion?.setText(edit_contacto.direccion,TextView.BufferType.EDITABLE)
        correo?.setText(edit_contacto.correo,TextView.BufferType.EDITABLE)
        iv_foto?.setImageResource(edit_contacto.foto)

        for(i in arrayFotos){
            if(edit_contacto.foto == i){
                indexFoto = i
            }
        }

    }
}
