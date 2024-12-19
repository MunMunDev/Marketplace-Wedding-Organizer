package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListVendorWeddingOrganizerDetailBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import java.lang.Exception

class VendorOrderAdapter(
    private val listTestimoni: ArrayList<VendorModel>,
): RecyclerView.Adapter<VendorOrderAdapter.ViewHolder>() {

    private val rupiah = KonversiRupiah()

    class ViewHolder(val binding: ListVendorWeddingOrganizerDetailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListVendorWeddingOrganizerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTestimoni.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendor = listTestimoni[position]
        holder.binding.apply {
            var nama = vendor.nama_vendor!!
            val harga = rupiah.rupiah(vendor.harga!!.toLong())

            tvNamaVendor.text = nama
            tvHarga.text = harga
        }
    }
}