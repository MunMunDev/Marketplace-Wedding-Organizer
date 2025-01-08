package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerMainBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.akun.AkunActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.list_chat.ChatListWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.list.WeddingOrganizerPesananActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.vendor.WeddingOrganizerVendorActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin

class WeddingOrganizerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerMainBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferencesLogin()
        setAppNavBar()
        setKontrolNavigationDrawer()
        setButton()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@WeddingOrganizerMainActivity)
    }

    private fun setAppNavBar() {
        binding.myAppBar.apply {
            ivBack.visibility = View.GONE
            ivNavDrawer.visibility = View.VISIBLE

//            tvTitle.text = "Halaman Wedding Organizer"
            tvTitle.text = sharedPreferencesLogin.getNama()
        }
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerMainActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@WeddingOrganizerMainActivity)
        }
    }

    private fun setButton() {
        binding.apply {
            cvVendor.setOnClickListener {
                startActivity(Intent(this@WeddingOrganizerMainActivity, WeddingOrganizerVendorActivity::class.java))
            }
            cvChat.setOnClickListener {
                startActivity(Intent(this@WeddingOrganizerMainActivity, ChatListWeddingOrganizerActivity::class.java))
            }
            cvPesanan.setOnClickListener {
                startActivity(Intent(this@WeddingOrganizerMainActivity, WeddingOrganizerPesananActivity::class.java))
            }
            cvRiwayatPesanan.setOnClickListener {

            }
            cvAkun.setOnClickListener {
                startActivity(Intent(this@WeddingOrganizerMainActivity, AkunActivity::class.java))
            }

        }
    }
}