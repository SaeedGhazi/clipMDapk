package com.myname.clipmd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShareReceiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            val subject = intent.getStringExtra(Intent.EXTRA_SUBJECT) ?: "منابع متفرقه"
            
            if (sharedText != null) {
                saveToFile(sharedText, subject)
            }
        }
        finish() // بسته شدن سریع برنامه
    }

    private fun saveToFile(text: String, source: String) {
        val sharedPrefs = getSharedPreferences("AppConfig", Context.MODE_PRIVATE)
        val defaultCategory = sharedPrefs.getString("DEFAULT_CATEGORY", "General") ?: "General"
        
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        
        val markdownBlock = """
        ## 📌 منبع: $source
        - **📅 تاریخ:** $currentTime
        - **🗂️ دسته:** $defaultCategory
        
        ### 📝 محتوا:
        $text
        
        ---
        
        """.trimIndent()

        try {
            val directory = getExternalFilesDir(null)
            if (directory != null && !directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "$defaultCategory.md")
            
            val writer = FileWriter(file, true)
            writer.append(markdownBlock)
            writer.flush()
            writer.close()

            Toast.makeText(this, "✅ ذخیره شد در $defaultCategory", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "❌ خطا در ذخیره‌سازی", Toast.LENGTH_SHORT).show()
        }
    }
}
