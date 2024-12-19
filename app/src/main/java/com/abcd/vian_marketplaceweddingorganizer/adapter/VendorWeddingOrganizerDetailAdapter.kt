package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListVendorWeddingOrganizerDetailBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import java.lang.Exception

class VendorWeddingOrganizerDetailAdapter(
    private val listTestimoni: ArrayList<VendorModel>,
    private val check: Boolean,
    private val onClick: OnClickItem.ClickChooseVendorWeddingOrganizer
): RecyclerView.Adapter<VendorWeddingOrganizerDetailAdapter.ViewHolder>() {

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

            holder.itemView.setOnClickListener {
//                clVendor.setBackgroundResource(R.drawable.bg_card_klik_vendor)

                if(check){
                    val drawableAConstantState = ContextCompat.getDrawable(
                        holder.itemView.context, R.drawable.bg_card
                    )?.constantState
                    if (clVendor.background?.constantState == drawableAConstantState){
                        clVendor.setBackgroundResource(R.drawable.bg_card_klik_vendor)
                        onClick.clickChooseVendorWeddingOrganizer(true, position, vendor)
                    }
                    else {
                        clVendor.setBackgroundResource(R.drawable.bg_card)
                        onClick.clickChooseVendorWeddingOrganizer(false, position, vendor)
                    }
                }
            }
        }
    }
}