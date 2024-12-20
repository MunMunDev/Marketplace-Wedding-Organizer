package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.WeddingOrganizerAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogShowImageBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.detail.WeddingOrganizerDetailActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeddingOrganizerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerViewModel by viewModels()
    private lateinit var plafonAdapter: WeddingOrganizerAdapter
    @Inject
    lateinit var loading: LoadingAlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavitagionDrawer()
        setSharedPreferencesLogin()
        setButton()
        fetchWeddingOrganizer()
        getWeddingOrganizer()
    }
    private fun setButton() {
        binding.apply {

        }
    }


    private fun setNavitagionDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, appNavbarDrawer.ivNavDrawer, this@WeddingOrganizerActivity)

            appNavbarDrawer.tvTitle.text = "Wedding Organizer"
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@WeddingOrganizerActivity)
    }

    private fun fetchWeddingOrganizer() {
        viewModel.fetchWeddingOrganizer()
    }

    private fun getWeddingOrganizer() {
        viewModel.getWeddingOrganizer().observe(this@WeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerActivity)
                is UIState.Failure-> setFailureFetchWeddingOrganizer(result.message)
                is UIState.Success-> setSuccessFetchWeddingOrganizer(result.data)
            }
        }
    }

    private fun setFailureFetchWeddingOrganizer(message: String) {
        Toast.makeText(this@WeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
        loading.alertDialogCancel()
    }

    private fun setSuccessFetchWeddingOrganizer(data: ArrayList<WeddingOrganizerModel>) {
        if(data.isNotEmpty()){
            setAdapterWeddingOrganizer(data)
        } else{
            Toast.makeText(this@WeddingOrganizerActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
        setStopShimmer()
    }

    private fun setAdapterWeddingOrganizer(data: ArrayList<WeddingOrganizerModel>){
        plafonAdapter = WeddingOrganizerAdapter(data, object : OnClickItem.ClickWeddingOrganizer{
            override fun clickWeddingOrganizer(weddingOrganizer: WeddingOrganizerModel) {
                val i = Intent(this@WeddingOrganizerActivity, WeddingOrganizerDetailActivity::class.java)
                i.putExtra("idWeddingOrganizer", weddingOrganizer.id_wo)
                i.putExtra("nama", weddingOrganizer.nama_wo)
                i.putExtra("deskripsi", weddingOrganizer.deskripsi_wo)
                i.putExtra("alamat", weddingOrganizer.alamat_wo)
                i.putExtra("harga", weddingOrganizer.harga_wo)
                i.putExtra("gambarWeddingOrganizer", weddingOrganizer.logo_wo)
                i.putExtra("vendor", weddingOrganizer.vendor)
                i.putParcelableArrayListExtra("vendor", weddingOrganizer.vendor)

                startActivity(i)
            }

//            override fun clickLogoWeddingOrganizer(namaWO: String, logo: String) {
//                setShowImage(namaWO, logo)
//            }

        })
        binding.apply {
            rvWeddingOrganizer.layoutManager = LinearLayoutManager(this@WeddingOrganizerActivity, LinearLayoutManager.VERTICAL, false)
            rvWeddingOrganizer.adapter = plafonAdapter
        }
    }

    private fun setShowImage(jenisWeddingOrganizer: String, image: String) {
        val view = AlertDialogShowImageBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = jenisWeddingOrganizer
            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }

        Glide.with(this@WeddingOrganizerActivity)
            .load(image) // URL Gambar
            .error(R.drawable.background_main2)
            .placeholder(R.drawable.loading)
            .into(view.ivShowImage) // imageView mana yang akan diterapkan

    }

    private fun setStopShimmer(){
        binding.apply {
            smWeddingOrganizer.stopShimmer()
            smWeddingOrganizer.visibility = View.GONE

            rvWeddingOrganizer.visibility = View.VISIBLE
        }
    }
}