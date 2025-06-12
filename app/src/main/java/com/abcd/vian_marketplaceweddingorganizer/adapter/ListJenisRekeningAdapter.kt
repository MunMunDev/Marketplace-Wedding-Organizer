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
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListJenisRekeningBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListRiwayatPesananBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu

class ListJenisRekeningAdapter(
    private val listJenisRekening: ArrayList<RekeningModel>,
): RecyclerView.Adapter<ListJenisRekeningAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListJenisRekeningBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListJenisRekeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listJenisRekening.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jenisRekening = listJenisRekening[position]

        holder.binding.apply {
            tvJenisBank.text = "Bank ${jenisRekening.jenis_rekening!!}"
            tvNoRekening.text = jenisRekening.no_rekening!!

        }
    }
}