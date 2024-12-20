package com.abcd.vian_marketplaceweddingorganizer.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.bumptech.glide.Glide

class ChatWeddingOrganizerAdapter(
    val context: Context,
    val messageList: ArrayList<MessageModel>,
    var id:Int,
    val onClick: OnClickItem.ClickChatWeddingOrganizer,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG = "MessageKonsultasiAdapterTag"
    val ITEM_SENT = 1
    val ITEM_RECEIVED = 2
    val ITEM_SENT_IMAGE = 3
    val ITEM_RECEIVED_IMAGE = 4

    var checkBoxMessage = false
    var nomorCheck = 0
    var valueDataIdMessage = arrayListOf<String>()

    fun updateMessage(list: ArrayList<MessageModel>, add:Boolean, rv: RecyclerView){
        val tempList: ArrayList<MessageModel> = list
        val listUpdate: ArrayList<MessageModel> = arrayListOf()
        listUpdate.addAll(list)
        for(values in listUpdate){
            val a = messageList.singleOrNull{
                it.idMessage == values.idMessage
            }

            // Jika ADD = true maka tambah data. Jika ADD = false maka delete
            if(add){
                if(a != null){
                    tempList.remove(values)
                }
            } else{
                if(a == null){
                    tempList.remove(values)
                    Log.d(TAG, "deleteMessage: ${values.gambar}")
                }
            }
        }

        messageList.addAll(tempList)
        notifyDataSetChanged()
        rv.scrollToPosition(messageList.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_sent, parent, false)
            return SentViewHolder(view)
        }
        else if(viewType == 2){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_received, parent, false)
            return ReceivedViewHolder(view)
        }else if(viewType == 3){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_sent_gambar, parent, false)
            return SentViewHolderGambar(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_received_gambar, parent, false)
            return ReceivedViewHolderGambar(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var list = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){
            //Tampilkan Chat Pengirim
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = list.message

            viewHolder.sentMessage.setOnLongClickListener(object: OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    var popupMenu = PopupMenu(context, v)
                    popupMenu.inflate(R.menu.popup_menu_hapus)
                    popupMenu.setOnMenuItemClickListener { v->
                        when(v.itemId){
                            R.id.hapus->{
                                onClick.clickItemHapus(list)
//                                hapusPesan(list)
                            }
                        }

                        return@setOnMenuItemClickListener true
                    }
                    popupMenu.show()

                    return true
                }

            })

            if(checkBoxMessage){
                viewHolder.sentMessage.setOnClickListener {

                }
            }
        }
        else if(holder.javaClass == ReceivedViewHolder::class.java){
            //Tampilkan Chat Diterima
            val viewHolder = holder as ReceivedViewHolder
            viewHolder.receivedMessage.text = list.message
        } else if(holder.javaClass == SentViewHolderGambar::class.java){
            //Tampilkan Gambar Dikirim
            val viewHolder = holder as SentViewHolderGambar
            Glide.with(holder.itemView)
                .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${list.gambar}") // URL Gambar
                .error(R.drawable.background_main2)
                .into(viewHolder.sentMessageGambar) // imageView mana yang akan diterapkan

            viewHolder.sentMessageGambar.setOnLongClickListener(object: OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    var popupMenu = PopupMenu(context, v)
                    popupMenu.inflate(R.menu.popup_menu_hapus)
                    popupMenu.setOnMenuItemClickListener { v->
                        when(v.itemId){
                            R.id.hapus->{
                                onClick.clickItemGambarHapus(list)
//                                hapusPesan(list)
                            }
                        }

                        return@setOnMenuItemClickListener true
                    }
                    popupMenu.show()

                    return true
                }

            })
        } else if(holder.javaClass == ReceivedViewHolderGambar::class.java){
            //Tampilkan Gambar Diterima
            val viewHolder = holder as ReceivedViewHolderGambar
            Glide.with(holder.itemView)
                .load("https://aplikasi-tugas.my.id/rusly/gambar/${list.gambar}") // URL Gambar
                .error(R.drawable.background_main2)
                .into(viewHolder.receivedMessageGambar) // imageView mana yang akan diterapkan
        }
    }

    fun hapusPesan(messageList: MessageModel){
        var view = View.inflate(context, R.layout.alert_dialog_hapus, null)
        var tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        var btnHapus = view.findViewById<Button>(R.id.btnHapus)
        var btnBatalHapus = view.findViewById<Button>(R.id.btnBatalHapus)

        var alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(view)
        var dialog = alertDialog.create()
        dialog.show()

        btnHapus.setOnClickListener {

        }

        btnBatalHapus.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(currentMessage.pengirim == "user"){
            Log.d(TAG, "user: ")
            if(currentMessage.idUser == id){
                if(currentMessage.message.toString().trim().isEmpty()){
                    return ITEM_SENT_IMAGE
                } else{
                    return ITEM_SENT
                }
            }
            else{
                if(currentMessage.message.toString().trim().isEmpty()){
                    return ITEM_RECEIVED_IMAGE
                } else{
                    return ITEM_RECEIVED
                }
            }
        } else{
            Log.d(TAG, "wo: ")
            if(currentMessage.idWo == id){
                if(currentMessage.message.toString().trim().isEmpty()){
                    return ITEM_SENT_IMAGE
                } else{
                    return ITEM_SENT
                }
            }
            else{
                if(currentMessage.message.toString().trim().isEmpty()){
                    return ITEM_RECEIVED_IMAGE
                } else{
                    return ITEM_RECEIVED
                }
            }
        }
    }

    class SentViewHolder(v: View): RecyclerView.ViewHolder(v){
        val sentMessage = v.findViewById<TextView>(R.id.tvMessageSent)
    }
    class SentViewHolderGambar(v: View): RecyclerView.ViewHolder(v){
        val sentMessageGambar = v.findViewById<ImageView>(R.id.ivMessageSentImage)
    }
    class ReceivedViewHolder(v: View): RecyclerView.ViewHolder(v){
        val receivedMessage = v.findViewById<TextView>(R.id.tvMessageReceived)
    }
    class ReceivedViewHolderGambar(v: View): RecyclerView.ViewHolder(v){
        val receivedMessageGambar = v.findViewById<ImageView>(R.id.ivMessageReceivedImage)
    }

}