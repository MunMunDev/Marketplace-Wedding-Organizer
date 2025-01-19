package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAdminMainBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.wedding_organizer.AdminWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferencesLogin()
        setAppNavBar()
        setKontrolNavigationDrawer()
        setButton()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@AdminMainActivity)
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
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminMainActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminMainActivity)
        }
    }

    private fun setButton() {
        binding.apply {
            cvWeddingOrganizer.setOnClickListener {
                startActivity(Intent(this@AdminMainActivity, AdminWeddingOrganizerActivity::class.java))
            }
            cvAkunUser.setOnClickListener {
//                startActivity(Intent(this@AdminMainActivity, AdminAkunUserActivity::class.java))
            }
            cvTestimoni.setOnClickListener {
//                startActivity(Intent(this@AdminMainActivity, AdminTestimoniActivity::class.java))
            }
        }
    }
}