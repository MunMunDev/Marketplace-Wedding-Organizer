package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.WeddingOrganizerAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivitySearchVendorBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.detail.WeddingOrganizerDetailActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchVendorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchVendorBinding
    @Inject
    lateinit var loading: LoadingAlertDialog
    private val viewModel: SearchVendorViewModel by viewModels()
    private var listWeddingOrganizer = ArrayList<WeddingOrganizerModel>()
    private lateinit var adapter: WeddingOrganizerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchData()
        setButton()
        fetchWeddingOrganizer()
        getWeddingOrganizer()
    }

    private fun setSearchData() {
        binding.srcData.apply {
            requestFocus()
            onActionViewExpanded()
            queryHint = ""
        }
        // Search Data
        binding.srcData.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("MainActivityTAG", "onQueryTextSubmit:1: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivityTAG", "onQueryTextChange:2: $newText ")
//                if(!cekSearchData){     // WeddingOrganizer
//                    adapter.searchData(newText!!)
//                    Log.d("MainActivityTAG", "materi")
//                }else {     // Video
//                    adapterVideo.searchData(newText!!)
//                    Log.d("MainActivityTAG", "video ")
//                }
                adapter.searchData(newText!!)
                return true
            }

        })
    }

    private fun setButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }


    private fun fetchWeddingOrganizer() {
        viewModel.fetchWeddingOrganizer()
    }

    private fun getWeddingOrganizer() {
        viewModel.getWeddingOrganizer().observe(this@SearchVendorActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@SearchVendorActivity)
                is UIState.Failure-> setFailureWeddingOrganizer(result.message)
                is UIState.Success-> setSuccessWeddingOrganizer(result.data)
            }
        }
    }

    private fun setFailureWeddingOrganizer(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@SearchVendorActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessWeddingOrganizer(data: ArrayList<WeddingOrganizerModel>) {
        loading.alertDialogCancel()
        val sort = data.sortedWith(compareBy { it.nama })
        val dataArrayList = arrayListOf<WeddingOrganizerModel>()
        dataArrayList.addAll(sort)
        listWeddingOrganizer = dataArrayList
        adapter = WeddingOrganizerAdapter(listWeddingOrganizer, object : OnClickItem.ClickWeddingOrganizer{
            override fun clickWeddingOrganizer(weddingOrganizer: WeddingOrganizerModel) {
                val i = Intent(this@SearchVendorActivity, WeddingOrganizerDetailActivity::class.java)
                i.putExtra("idWeddingOrganizer", weddingOrganizer.id_wo)
                i.putExtra("nama", weddingOrganizer.nama)
                i.putExtra("deskripsi", weddingOrganizer.deskripsi_wo)
                i.putExtra("alamat", weddingOrganizer.alamat)
                i.putExtra("gambarWeddingOrganizer", weddingOrganizer.logo_wo)
                i.putExtra("vendor", weddingOrganizer.vendor)
                i.putParcelableArrayListExtra("vendor", weddingOrganizer.vendor)

                startActivity(i)
            }

        })
        setRecyclerViewWeddingOrganizer(dataArrayList)
    }

    private fun setRecyclerViewWeddingOrganizer(data: ArrayList<WeddingOrganizerModel>) {
        binding.apply {
            rvData.layoutManager = LinearLayoutManager(
                this@SearchVendorActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            rvData.adapter = adapter
        }
    }

}