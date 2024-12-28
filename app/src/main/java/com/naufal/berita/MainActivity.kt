package com.naufal.berita

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private val storagePermission = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnRegister = findViewById(R.id.btnSignUp)
        btnLogin = findViewById(R.id.btnLogin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this,  RegisterActivity::class.java))
        }
        btnLogin.setOnClickListener{
            startActivity(Intent(this,  LoginActivity::class.java))
        }

        checkStoragePermission()
    }

    private fun checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermission
            )
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }
}