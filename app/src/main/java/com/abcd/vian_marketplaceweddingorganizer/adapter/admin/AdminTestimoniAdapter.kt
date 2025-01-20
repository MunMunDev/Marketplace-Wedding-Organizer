package com.abcd.vian_marketplaceweddingorganizer.adapter.admin

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListAdminTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem

class AdminTestimoniAdapter(
    private var listTestimoni: ArrayList<TestimoniModel>,
    private var onClick: OnClickItem.ClickAdminTestimoni
): RecyclerView.Adapter<AdminTestimoniAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListAdminTestimoniBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAdminTestimoniBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listTestimoni.size+1)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(position==0){
                tvNo.text = "NO"
                tvNama.text = "Nama"
                tvVendor.text = "Vendor"
                tvTestimoni.text = "Testimoni"
                tvBintang.text = "Bintang"
                tvTanggal.text = "Tanggal"
                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvNama.setBackgroundResource(R.drawable.bg_table_title)
                tvVendor.setBackgroundResource(R.drawable.bg_table_title)
                tvTestimoni.setBackgroundResource(R.drawable.bg_table_title)
                tvBintang.setBackgroundResource(R.drawable.bg_table_title)
                tvTanggal.setBackgroundResource(R.drawable.bg_table_title)
                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvNama.setTextColor(Color.parseColor("#ffffff"))
                tvVendor.setTextColor(Color.parseColor("#ffffff"))
                tvTestimoni.setTextColor(Color.parseColor("#ffffff"))
                tvBintang.setTextColor(Color.parseColor("#ffffff"))
                tvTanggal.setTextColor(Color.parseColor("#ffffff"))
                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvNama.setTypeface(null, Typeface.BOLD)
                tvVendor.setTypeface(null, Typeface.BOLD)
                tvTestimoni.setTypeface(null, Typeface.BOLD)
                tvBintang.setTypeface(null, Typeface.BOLD)
                tvTanggal.setTypeface(null, Typeface.BOLD)
                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val tempTestimoni = listTestimoni[(position-1)]

                tvNo.text = "$position"
                tempTestimoni.apply {
                    tvNama.text = nama_user
                    tvVendor.text = vendor
                    tvTestimoni.text = testimoni
                    tvBintang.text = "$bintang bintang"
                    tvTanggal.text = tanggal
                }
                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvNama.setBackgroundResource(R.drawable.bg_table)
                tvVendor.setBackgroundResource(R.drawable.bg_table)
                tvTestimoni.setBackgroundResource(R.drawable.bg_table)
                tvBintang.setBackgroundResource(R.drawable.bg_table)
                tvTanggal.setBackgroundResource(R.drawable.bg_table)
                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvNama.setTextColor(Color.parseColor("#000000"))
                tvVendor.setTextColor(Color.parseColor("#000000"))
                tvTestimoni.setTextColor(Color.parseColor("#000000"))
                tvBintang.setTextColor(Color.parseColor("#000000"))
                tvTanggal.setTextColor(Color.parseColor("#000000"))
                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvNama.setTypeface(null, Typeface.NORMAL)
                tvVendor.setTypeface(null, Typeface.NORMAL)
                tvTestimoni.setTypeface(null, Typeface.NORMAL)
                tvBintang.setTypeface(null, Typeface.NORMAL)
                tvTanggal.setTypeface(null, Typeface.NORMAL)
                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvVendor.gravity = Gravity.CENTER_VERTICAL
                tvTestimoni.gravity = Gravity.CENTER_VERTICAL
                tvBintang.gravity = Gravity.CENTER_VERTICAL
                tvTanggal.gravity = Gravity.CENTER_VERTICAL

                tvNama.setOnClickListener{
                    onClick.clickItemNama("Nama User", tempTestimoni.nama_user!!)
                }
                tvVendor.setOnClickListener{
                    onClick.clickItemVendor("Vendor", tempTestimoni.vendor!!)
                }
                tvTestimoni.setOnClickListener{
                    onClick.clickItemTestimoni("Testimoni", tempTestimoni.testimoni!!)
                }
                tvSetting.setOnClickListener {
                    onClick.clickItemSetting(tempTestimoni, it)
                }
            }

        }
    }
}