package com.abcd.vian_marketplaceweddingorganizer.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivitySplashScreenBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.AdminMainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.login.LoginActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main.MainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main.WeddingOrganizerMainActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.hide()
        }

        sharedPreferencesLogin = SharedPreferencesLogin(this@SplashScreenActivity)

        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPreferencesLogin.getIdUser() == 0){
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }
            else{
                if(sharedPreferencesLogin.getSebagai() == "user"){
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }  else if(sharedPreferencesLogin.getSebagai() == "wo"){
                    startActivity(Intent(this@SplashScreenActivity, WeddingOrganizerMainActivity::class.java))
                    finish()
                } else if(sharedPreferencesLogin.getSebagai() == "admin"){
                    startActivity(Intent(this@SplashScreenActivity, AdminMainActivity::class.java))
                    finish()
                }
            }
        }, 3000)
    }
}