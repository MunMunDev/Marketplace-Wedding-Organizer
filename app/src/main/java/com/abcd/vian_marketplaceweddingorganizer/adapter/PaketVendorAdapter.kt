package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.PaketModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListVendorPaketBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import java.lang.Exception

class PaketVendorAdapter(
    private val listPaket: ArrayList<PaketModel>,
    private val onClick: OnClickItem.ClickPaketWeddingOrganizer
): RecyclerView.Adapter<PaketVendorAdapter.ViewHolder>() {

    private val rupiah = KonversiRupiah()

    class ViewHolder(val binding: ListVendorPaketBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListVendorPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPaket.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paket = listPaket[position]
        holder.binding.apply {
            val nama = paket.nama_paket!!
            val keterangan = paket.keterangan!!
            var harga = 0
            val listPaket: ArrayList<VendorModel> = arrayListOf()
            for(value in paket.paket_vendor!!){
                val vendor = value.vendor!!
                val vendorHarga = vendor.harga!!
                harga+=vendorHarga

                listPaket.add(vendor)
            }

            val hargaRupiah = rupiah.rupiah(harga.toLong())

            tvNamaPaket.text = nama
            tvHarga.text = hargaRupiah
            tvKeteranganPaket.text = keterangan

            btnPesanPaket.setOnClickListener {
                onClick.clickPaketWeddingOrganizer(listPaket)
            }
        }
    }
}