package com.example.myapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.utils.HashGen
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_get_ticket.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

//Wait for server
//Тут локальный вариант. Для хэша (sha-256) подается паспорт+фамилия
//Когда будет сервер, переписать вместо паспорт+фамилия uuid+паспорт.
//Стоит добавить сохранение тикета в локальную бд и синхронизацию при наличии связи с сервером.
class GetTicketActivity : AppCompatActivity() {
    val WHITE = -0x1
    val BLACK = -0x1000000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_ticket)
        val email: TextView = findViewById(R.id.textView18)
        email.text="4"
        val lname: TextView = findViewById(R.id.textView22)
        lname.text="21.11.2018"
        val mname: TextView = findViewById(R.id.textView10)
        mname.text= intent.extras!!["passport"].toString()
        val token = intent.extras!!["passport"].toString()+"kuznetcov"
        val token2 = HashGen().hash(token)
        try {
            val barcode_bitmap = encodeAsBitmap(token2, BarcodeFormat.QR_CODE, 200, 200)
            imageView2.setImageBitmap(barcode_bitmap)


        } catch (e: WriterException) {
            e.printStackTrace()
        }


    }
    //Для битмапа для QR
    @Throws(WriterException::class)
    private fun encodeAsBitmap(code: String?, format: BarcodeFormat, img_width: Int, img_height: Int): Bitmap? {
        if (code == null) {
            return null
        }
        var hints: MutableMap<EncodeHintType, Any>? = null
        val encoding = guessAppropriateEncoding(code)
        if (encoding != null) {
            hints = EnumMap(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = encoding
        }
        val writer = MultiFormatWriter()
        val result: BitMatrix
        try {
            result = writer.encode(code, format, img_width, img_height, hints)
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }

        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
    private fun guessAppropriateEncoding(contents: CharSequence): String? {
        // Very crude at the moment
        for (i in 0 until contents.length) {
            if (contents[i].toInt() > 0xFF) {
                return "UTF-8"
            }
        }
        return null
    }
}
