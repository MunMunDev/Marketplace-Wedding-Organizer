package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListDataWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.bumptech.glide.Glide

class WeddingOrganizerAdapter(
    private val tempListWeddingOrganizer: ArrayList<WeddingOrganizerModel>,
    private val click: OnClickItem.ClickWeddingOrganizer
): RecyclerView.Adapter<WeddingOrganizerAdapter.ViewHolder>() {
    private val rupiah = KonversiRupiah()
    private var listWeddingOrganizer = tempListWeddingOrganizer
    
    fun searchData(kata: String){
        val vKata = kata.toLowerCase().trim()
        var data = tempListWeddingOrganizer.filter {
            it.nama_wo!!.lowercase().trim().contains(vKata)
        } as ArrayList
        if(data.size==0){
            for(values in tempListWeddingOrganizer){
                val cek = values.vendor.filter {
                    it.nama_vendor!!.lowercase().trim().contains(vKata)
                } as ArrayList
                if(cek.size>0){
                    data.add(values)
                }
            }
        }
        Log.d("DetailTAG", "searchData: ${data.size}")
        listWeddingOrganizer = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListDataWeddingOrganizerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDataWeddingOrganizerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listWeddingOrganizer.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataWeddingOrganizer = listWeddingOrganizer[position]

        holder.apply {
            binding.apply {
                var vendor = ""
                for(value in dataWeddingOrganizer.vendor){
                    vendor+="${value.nama_vendor}, "
                }
                tvNamaWeddingOrganizer.text = dataWeddingOrganizer.nama_wo!!
                tvAlamat.text = dataWeddingOrganizer.alamat_wo
                tvHarga.text = rupiah.rupiah(dataWeddingOrganizer.harga_wo!!.toLong())
                tvVendor.text = vendor

//                ivGambarWeddingOrganizer.setOnClickListener {
//                    click.clickLogoWeddingOrganizer(
//                        dataWeddingOrganizer.nama_wo,
//                        "${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${dataWeddingOrganizer.logo_wo!!}"
//                    )
//                }

                Glide.with(holder.itemView)
                    .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${dataWeddingOrganizer.logo_wo}") // URL Gambar
                    .error(R.drawable.background_main2)
                    .placeholder(R.drawable.loading)
                    .into(ivGambarWeddingOrganizer) // imageView mana yang akan diterapkan
            }

            itemView.setOnClickListener {
                dataWeddingOrganizer.apply {
                    click.clickWeddingOrganizer(
                        dataWeddingOrganizer
                    )
                }
            }
        }
    }
}