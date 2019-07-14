package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_prime.*

class PrimeActivity : AppCompatActivity() {
//Всякий контент
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prime)

        profile_button.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    get_ticket_button.setOnClickListener {
            startActivity(Intent(this,GetTicketActivity::class.java).putExtra("passport", intent.extras!!["userid"].toString()))
        }




    }
}
