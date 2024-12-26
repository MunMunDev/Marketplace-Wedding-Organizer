package com.abcd.vian_marketplaceweddingorganizer.adapter

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

class RiwayatPesananListAdapter(
    private val listPesanan: ArrayList<RiwayatPesananListModel>,
    private val onClick: OnClickItem.ClickRiwayatPesanan
): RecyclerView.Adapter<RiwayatPesananListAdapter.ViewHolder>() {

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
            val wo = pesanan.wo!!
            val harga = rupiah.rupiah(pesanan.harga!!.toLong())
            val vendor = pesanan.vendor!!

            tvJudulWO.text = wo
            tvHarga.text = harga
            tvVendor.text = vendor

            Glide.with(holder.itemView)
                .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${pesanan.logo_wo}") // URL Gambar
                .error(R.drawable.background_main2)
                .placeholder(R.drawable.loading)
                .into(ivGambarWO) // imageView mana yang akan diterapkan
        }
        holder.itemView.setOnClickListener {
            onClick.clickRiwayatPesanan(pesanan.id_pemesanan!!)
        }
    }
}