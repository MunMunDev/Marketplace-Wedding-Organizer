package com.abcd.vian_marketplaceweddingorganizer.adapter.wo

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListWoVendorBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import javax.inject.Inject

class WeddingOrganizerVendorAdapter(
    private var listJenisPlafon: ArrayList<VendorModel>,
    private var onClick: OnClickItem.ClickWeddingOrganizerVendor
): RecyclerView.Adapter<WeddingOrganizerVendorAdapter.ViewHolder>() {
    private val rupiah = KonversiRupiah()
    class ViewHolder(val binding: ListWoVendorBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListWoVendorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listJenisPlafon.size+1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(position==0){
                tvNo.text = "NO"
                tvVendor.text = "Vendor"
                tvHarga.text = "Harga"
                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvVendor.setBackgroundResource(R.drawable.bg_table_title)
                tvHarga.setBackgroundResource(R.drawable.bg_table_title)
                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvVendor.setTextColor(Color.parseColor("#ffffff"))
                tvHarga.setTextColor(Color.parseColor("#ffffff"))
                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvVendor.setTypeface(null, Typeface.BOLD)
                tvHarga.setTypeface(null, Typeface.BOLD)
                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val vendor = listJenisPlafon[(position-1)]
                val harga = rupiah.rupiah(vendor.harga!!.toLong())

                tvNo.text = "$position"
                tvVendor.text = vendor.nama_vendor
                tvHarga.text = harga
                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvVendor.setBackgroundResource(R.drawable.bg_table)
                tvHarga.setBackgroundResource(R.drawable.bg_table)
                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvVendor.setTextColor(Color.parseColor("#000000"))
                tvHarga.setTextColor(Color.parseColor("#000000"))
                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvVendor.setTypeface(null, Typeface.NORMAL)
                tvHarga.setTypeface(null, Typeface.NORMAL)
                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvHarga.gravity = Gravity.CENTER_VERTICAL

                tvVendor.setOnClickListener{
                    onClick.clickItemVendor(vendor, it)
                }
                tvSetting.setOnClickListener {
                    onClick.clickItemSetting(vendor, it)
                }
            }
        }
    }
}