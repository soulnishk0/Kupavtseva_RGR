package com.example.kupavtseva_rgr


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kupavtseva_rgr.databinding.ActivityAddPizzaBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class AddPizzaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPizzaBinding
    private lateinit var database: DatabaseReference
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPizzaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spinnerSize = findViewById<Spinner>(R.id.spinner_size)
        val adapter =
            ArrayAdapter.createFromResource(this, R.array.pizza_sizes, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerSize.adapter = adapter;
        binding.cancel.setOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 100)

        }

        binding.save.setOnClickListener {
            val name = binding.editName.text.toString()
            val price = binding.editPrice.text.toString()
            val weight = binding.editWeight.text.toString()
            val size = binding.spinnerSize.selectedItem.toString()
            val newPizza = if (this::imageUri.isInitialized){
                val fileName = UUID.randomUUID().toString()
                val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName.jpg")
                storageReference.putFile(imageUri)
                Pizza(name,price,weight,size,fileName)
            } else{
                Pizza(name,price,weight,size)
            }
            println(newPizza)



            database = FirebaseDatabase.getInstance().getReference("Pizza")
            database.push().setValue(newPizza).addOnSuccessListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListPizzaActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.imageView2.setImageURI(imageUri)
        }
    }
}