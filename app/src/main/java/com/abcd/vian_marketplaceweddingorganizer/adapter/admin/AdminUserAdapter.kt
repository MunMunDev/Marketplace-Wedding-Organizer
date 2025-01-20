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
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListAdminUserBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem

class AdminUserAdapter(
    private var listUser: ArrayList<UsersModel>,
    private var onClick: OnClickItem.ClickAdminUser
): RecyclerView.Adapter<AdminUserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListAdminUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAdminUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listUser.size+1)
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
                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvNama.setBackgroundResource(R.drawable.bg_table_title)
                tvAlamat.setBackgroundResource(R.drawable.bg_table_title)
                tvNomorHp.setBackgroundResource(R.drawable.bg_table_title)
                tvUsername.setBackgroundResource(R.drawable.bg_table_title)
                tvPassword.setBackgroundResource(R.drawable.bg_table_title)
                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvNama.setTextColor(Color.parseColor("#ffffff"))
                tvAlamat.setTextColor(Color.parseColor("#ffffff"))
                tvNomorHp.setTextColor(Color.parseColor("#ffffff"))
                tvUsername.setTextColor(Color.parseColor("#ffffff"))
                tvPassword.setTextColor(Color.parseColor("#ffffff"))
                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvNama.setTypeface(null, Typeface.BOLD)
                tvAlamat.setTypeface(null, Typeface.BOLD)
                tvNomorHp.setTypeface(null, Typeface.BOLD)
                tvUsername.setTypeface(null, Typeface.BOLD)
                tvPassword.setTypeface(null, Typeface.BOLD)
                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val user = listUser[(position-1)]

                tvNo.text = "$position"
                user.apply {
                    tvNama.text = nama
                    tvAlamat.text = alamat
                    tvNomorHp.text = nomorHp
                    tvUsername.text = username
                    tvPassword.text = password
                }
                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvNama.setBackgroundResource(R.drawable.bg_table)
                tvAlamat.setBackgroundResource(R.drawable.bg_table)
                tvNomorHp.setBackgroundResource(R.drawable.bg_table)
                tvUsername.setBackgroundResource(R.drawable.bg_table)
                tvPassword.setBackgroundResource(R.drawable.bg_table)
                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvNama.setTextColor(Color.parseColor("#000000"))
                tvAlamat.setTextColor(Color.parseColor("#000000"))
                tvNomorHp.setTextColor(Color.parseColor("#000000"))
                tvUsername.setTextColor(Color.parseColor("#000000"))
                tvPassword.setTextColor(Color.parseColor("#000000"))
                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvNama.setTypeface(null, Typeface.NORMAL)
                tvAlamat.setTypeface(null, Typeface.NORMAL)
                tvNomorHp.setTypeface(null, Typeface.NORMAL)
                tvUsername.setTypeface(null, Typeface.NORMAL)
                tvPassword.setTypeface(null, Typeface.NORMAL)
                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvAlamat.gravity = Gravity.CENTER_VERTICAL
                tvNomorHp.gravity = Gravity.CENTER_VERTICAL
                tvUsername.gravity = Gravity.CENTER_VERTICAL
                tvPassword.gravity = Gravity.CENTER_VERTICAL


                tvNama.setOnClickListener{
                    onClick.clickItemNama("Nama Wedding Organizer", user.nama!!)
                }
                tvAlamat.setOnClickListener{
                    onClick.clickItemAlamat("Alamat", user.alamat!!)
                }
                tvUsername.setOnClickListener{
                    onClick.clickItemUsername("Username", user.username!!)
                }
                tvSetting.setOnClickListener {
                    onClick.clickItemSetting(user, it)
                }
            }

        }
    }
}