package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.riwayat_pesanan.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.wo.WeddingOrganizerPesananListAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerRiwayatPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.detail.WeddingOrganizerPesananDetailActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.list.WeddingOrganizerPesananViewModel
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeddingOrganizerRiwayatPesananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerRiwayatPesananBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerRiwayatPesananViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerRiwayatPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStartShimmer()
        setKontrolNavigationDrawer()
        setSharedPreferencesLogin()
        fetchRiwayatPesanan()
        getRiwayatPesanan()
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            drawerView.tvTitle.text = "Pesanan"

            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerRiwayatPesananActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, drawerView.ivNavDrawer, this@WeddingOrganizerRiwayatPesananActivity)
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@WeddingOrganizerRiwayatPesananActivity)
    }

    private fun fetchRiwayatPesanan() {
        viewModel.fetchRiwayatPesanan(sharedPreferencesLogin.getIdWO())
    }

    private fun getRiwayatPesanan(){
        viewModel.getRiwayatPesanan().observe(this@WeddingOrganizerRiwayatPesananActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchRiwayatPesanan(result.data)
                is UIState.Failure-> setFailureFetchRiwayatPesanan(result.message)
            }
        }
    }

    private fun setFailureFetchRiwayatPesanan(message: String) {
        setStopShimmer()
        Toast.makeText(this@WeddingOrganizerRiwayatPesananActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchRiwayatPesanan(data: ArrayList<RiwayatPesananListModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@WeddingOrganizerRiwayatPesananActivity, "Tidak ada data Pesanan", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapter(data: ArrayList<RiwayatPesananListModel>) {
        val adapter = WeddingOrganizerPesananListAdapter(data, object : OnClickItem.ClickPesanan{
            override fun clickPesanan(idPemesanan: Int) {
                val i = Intent(this@WeddingOrganizerRiwayatPesananActivity, WeddingOrganizerPesananDetailActivity::class.java)
                i.putExtra("idPemesanan", idPemesanan)
                startActivity(i)
            }

        })
        binding.apply {
            rvPesanan.layoutManager = LinearLayoutManager(this@WeddingOrganizerRiwayatPesananActivity, LinearLayoutManager.VERTICAL, false)
            rvPesanan.adapter = adapter
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smListPesanan.startShimmer()
            smListPesanan.visibility = View.VISIBLE
            rvPesanan.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smListPesanan.stopShimmer()
            smListPesanan.visibility = View.GONE
            rvPesanan.visibility = View.VISIBLE
        }
    }
}