package com.example.challengebinar

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challengebinar.databinding.ActivityLoginBinding
import com.example.challengebinar.databinding.FormPengunjungBinding

class Activity_login : AppCompatActivity() {
    val sharePreferenceFile = "formLoginScreen"

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences : SharedPreferences = this.getSharedPreferences(sharePreferenceFile, MODE_PRIVATE)

        binding.btnLogin.setOnClickListener{
            val shareEmail = sharedPreferences.getString("email_value", "defaultEmail")
            val sharePassword = sharedPreferences.getString("pass_value", "defaultPass")

            if (shareEmail.equals("defaultEmail") || sharePassword.equals("defaultPass")){
                Toast.makeText(this, "Anda Tidak Mempunyai Akses", Toast.LENGTH_SHORT).show()
            }
            else{
                val mainActivity = Intent (this, MainActivity :: class.java )
                startActivity(mainActivity)

            }
        }
         binding.tvSignUp.setOnClickListener{
             val registrasiActivity = Intent (this, form_registrasi :: class.java)
             startActivity(registrasiActivity)
         }
    }
}