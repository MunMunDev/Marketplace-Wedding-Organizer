package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.adapter.RiwayatPesananListAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityRiwayatPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RiwayatPesananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatPesananBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: RiwayatPesananViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setSharedPreferencesLogin()
        fetchRiwayatPembayaran()
        getRiwayatPembayaran()
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@RiwayatPesananActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, ivDrawerView, this@RiwayatPesananActivity)
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@RiwayatPesananActivity)
    }

    private fun fetchRiwayatPembayaran() {
        viewModel.fetchRiwayatPesanan(sharedPreferencesLogin.getIdUser())
    }
    private fun getRiwayatPembayaran(){
        viewModel.getRiwayatPesanan().observe(this@RiwayatPesananActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchRiwayatPembayaran(result.data)
                is UIState.Failure-> setFailureFetchRiwayatPembayaran(result.message)
            }
        }
    }

    private fun setFailureFetchRiwayatPembayaran(message: String) {
        Toast.makeText(this@RiwayatPesananActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchRiwayatPembayaran(data: ArrayList<RiwayatPesananListModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@RiwayatPesananActivity, "Tidak ada data Jenis Plafon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter(data: ArrayList<RiwayatPesananListModel>) {
        val adapter = RiwayatPesananListAdapter(data, object : OnClickItem.ClickRiwayatPesanan{
            override fun clickRiwayatPesanan(idPemesanan: Int) {
//                val i = Intent(this@RiwayatPesananActivity, RiwayatPesananDetailActivity::class.java)
//                i.putExtra("idPemesanan", idPemesanan)
//                startActivity(i)
            }

        })
        binding.apply {
            rvRiwayatPesanan.layoutManager = LinearLayoutManager(this@RiwayatPesananActivity, LinearLayoutManager.VERTICAL, false)
            rvRiwayatPesanan.adapter = adapter
        }
    }
}