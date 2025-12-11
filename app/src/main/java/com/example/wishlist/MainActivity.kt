package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wishlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WishlistAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val items: MutableList<Item> = ItemFetcher.getItems()
    private val wishlistAdapter = WishlistAdapter(items, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemRecyclerView = binding.itemRecyclerView
        itemRecyclerView.adapter = wishlistAdapter
        itemRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.nameEditText.setOnClickListener {
            binding.nameEditText.selectAll()
        }

        binding.urlEditText.setOnClickListener {
            binding.urlEditText.selectAll()
        }

        binding.submitButton.setOnClickListener {
            val inputName = binding.nameEditText.text.toString().trim()
            val inputUrl = binding.urlEditText.text.toString().trim()
            val priceText = binding.priceEditText.text.toString().trim()

            if (inputName.isBlank() || inputUrl.isBlank() || priceText.isBlank()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputPrice = priceText.toDoubleOrNull()
            if (inputPrice == null) {
                Toast.makeText(
                    this,
                    "Error: Price entered was not valid. Please enter a number.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.priceEditText.text.clear()
                return@setOnClickListener
            }

            val formattedPrice = String.format("%.2f", inputPrice).toDouble()
            val newItem = Item(inputName, formattedPrice, inputUrl)

            items.add(newItem)
            wishlistAdapter.notifyItemInserted(items.size - 1)

            binding.nameEditText.text.clear()
            binding.priceEditText.text.clear()
            binding.urlEditText.text.clear()
        }

        binding.submitButton.setOnLongClickListener {
            true
        }
    }

    override fun onItemClick(position: Int) {
        try {
            val url = items[position].url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Invalid URL: ${items[position].url}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLongItemClick(position: Int) {
        items.removeAt(position)
        wishlistAdapter.notifyItemRemoved(position)
    }
}
