package com.example.budgetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.example.budgetapp.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var binding: ActivityAddTransactionBinding // View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database instance
        db = Room.databaseBuilder(this,
            AppDatabase::class.java, "transactions").build()

        // Add text change listeners
        binding.labelInput.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                binding.labelLayout.error = null
            }
        }

        binding.amountInput.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                binding.amountLayout.error = null
            }
        }

        // Add transaction button click listener
        binding.addTransactionBtn.setOnClickListener {
            val label = binding.labelInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()

            if (label.isEmpty()) {
                binding.labelLayout.error = "Please enter a valid label"
            } else if (amount == null) {
                binding.amountLayout.error = "Please enter a valid amount"
            } else {
                val transaction = Transaction(0, label, amount, description)
                insert(transaction)
            }
        }

        // Close button click listener
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    // Insert transaction into the database
    private fun insert(transaction: Transaction) {
        lifecycleScope.launch {
            db.transactionDao().insertAll(transaction)
            // Ensure finish() is called on the main thread
            runOnUiThread {
                finish()
            }
        }
    }
}
