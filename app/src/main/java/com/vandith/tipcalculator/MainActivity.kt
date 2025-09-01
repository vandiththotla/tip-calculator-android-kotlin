package com.vandith.tipcalculator

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import java.text.NumberFormat
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etBill = findViewById<EditText>(R.id.etBill)
        val seekTip = findViewById<SeekBar>(R.id.seekTip)
        val tvTipPercent = findViewById<TextView>(R.id.tvTipPercent)
        val etSplit = findViewById<EditText>(R.id.etSplit)
        val btnCalc = findViewById<Button>(R.id.btnCalc)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        var tipPct = 15
        seekTip.progress = tipPct
        tvTipPercent.text = "Tip: ${tipPct}%"

        seekTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPct = progress
                tvTipPercent.text = "Tip: ${tipPct}%"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        val currency = NumberFormat.getCurrencyInstance()

        btnCalc.setOnClickListener {
            val bill = etBill.text.toString().toDoubleOrNull()
            val split = max(1, etSplit.text.toString().toIntOrNull() ?: 1)
            if (bill == null || bill <= 0.0) {
                Toast.makeText(this, "Enter a valid bill amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tip = bill * tipPct / 100.0
            val total = bill + tip
            val perPerson = total / split
            tvResult.text = "Tip: ${currency.format(tip)}  •  Total: ${currency.format(total)}  •  Per Person: ${currency.format(perPerson)}"
        }

        btnReset.setOnClickListener {
            etBill.setText("")
            etSplit.setText("")
            seekTip.progress = 15
            tvResult.text = "Tip: $0.00  •  Total: $0.00  •  Per Person: $0.00"
        }
    }
}
