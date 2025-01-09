package com.abcd.vian_marketplaceweddingorganizer.adapter.wo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.bumptech.glide.Glide

class WeddingOrganizerPesananListAdapter(
    private val listPesanan: ArrayList<RiwayatPesananListModel>,
    private val onClick: OnClickItem.ClickPesanan
): RecyclerView.Adapter<WeddingOrganizerPesananListAdapter.ViewHolder>() {

    private val rupiah = KonversiRupiah()

    class ViewHolder(val binding: ListPesananBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pesanan = listPesanan[position]
        holder.binding.apply {
            val wo = pesanan.nama_lengkap!!
            val harga = rupiah.rupiah(pesanan.harga!!.toLong())
            val vendor = pesanan.vendor!!

            tvJudulWO.text = wo
            tvHarga.text = harga
            tvVendor.text = vendor

            Glide.with(holder.itemView)
                .load("") // URL Gambar
                .error(R.drawable.gambar_user)
                .placeholder(R.drawable.loading)
                .into(ivGambarWO) // imageView mana yang akan diterapkan
        }
        holder.itemView.setOnClickListener {
            onClick.clickPesanan(pesanan.id_pemesanan!!)
        }
    }
}