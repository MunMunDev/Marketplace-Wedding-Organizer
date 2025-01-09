package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.list

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
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.detail.WeddingOrganizerPesananDetailActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeddingOrganizerPesananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerPesananBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerPesananViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStartShimmer()
        setKontrolNavigationDrawer()
        setSharedPreferencesLogin()
        fetchPesanan()
        getPesanan()
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            drawerView.tvTitle.text = "Pesanan"

            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerPesananActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, drawerView.ivNavDrawer, this@WeddingOrganizerPesananActivity)
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@WeddingOrganizerPesananActivity)
    }

    private fun fetchPesanan() {
        viewModel.fetchPesanan(sharedPreferencesLogin.getIdWO())
    }
    private fun getPesanan(){
        viewModel.getPesanan().observe(this@WeddingOrganizerPesananActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchPesanan(result.data)
                is UIState.Failure-> setFailureFetchPesanan(result.message)
            }
        }
    }

    private fun setFailureFetchPesanan(message: String) {
        setStopShimmer()
        Toast.makeText(this@WeddingOrganizerPesananActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchPesanan(data: ArrayList<RiwayatPesananListModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@WeddingOrganizerPesananActivity, "Tidak ada data Pesanan", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapter(data: ArrayList<RiwayatPesananListModel>) {
        val adapter = WeddingOrganizerPesananListAdapter(data, object : OnClickItem.ClickPesanan{
            override fun clickPesanan(idPemesanan: Int) {
                val i = Intent(this@WeddingOrganizerPesananActivity, WeddingOrganizerPesananDetailActivity::class.java)
                i.putExtra("idPemesanan", idPemesanan)
                startActivity(i)
            }

        })
        binding.apply {
            rvPesanan.layoutManager = LinearLayoutManager(this@WeddingOrganizerPesananActivity, LinearLayoutManager.VERTICAL, false)
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