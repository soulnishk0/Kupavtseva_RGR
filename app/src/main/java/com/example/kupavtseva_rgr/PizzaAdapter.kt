package com.example.kupavtseva_rgr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PizzaAdapter(private val pizzaList : ArrayList<Pizza>) : RecyclerView.Adapter<PizzaAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pizza_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = pizzaList[position]

        holder.name.text = currentitem.name
        holder.weight.text = currentitem.weight
        holder.price.text = currentitem.price
        holder.size.text = currentitem.size
        if (currentitem.image != null){
            val name = currentitem.image
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$name.jpg")
            val localFile = File.createTempFile("tmpFile", "jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                holder.profileImage.setImageURI(localFile.toUri())
            }
        }
    }

    override fun getItemCount(): Int {

        return pizzaList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tvname)
        val weight : TextView = itemView.findViewById(R.id.tvweight)
        val price : TextView = itemView.findViewById(R.id.tvprice)
        val size : TextView = itemView.findViewById(R.id.tvsize)
        val profileImage : ImageView = itemView.findViewById(R.id.tvimage)

    }

}