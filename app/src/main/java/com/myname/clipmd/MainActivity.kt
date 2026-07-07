package com.myname.clipmd

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val sharedPrefs = getSharedPreferences("AppConfig", Context.MODE_PRIVATE)

        // نمایش مقدار قبلی
        categoryInput.setText(sharedPrefs.getString("DEFAULT_CATEGORY", "General"))

        saveBtn.setOnClickListener {
            val newCategory = categoryInput.text.toString().trim()
            if (newCategory.isNotEmpty()) {
                sharedPrefs.edit().putString("DEFAULT_CATEGORY", newCategory).apply()
                Toast.makeText(this, "دسته‌بندی تغییر کرد: $newCategory", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "لطفاً یک نام وارد کنید", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
