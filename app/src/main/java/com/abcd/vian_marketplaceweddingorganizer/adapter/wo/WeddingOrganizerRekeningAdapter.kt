package com.abcd.vian_marketplaceweddingorganizer.adapter.wo

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListWoRekeningBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import javax.inject.Inject

class WeddingOrganizerRekeningAdapter(
    private var listRekening: ArrayList<RekeningModel>,
    private var onClick: OnClickItem.ClickWeddingOrganizerRekening
): RecyclerView.Adapter<WeddingOrganizerRekeningAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListWoRekeningBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListWoRekeningBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listRekening.size+1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(position==0){
                tvNo.text = "NO"
                tvRekening.text = "Rekening"
                tvNama.text = "Nama"
                tvNoRekening.text = "No. Rekening"
                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvRekening.setBackgroundResource(R.drawable.bg_table_title)
                tvNama.setBackgroundResource(R.drawable.bg_table_title)
                tvNoRekening.setBackgroundResource(R.drawable.bg_table_title)
                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvRekening.setTextColor(Color.parseColor("#ffffff"))
                tvNama.setTextColor(Color.parseColor("#ffffff"))
                tvNoRekening.setTextColor(Color.parseColor("#ffffff"))
                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvRekening.setTypeface(null, Typeface.BOLD)
                tvNama.setTypeface(null, Typeface.BOLD)
                tvNoRekening.setTypeface(null, Typeface.BOLD)
                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val rekening = listRekening[(position-1)]

                tvNo.text = "$position"
                tvRekening.text = rekening.jenis_rekening
                tvNama.text = rekening.nama
                tvNoRekening.text = rekening.no_rekening
                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvRekening.setBackgroundResource(R.drawable.bg_table)
                tvNama.setBackgroundResource(R.drawable.bg_table)
                tvNoRekening.setBackgroundResource(R.drawable.bg_table)
                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvRekening.setTextColor(Color.parseColor("#000000"))
                tvNama.setTextColor(Color.parseColor("#000000"))
                tvNoRekening.setTextColor(Color.parseColor("#000000"))
                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvRekening.setTypeface(null, Typeface.NORMAL)
                tvNama.setTypeface(null, Typeface.NORMAL)
                tvNoRekening.setTypeface(null, Typeface.NORMAL)
                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvNoRekening.gravity = Gravity.CENTER_VERTICAL

                tvSetting.setOnClickListener {
                    onClick.clickItemSetting(rekening, it)
                }
                tvNama.setOnClickListener {
                    onClick.clickItemNama("Nama Di Rekening", rekening.nama!!, it)
                }
            }
        }
    }
}