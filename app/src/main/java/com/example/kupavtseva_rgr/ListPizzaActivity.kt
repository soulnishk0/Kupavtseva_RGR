package com.example.kupavtseva_rgr


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kupavtseva_rgr.databinding.ActivityAddPizzaBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class ListPizzaActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var pizzaRecyclerview : RecyclerView
    private lateinit var pizzaArrayList : ArrayList<Pizza>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pizza)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        pizzaRecyclerview = findViewById<View>(R.id.list) as RecyclerView
        pizzaRecyclerview.layoutManager = layoutManager
        pizzaArrayList = arrayListOf<Pizza>()
        getUserData()
        var addPizza = findViewById<FloatingActionButton>(R.id.add_btn)
        addPizza.setOnClickListener {
            val intent = Intent(this, AddPizzaActivity::class.java)
            startActivity(intent)
        }


    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Pizza")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val pizza = userSnapshot.getValue(Pizza::class.java)
                        pizzaArrayList.add(pizza!!)
                    }
                    pizzaRecyclerview.adapter = PizzaAdapter(pizzaArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}