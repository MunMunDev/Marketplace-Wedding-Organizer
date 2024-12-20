package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ListChatBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import com.bumptech.glide.Glide
import java.lang.Exception

class ChatListAdapter(
    private val listMessage: ArrayList<MessageModel>,
    private val sebagai: String,
    private val onClick: OnClickItem.ClickChatListWeddingOrganizer
): RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {
    private val tanggalDanWaktu = TanggalDanWaktu()

    class ViewHolder(val binding: ListChatBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMessage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = listMessage[position]
        holder.binding.apply {
            val nama = if(sebagai == "user"){
                message.wedding_organizer!!.nama_wo!!
            } else{
                message.user!!.nama!!
            }
            val tanggal = tanggalDanWaktu.konversiBulan(message.tanggal!!)
            val valueMessage = if(message.message!!.isNotEmpty()){
                message.message!!
            } else{
                "Gambar"
            }

            val firstLetter = try {
                nama.substring(0, 1)
            } catch (ex: Exception){
                "A"
            }

            if(sebagai=="user"){
                // sebagai user, chat dengan WO
                tvInisial.visibility = View.GONE
                cvLogo.visibility = View.VISIBLE

                Glide.with(holder.itemView)
                    .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${message.wedding_organizer!!.logo_wo}") // URL Gambar
                    .error(R.drawable.background_main2)
                    .placeholder(R.drawable.loading)
                    .into(ivLogo) // imageView mana yang akan diterapkan
            } else{
                // sebagai wo, chat dengan user
                tvInisial.visibility = View.VISIBLE
                cvLogo.visibility = View.GONE
            }

            tvNama.text = nama
            tvMessage.text = valueMessage
            tvInisial.text = firstLetter

            val listBgCircle = ArrayList<Int>()
            listBgCircle.add(R.drawable.bg_circle)
            listBgCircle.add(R.drawable.bg_circle_1)
            listBgCircle.add(R.drawable.bg_circle_2)
            listBgCircle.add(R.drawable.bg_circle_3)
            listBgCircle.add(R.drawable.bg_circle_4)
            listBgCircle.add(R.drawable.bg_circle_5)

            val mathRandom = (Math.random()*5).toInt()
            tvInisial.setBackgroundResource(listBgCircle[mathRandom])
        }

        holder.itemView.setOnClickListener {
            onClick.clickDetailChat(message)
        }
    }
}