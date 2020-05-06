package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class ContactAdapter(val context: Context, items:ArrayList<Contact>):BaseAdapter() {
    var items:ArrayList<Contact>?= ArrayList(items)
    val copiaItems:ArrayList<Contact>?=items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder:ViewHolder
        var vista:View? = convertView

        if(vista==null){
            vista = LayoutInflater.from(context).inflate(R.layout.template_main,null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        }else{
            viewHolder = vista.tag as ViewHolder
        }

        val item = getItem(position) as Contact
        viewHolder.nombre.text = item.nombre.plus(" ").plus(item.apellidos)
        viewHolder.empresa.text = item.empresa
        viewHolder.foto.setImageResource(item.foto)

        return vista!!

    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    fun addItem(item:Contact){
        copiaItems?.add(item)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }
    fun updateItem(index: Int,newItem:Contact){
        copiaItems?.set(index,newItem)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun filtrar(str:String){
        items?.clear()
        if(str.isEmpty()){
            items = ArrayList(copiaItems!!)
            notifyDataSetChanged()
            return
        }
        var busqueda = str.toLowerCase()
        for(i in copiaItems!!){
            val nombre = i.nombre.toLowerCase()
            if(nombre.contains(busqueda)){
                items?.add(i)
            }
        }
        notifyDataSetChanged()
    }


    private class ViewHolder(view:View){
        val nombre:TextView = view.findViewById(R.id.tv_name_contact)
        val empresa:TextView = view.findViewById(R.id.tv_company_contact)
        val foto:ImageView = view.findViewById(R.id.iv_foto_contacto)
    }
}