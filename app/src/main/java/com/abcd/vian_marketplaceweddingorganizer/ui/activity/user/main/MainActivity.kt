package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.adapter.PesananAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityMainBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.akun.AkunActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.chat.list_chat.ChatListWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan.list.RiwayatPesananActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan.detail.RiwayatPesananDetailActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.WeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.search.SearchVendorActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        fetchPesanan()
        getPesanan()
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

    private fun fetchPesanan(){
        viewModel.fetchPesanan(sharedPreferencesLogin.getIdUser())
    }

    private fun getPesanan(){
        viewModel.getPesanan().observe(this@MainActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Failure->setFailureFetchPesanan(result.message)
                is UIState.Success->setSuccessFetchPesanan(result.data)
            }
        }
    }

    private fun setSuccessFetchPesanan(data: ArrayList<RiwayatPesananListModel>) {
        if(data.isNotEmpty()){
            binding.apply {
                rvPesanan.visibility = View.VISIBLE
                tvNotHavePesanan.visibility = View.GONE
            }
            setAdapterPesanan(data)
        } else{
            Toast.makeText(this@MainActivity, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureFetchPesanan(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        binding.apply {
            rvPesanan.visibility = View.GONE
            tvNotHavePesanan.visibility = View.VISIBLE
        }
    }

    private fun setAdapterPesanan(data: ArrayList<RiwayatPesananListModel>) {
        val adapter = PesananAdapter(data, object: OnClickItem.ClickPesanan{
            override fun clickPesanan(idPemesanan: Int) {
                val i = Intent(this@MainActivity, RiwayatPesananDetailActivity::class.java)
                i.putExtra("idPemesanan", idPemesanan)
                startActivity(i)
            }
        })
        binding.apply {
            rvPesanan.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvPesanan.adapter = adapter
        }
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
                startActivity(Intent(this@MainActivity, RiwayatPesananActivity::class.java))
            }
            btnAkun.setOnClickListener {
                startActivity(Intent(this@MainActivity, AkunActivity::class.java))
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