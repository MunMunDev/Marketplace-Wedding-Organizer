package com.abcd.vian_marketplaceweddingorganizer.adapter.admin

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListAdminWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.bumptech.glide.Glide

class AdminWeddingOrganizerAdapter(
    private var listJenisPlafon: ArrayList<UsersModel>,
    private var onClick: OnClickItem.ClickAdminWeddingOrganizer
): RecyclerView.Adapter<AdminWeddingOrganizerAdapter.ViewHolder>() {
    private val rupiah = KonversiRupiah()
    class ViewHolder(val binding: ListAdminWeddingOrganizerBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAdminWeddingOrganizerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listJenisPlafon.size+1)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(position==0){
                tvNo.text = "NO"
                tvNama.text = "Nama"
                tvAlamat.text = "Alamat"
                tvNomorHp.text = "Nomor HP"
                tvUsername.text = "Username"
                tvPassword.text = "Password"
                tvDeskripsi.text = "Deskripsi"

                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvNama.setBackgroundResource(R.drawable.bg_table_title)
                tvAlamat.setBackgroundResource(R.drawable.bg_table_title)
                tvNomorHp.setBackgroundResource(R.drawable.bg_table_title)
                tvUsername.setBackgroundResource(R.drawable.bg_table_title)
                tvPassword.setBackgroundResource(R.drawable.bg_table_title)
                tvDeskripsi.setBackgroundResource(R.drawable.bg_table_title)

                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvNama.setTextColor(Color.parseColor("#ffffff"))
                tvAlamat.setTextColor(Color.parseColor("#ffffff"))
                tvNomorHp.setTextColor(Color.parseColor("#ffffff"))
                tvUsername.setTextColor(Color.parseColor("#ffffff"))
                tvPassword.setTextColor(Color.parseColor("#ffffff"))
                tvDeskripsi.setTextColor(Color.parseColor("#ffffff"))

                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvNama.setTypeface(null, Typeface.BOLD)
                tvAlamat.setTypeface(null, Typeface.BOLD)
                tvNomorHp.setTypeface(null, Typeface.BOLD)
                tvUsername.setTypeface(null, Typeface.BOLD)
                tvPassword.setTypeface(null, Typeface.BOLD)
                tvDeskripsi.setTypeface(null, Typeface.BOLD)

                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val wo = listJenisPlafon[(position-1)]

                tvNo.text = "$position"
                wo.apply {
                    tvNama.text = nama
                    tvAlamat.text = alamat
                    tvNomorHp.text = nomorHp
                    tvUsername.text = username
                    tvPassword.text = password
                    tvDeskripsi.text = deskripsi_wo

                    Glide.with(holder.itemView)
                        .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${logo_wo}")
                        .error(R.drawable.background_main2)
                        .into(ivLogo)
                }

                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvNama.setBackgroundResource(R.drawable.bg_table)
                tvAlamat.setBackgroundResource(R.drawable.bg_table)
                tvNomorHp.setBackgroundResource(R.drawable.bg_table)
                tvUsername.setBackgroundResource(R.drawable.bg_table)
                tvPassword.setBackgroundResource(R.drawable.bg_table)
                tvDeskripsi.setBackgroundResource(R.drawable.bg_table)
                ivLogo.setBackgroundResource(R.drawable.bg_table)

                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvNama.setTextColor(Color.parseColor("#000000"))
                tvAlamat.setTextColor(Color.parseColor("#000000"))
                tvNomorHp.setTextColor(Color.parseColor("#000000"))
                tvUsername.setTextColor(Color.parseColor("#000000"))
                tvPassword.setTextColor(Color.parseColor("#000000"))
                tvDeskripsi.setTextColor(Color.parseColor("#000000"))

                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvNama.setTypeface(null, Typeface.NORMAL)
                tvAlamat.setTypeface(null, Typeface.NORMAL)
                tvNomorHp.setTypeface(null, Typeface.NORMAL)
                tvUsername.setTypeface(null, Typeface.NORMAL)
                tvPassword.setTypeface(null, Typeface.NORMAL)
                tvDeskripsi.setTypeface(null, Typeface.NORMAL)

                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvAlamat.gravity = Gravity.CENTER_VERTICAL
                tvNomorHp.gravity = Gravity.CENTER_VERTICAL
                tvUsername.gravity = Gravity.CENTER_VERTICAL
                tvPassword.gravity = Gravity.CENTER_VERTICAL
                tvDeskripsi.gravity = Gravity.CENTER_VERTICAL


                tvNama.setOnClickListener{
                    onClick.clickItemNama("Nama Wedding Organizer", wo.nama!!)
                }
                tvAlamat.setOnClickListener{
                    onClick.clickItemAlamat("Alamat", wo.alamat!!)
                }
                tvUsername.setOnClickListener{
                    onClick.clickItemUsername("Username", wo.username!!)
                }
                tvDeskripsi.setOnClickListener{
                    onClick.clickItemDeskripsi("Deskripsi WO", wo.deskripsi_wo!!)
                }
                ivLogo.setOnClickListener {
                    onClick.clickItemGambar(wo.nama!!, wo.logo_wo!!)
                }
                tvSetting.setOnClickListener {
                    onClick.clickItemSetting(wo, it)
                }
            }
        }
    }
}