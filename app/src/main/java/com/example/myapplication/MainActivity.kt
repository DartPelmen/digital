package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dao.UserDao
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.models.User
import com.example.myapplication.utils.HashGen
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    val WHITE = -0x1
    val BLACK = -0x1000000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)

        Log.v("INFOGF: ",HashGen().hash("Hello"))
        login_button.setOnClickListener {

            //Log.v(login_field.text.toString(),password_field.text.toString())
            Retrofit.Builder().
                baseUrl("http://194.165.3.32:8000").
                addConverterFactory(GsonConverterFactory.create()).
                build().create(Request::class.java).registrationPost(login_field.text.toString(),password_field.text.toString()).enqueue(object :
                Callback<Login> {
                override fun onFailure(call: Call<Login>?, t: Throwable?) {
                    Log.v("ASDASDASDASD","GO TO OFFLINE")
                    val i=Intent(this@MainActivity,PrimeActivity::class.java)
                    i.putExtra("userid","1016499912")
                    i.putExtra("username","DartPelmen")
                    i.putExtra("password","qwerty")
                    startActivity(i)
                }
                override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                    // Раскомментить, если хочется попробовать с сервером. Переписать (тут плохо), когда будет рабочий сервер
                    // Log.v("ASDASDASDASD",response!!.body().status)
                    val i=Intent(this@MainActivity,PrimeActivity::class.java)
                    i.putExtra("userid","1016499912")
                    i.putExtra("username","DartPelmen")
                    i.putExtra("password","qwerty")
                    startActivity(i)
                }})
        }

//Шаблон для Room API - для локального хранения талонов. Работает, дописывать - по готовности сервера
      /* Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            var gender1 = User(first_name = "FI", middle_name = "MI", last_name = "LI",passport = "101049912",email = "kyzaiv1@yandex.ru",username = "DartPelmen",date_of_birth = Date(1996,11,21),phone = "9990882846", id = 1,password = "qwerty")
            var gender2 = User(first_name = "FI", middle_name = "MI", last_name = "LI",passport = "101049912",email = "kyzaiv2@yandex.ru",username = "DartPelmen2",date_of_birth = Date(1997,11,21),phone = "9990882846", id = 2,password = "qwerty")

            with(userDao){
                this?.insertUser(gender1)
                this?.insertUser(gender2)
            }
            db?.userDao()?.getUsers()
        }.doOnNext { list ->
            var finalString = ""
            list?.map { finalString+= it.email+" - " }
            Log.v("ASd",finalString)

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()*/
       /*
        //Тут генерится QR-код. Дописывать - по серверу
        val token = "token"
        try {
            val barcode_bitmap = encodeAsBitmap(token, BarcodeFormat.QR_CODE, 200, 200)
            imageView.setImageBitmap(barcode_bitmap)


        } catch (e: WriterException) {
            e.printStackTrace()
        }*/


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
