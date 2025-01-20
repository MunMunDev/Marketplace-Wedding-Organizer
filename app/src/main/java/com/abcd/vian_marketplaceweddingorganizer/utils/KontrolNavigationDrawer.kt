package com.abcd.vian_marketplaceweddingorganizer.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main.MainActivity
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.AdminMainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.user.AdminUserActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.wedding_organizer.AdminWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.testimoni.AdminTestimoniActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.login.LoginActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.akun.AkunActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.list_chat.ChatListWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan.list.RiwayatPesananActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.WeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.akun.WeddingOrganizerAkunActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main.WeddingOrganizerMainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.list.WeddingOrganizerPesananActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.riwayat_pesanan.list.WeddingOrganizerRiwayatPesananActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.vendor.WeddingOrganizerVendorActivity

class KontrolNavigationDrawer(var context: Context) {
    var sharedPreferences = SharedPreferencesLogin(context)
    fun cekSebagai(navigation: com.google.android.material.navigation.NavigationView){
        if(sharedPreferences.getSebagai() == "user"){
            navigation.menu.clear()
            navigation.inflateMenu(R.menu.nav_menu_user)
        } else if(sharedPreferences.getSebagai() == "wo"){
            navigation.menu.clear()
            navigation.inflateMenu(R.menu.nav_menu_wo)
        }
        else if(sharedPreferences.getSebagai() == "admin"){
            navigation.menu.clear()
            navigation.inflateMenu(R.menu.nav_menu_admin)
        }
    }
    @SuppressLint("ResourceAsColor")
    fun onClickItemNavigationDrawer(navigation: com.google.android.material.navigation.NavigationView, navigationLayout: DrawerLayout, igNavigation:ImageView, activity: Activity){
        navigation.setNavigationItemSelectedListener {
            if(sharedPreferences.getSebagai() == "user"){
                when(it.itemId){
                    R.id.userNavDrawerHome -> {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.userNavDrawerWO -> {
                        val intent = Intent(context, WeddingOrganizerActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.userNavDrawerRiwayatPesanan -> {
                        val intent = Intent(context, RiwayatPesananActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.userNavDrawerAkun -> {
                        val intent = Intent(context, AkunActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.userBtnKeluar ->{
                        logout(activity)
                    }
                }
            } else if(sharedPreferences.getSebagai() == "wo"){
                when(it.itemId){
                    R.id.woNavDrawerHome -> {
                        val intent = Intent(context, WeddingOrganizerMainActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woNavDrawerVendor -> {
                        val intent = Intent(context, WeddingOrganizerVendorActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woNavDrawerChat -> {
                        val intent = Intent(context, ChatListWeddingOrganizerActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woNavDrawerPesanan -> {
                        val intent = Intent(context, WeddingOrganizerPesananActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woNavDrawerRiwayatPesanan -> {
                        val intent = Intent(context, WeddingOrganizerRiwayatPesananActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woNavDrawerAkun -> {
                        val intent = Intent(context, WeddingOrganizerAkunActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.woBtnKeluar ->{
                        logout(activity)
                    }
                }
            }
            else if(sharedPreferences.getSebagai() == "admin"){
                when(it.itemId){
                    R.id.adminNavDrawerHome -> {
                        val intent = Intent(context, AdminMainActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerAkunWo -> {
                        val intent = Intent(context, AdminWeddingOrganizerActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerAkunUser -> {
                        val intent = Intent(context, AdminUserActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerTestimoni -> {
                        val intent = Intent(context, AdminTestimoniActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }

                    R.id.adminBtnKeluar ->{
                        logout(activity)
                    }
                }

            }
            navigationLayout.setBackgroundColor(R.color.white)
            navigationLayout.closeDrawer(GravityCompat.START)
            true
        }
        // garis 3 navigasi
        igNavigation.setOnClickListener {
            navigationLayout.openDrawer(GravityCompat.START)
        }
    }

    fun logout(activity: Activity){
        sharedPreferences.setLogin(0, 0,"", "","", "","", "","", "")
        context.startActivity(Intent(context, LoginActivity::class.java))
        activity.finish()

    }
}