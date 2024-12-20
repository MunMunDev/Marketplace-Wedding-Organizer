package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListPilihAlamatBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem

class ChooseAddressAdapter(
    private val list: ArrayList<AlamatModel>,
    private val click: OnClickItem.ClickChooseeAddress
): RecyclerView.Adapter<ChooseAddressAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListPilihAlamatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListPilihAlamatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.apply {
            val kecamatan = "Kacamatan ${data.kab_kota!!.listKecamatan!![0].kecamatan}, ${data.kab_kota.kab_kota}"
            tvNama.text = data.nama_lengkap
            tvNomorHp.text = data.nomor_hp
            tvKecamatan.text = kecamatan
            tvAlamat.text = data.alamat
            tvAlamatDetail.text = data.detail_alamat

            if(data.main=="1"){
                tvTitleAlamatSekarang.visibility = View.VISIBLE
            } else{
                tvTitleAlamatSekarang.visibility = View.GONE
            }

            clAlamat.setOnClickListener {
                click.clickItemPilih(data, it)
            }
            btnUbah.setOnClickListener{
                click.clickItemEdit(data, it)
            }
        }

    }
}