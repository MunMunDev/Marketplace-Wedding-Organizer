package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.riwayat_pesanan.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.RiwayatPesananDetailAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerRiwayatPesananDetailBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKonfirmasiBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.chat.ChatWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.detail.WeddingOrganizerPesananDetailViewModel
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import javax.inject.Inject

@AndroidEntryPoint
class WeddingOrganizerRiwayatPesananDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerRiwayatPesananDetailBinding
    private val viewModel: WeddingOrganizerRiwayatPesananDetailViewModel by viewModels()
    private var idPemesanan = 1
    private var idWo = 1
    private var idUser = 0
    private var user = ""
    private var numberWhatsapp = ""
    private var messageChatWhatsapp = ""
    @Inject
    lateinit var tanggalDanWaktu: TanggalDanWaktu
    @Inject
    lateinit var rupiah: KonversiRupiah
    @Inject
    lateinit var loading: LoadingAlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerRiwayatPesananDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setConfigurationHeader()
        setDataSebelumnya()
        setButton()
        getRiwayatPembayaran()
    }

    private fun setConfigurationHeader() {
        binding.drawerView.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

        }
    }

    private fun setDataSebelumnya() {
        val i = intent.extras
        if(i != null){
            idPemesanan = i.getInt("idPemesanan")!!
            fetchRiwayatPembayaran(idPemesanan)
        }
    }

    private fun setButton() {
        binding.apply {
            drawerView.ivBack.setOnClickListener {
                finish()
            }
            btnChatUser.setOnClickListener{
                val i = Intent(this@WeddingOrganizerRiwayatPesananDetailActivity, ChatWeddingOrganizerActivity::class.java)
                i.putExtra("id_received", idUser)
                i.putExtra("nama_wedding_organizer", user)

                startActivity(i)
            }
            btnChatWA.setOnClickListener{
                sendChatWhatsapp()
            }
        }
    }

    private fun sendChatWhatsapp() {
        val url = "https://api.whatsapp.com/send?phone=$numberWhatsapp&text=${URLEncoder.encode(messageChatWhatsapp, "UTF-8")}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun fetchRiwayatPembayaran(idPemesanan: Int) {
        viewModel.fetchDetailRiwayatPesanan(idPemesanan)
    }
    private fun getRiwayatPembayaran(){
        viewModel.getDetailRiwayatPesanan().observe(this@WeddingOrganizerRiwayatPesananDetailActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchRiwayatPembayaran(result.data)
                is UIState.Failure-> setFailureFetchRiwayatPembayaran(result.message)
            }
        }
    }

    private fun setFailureFetchRiwayatPembayaran(message: String) {
        Toast.makeText(this@WeddingOrganizerRiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
        Log.d("DetailTAG", "setFailureFetchRiwayatPembayaran: $message")
    }

    private fun setSuccessFetchRiwayatPembayaran(data: ArrayList<RiwayatPesananModel>) {
        if(data.isNotEmpty()){
            setConfigurationData(data)
            setAlamat(data)
            setKeterangan(data)
            setAdapter(data)
        } else{
            Toast.makeText(this@WeddingOrganizerRiwayatPesananDetailActivity, "Tidak ada data Jenis Plafon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setConfigurationData(data: ArrayList<RiwayatPesananModel>) {
        binding.drawerView.tvTitle.text = data[0].nama_lengkap
        idUser = data[0].id_user!!
        user = data[0].nama_user!!
        numberWhatsapp = data[0].nomor_hp!!
        var hargaPesanan = 0
        messageChatWhatsapp = "Hy ${data[0].nama_lengkap} " +
                "\nKami dari *${data[0].wo!!.nama}* ingin menyampaikan bahwa pesanan anda sedang kami proses. " +
                "\nPesanan Anda: "
        for((i, value) in data.withIndex()){
            hargaPesanan+=value.harga!!
            val tempHarga = rupiah.rupiah(value.harga.toLong())
            messageChatWhatsapp+="$i. ${value.vendor} : $tempHarga"
        }
        messageChatWhatsapp+="Harga Total : ${rupiah.rupiah(hargaPesanan.toLong())}"
    }

    private fun setAlamat(data: ArrayList<RiwayatPesananModel>) {
        binding.apply {
            tvNama.text = data[0].nama_lengkap
            tvNomorHp.text = data[0].nomor_hp
            tvKecamatan.text = "Kecamatan ${data[0].kecamatan_kab_kota}"
            tvAlamat.text = data[0].alamat
            tvAlamatDetail.text = data[0].detail_alamat
        }
    }

    private fun setKeterangan(data: ArrayList<RiwayatPesananModel>) {
        var totalHarga = 0
        for (values in data){
            totalHarga += values.harga!!
        }
        idWo = data[0].wo!!.id_wo!!
        val metodePembayaran = data[0].metode_pembayaran!!
        var array = data[0].waktu_acara!!.split(" ")
        var tanggalAcara = ""
        var tanggalPesanan = ""
        var tanggalBayar = ""
        if(data[0].waktu_acara!!.isNotEmpty()){
            array = data[0].waktu_acara!!.split(" ")
            tanggalAcara = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        if(data[0].waktu!!.isNotEmpty()){
            array = data[0].waktu!!.split(" ")
            tanggalPesanan = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        if(data[0].waktu_bayar!!.isNotEmpty()){
            array = data[0].waktu_bayar!!.split(" ")
            tanggalBayar = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        var selesai = data[0].selesai!!
        if(selesai == "0"){
            selesai = "On Progress"
        } else{
            selesai = "Pesanan Telah Selesai"
        }

        val ket = data[0].ket!!
        binding.apply {
            tvTotalHarga.text = rupiah.rupiah(totalHarga.toLong())
            tvMetodePembayaran.text = metodePembayaran
            tvTanggalAcara.text = tanggalAcara
            tvTanggalPesanan.text = tanggalPesanan
            tvTanggalBayar.text = tanggalBayar
            tvKet.text = selesai
        }
    }

    private fun setAdapter(data: java.util.ArrayList<RiwayatPesananModel>) {
        val adapter = RiwayatPesananDetailAdapter(data)
        binding.apply {
            rvDetailRiwayatPesanan.layoutManager = LinearLayoutManager(this@WeddingOrganizerRiwayatPesananDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvDetailRiwayatPesanan.adapter = adapter
        }
    }

}