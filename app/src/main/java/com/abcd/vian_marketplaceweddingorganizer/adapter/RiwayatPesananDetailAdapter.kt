package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.PesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListRiwayatPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu

class RiwayatPesananDetailAdapter(
    private val listPesanan: ArrayList<RiwayatPesananModel>,
): RecyclerView.Adapter<RiwayatPesananDetailAdapter.ViewHolder>() {
    private val rupiah = KonversiRupiah()
    private val tanggalDanWaktu = TanggalDanWaktu()
    class ViewHolder(val binding: ListRiwayatPesananBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRiwayatPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPesanan = listPesanan[position]

        holder.binding.apply {
            dataPesanan.apply {
                Log.d("DetailTAG", "onBindViewHolder: $id_riwayat_pesanan, $id_user, ${wo}, $vendor, $harga, $waktu_acara")
            }
            tvVendor.text = dataPesanan.vendor!!
            tvHarga.text = rupiah.rupiah(dataPesanan.harga!!.toLong())

        }
    }
}