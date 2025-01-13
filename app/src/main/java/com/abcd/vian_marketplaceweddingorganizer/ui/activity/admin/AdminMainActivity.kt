package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
    }

    private fun setButton() {

    }
}