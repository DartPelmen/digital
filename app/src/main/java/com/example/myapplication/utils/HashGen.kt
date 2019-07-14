package com.example.myapplication.utils

import java.security.MessageDigest
//Просто sha-256. Здесь должен быть адекватный шифр или хэш.
class HashGen {
    fun hash(str: String): String {
        val bytes = str.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}