package com.example.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar

const val EXTRA_MESSAGE_ID = "ID"

class MainActivity : AppCompatActivity() {

    companion object{
        var contactAdapter:ContactAdapter?=null
        var arrayContact:ArrayList<Contact>? = null

        fun agregarContacto(contacto:Contact){
            contactAdapter?.addItem(contacto)
        }

        fun eliminarContacto(index:Int){
            contactAdapter?.removeItem(index)
        }

        fun obtenerContacto(index:Int):Contact = contactAdapter?.getItem(index) as Contact

        fun editarContacto(index:Int,contacto:Contact){
            contactAdapter?.updateItem(index,contacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)



        val listView = findViewById<ListView>(R.id.listView_Lista)
        arrayContact = ArrayList()
        arrayContact?.add(Contact("Luis","Inga Mendoza","ABCD", 23,100f,"ABCD@hotmail.com","Villa el Salvador","990098622",R.drawable.foto_01))
        arrayContact?.add(Contact("Alberto","Escobar","JAJA", 23,100f,"ABCD@hotmail.com","Villa el Salvador","990098622",R.drawable.foto_02))
        arrayContact?.add(Contact("Carlos","Manrique","JEJE", 23,100f,"ABCD@hotmail.com","Villa el Salvador","990098622",R.drawable.foto_03))
        arrayContact?.add(Contact("Ellie","Valenzuela","JIJI", 23,100f,"ABCD@hotmail.com","Villa el Salvador","990098622",R.drawable.foto_04))


        contactAdapter = ContactAdapter(this,arrayContact!!)
        listView.adapter = contactAdapter
        
        //ClickListener para cada elemento de lista
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,Detalles::class.java)
            //enviando id de contacto
            intent.putExtra(EXTRA_MESSAGE_ID,position.toString())
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        //Mostrar item buscar en toolbar
        val itemSearch = menu?.findItem(R.id.app_bar_search)
        val searchView = itemSearch?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Buscar contacto..."
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        //agregando metodos del searchView
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                contactAdapter?.filtrar(newText!!)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_add_contact->{
                val intent = Intent(this,Nuevo::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        contactAdapter?.notifyDataSetChanged()
    }

}
