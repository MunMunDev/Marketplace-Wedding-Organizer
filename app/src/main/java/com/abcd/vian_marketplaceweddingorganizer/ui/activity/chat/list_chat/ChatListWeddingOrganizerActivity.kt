package com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.list_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.adapter.ChatListAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityChatListWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogHapusBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.chat.ChatWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListWeddingOrganizerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatListWeddingOrganizerBinding
    private val viewModel: ChatListWeddingOrganizerViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferencesLogin
    private lateinit var listMessageAdapter: ChatListAdapter
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListWeddingOrganizerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setConfiguration()
        setButton()
        fetchChatListWeddingOrganizer(sharedPref.getIdUser(), sharedPref.getSebagai())
        getChatListWeddingOrganizer()
    }

    private fun setConfiguration() {
        setKontrolNavigationDrawer()
        sharedPref = SharedPreferencesLogin(this@ChatListWeddingOrganizerActivity)

        binding.appNavbarDrawer.apply {
            tvTitle.text = "List Chat"

            if(sharedPref.getSebagai() == "user"){
                ivBack.visibility = View.VISIBLE
                ivNavDrawer.visibility = View.GONE
            } else{
                ivBack.visibility = View.GONE
                ivNavDrawer.visibility = View.VISIBLE
            }

        }
    }

    private fun setButton() {
        binding.apply {
            appNavbarDrawer.apply {
                ivBack.setOnClickListener{
                    finish()
                }

            }
        }
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@ChatListWeddingOrganizerActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, appNavbarDrawer.ivNavDrawer, this@ChatListWeddingOrganizerActivity)
        }
    }

    private fun fetchChatListWeddingOrganizer(idUser:Int, sebagai: String){
        viewModel.fetchChatListWeddingOrganizer(idUser, sebagai)
    }

    private fun getChatListWeddingOrganizer(){
        viewModel.getChatListWeddingOrganizer().observe(this@ChatListWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchChatListWeddingOrganizer(result.data)
                is UIState.Failure-> setFailureFetchChatListWeddingOrganizer(result.message)
            }
        }
    }

    private fun setFailureFetchChatListWeddingOrganizer(message: String) {
        Toast.makeText(this@ChatListWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchChatListWeddingOrganizer(data: ArrayList<MessageModel>) {
        setAdapterChatListWeddingOrganizer(data)
    }

    private fun setAdapterChatListWeddingOrganizer(list: ArrayList<MessageModel>) {
        listMessageAdapter = ChatListAdapter(
            list,
            sharedPref.getSebagai(),
            object: OnClickItem.ClickChatListWeddingOrganizer{
                override fun clickDetailChat(message: MessageModel) {
                    val i = Intent(this@ChatListWeddingOrganizerActivity, ChatWeddingOrganizerActivity::class.java)
                    i.putExtra("id_received", message.id_penerima)
                    i.putExtra("nama_wedding_organizer", message.user!!.nama)

//                    if(sharedPref.getSebagai() == "user"){
//                        i.putExtra("id_received", message.wedding_organizer!!.id_wo)
//                        i.putExtra("nama_wedding_organizer", message.wedding_organizer!!.nama)
//                    } else{
//                        i.putExtra("id_received", message.user!!.idUser)
//                        i.putExtra("nama_wedding_organizer", message.user!!.nama)
//                    }
                    startActivity(i)
                }

                override fun clickItemHapus(message: MessageModel) {
                    val view = AlertDialogHapusBinding.inflate(layoutInflater)
                    val alertDialog = AlertDialog.Builder(this@ChatListWeddingOrganizerActivity)
                    alertDialog.setView(view.root)
                    val dialog = alertDialog.create()
                    dialog.show()

                    view.apply {
                        btnHapus.setOnClickListener {
//                            postDeleteMessage(message.idMessage!!)
                            dialog.dismiss()
                        }

                        btnBatalHapus.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }

            })

        binding.apply {
            rvListKonsultasiChatDokter.layoutManager = LinearLayoutManager(this@ChatListWeddingOrganizerActivity)
            rvListKonsultasiChatDokter.adapter = listMessageAdapter

            listMessageAdapter.notifyDataSetChanged()
        }
    }
}