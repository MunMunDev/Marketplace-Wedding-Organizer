package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.PesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import java.lang.Exception

class PesananAdapter(
    private val listPesanan: ArrayList<RiwayatPesananListModel>,
    private val onClick: OnClickItem.ClickPesanan
): RecyclerView.Adapter<PesananAdapter.ViewHolder>() {

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
            var wo = pesanan.wo!!
            val harga = rupiah.rupiah(pesanan.harga!!.toLong())
            val vendor = pesanan.vendor!!

            tvJudulWO.text = wo
            tvHarga.text = harga
            tvVendor.text = vendor
        }
        holder.itemView.setOnClickListener {
            onClick.clickPesanan(pesanan.id_pemesanan!!)
        }
    }
}