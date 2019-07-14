package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dao.UserDao
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.models.User
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

//профиль - или из сервера, или из оффлайна
class ProfileActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()



            db?.userDao()?.getUserByUsernameAndPassword("DartPelmen", "qwerty")

        }.doOnNext { list ->
            var finalString = ""
            list?.map {
                val email: TextView = findViewById(R.id.email_value_field)
                email.text=it!!.email
                val lname: TextView = findViewById(R.id.lname_value_field)
                lname.text=it!!.first_name
                val mname: TextView = findViewById(R.id.mname_value_field)
                mname.text=it!!.middle_name
                val fname: TextView = findViewById(R.id.fname_value_field)
                fname.text=it!!.first_name
                val dob: TextView = findViewById(R.id.date_of_birth_valuefield)
                dob.text=it!!.date_of_birth.toString()
            }
            Log.v("ASd",finalString)

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }
}
