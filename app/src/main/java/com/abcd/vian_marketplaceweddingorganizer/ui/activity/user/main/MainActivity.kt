package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityMainBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.akun.AkunActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.chat.list_chat.ChatListWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.WeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.search.SearchVendorActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavitagionDrawer()
        setSharedPreferencesLogin()
        setButton()
    }

    private fun setNavitagionDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@MainActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(
                navView,
                drawerLayoutMain,
                ivDrawerView,
                this@MainActivity
            )
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@MainActivity)
        binding.tvNamaUser.text = "Hy, ${sharedPreferencesLogin.getNama()}"
    }

    private fun setButton() {
        binding.apply {
            ivChat.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        ChatListWeddingOrganizerActivity::class.java
                    )
                )
            }
            srcData.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchVendorActivity::class.java))
            }
            btnWO.setOnClickListener {
                startActivity(Intent(this@MainActivity, WeddingOrganizerActivity::class.java))
            }
            btnRiwayatPembayaran.setOnClickListener {
//                startActivity(Intent(this@MainActivity, RiwayatPesananActivity::class.java))
            }
            btnAkun.setOnClickListener {
                startActivity(Intent(this@MainActivity, AkunActivity::class.java))
            }
            btnPesan.setOnClickListener {
//                showDialogPesan(listPesanan)
//                val i = Intent(this@MainActivity, PaymentActivity::class.java)
//                i.putParcelableArrayListExtra("pesanan", listPesanan)
//                startActivity(i)
//                finish()
            }
        }
    }

    var tapDuaKali = false
    override fun onBackPressed() {
        if (tapDuaKali) {
            super.onBackPressed()
        }
        tapDuaKali = true
        Toast.makeText(this@MainActivity, "Tekan Sekali Lagi untuk keluar", Toast.LENGTH_SHORT)
            .show()

        Handler().postDelayed({
            tapDuaKali = false
        }, 2000)

    }
}