package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.TestimoniAdapter
import com.abcd.vian_marketplaceweddingorganizer.adapter.VendorWeddingOrganizerDetailAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerDetailBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.BottomSheetDialogBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.chat.ChatWeddingOrganizerActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.payment.PaymentActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeddingOrganizerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerDetailBinding
    private var idWeddingOrganizer: Int = 0
    private var namaWeddingOrganizer: String = ""
    private var alamat: String = ""
    private var deskripsi: String = ""
    private var harga: Int = 0
    private var gambarWeddingOrganizer: String = ""
    private var vendor: ArrayList<VendorModel> = arrayListOf()
    @Inject
    lateinit var rupiah : KonversiRupiah
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerDetailViewModel by viewModels()
    private lateinit var adapter: TestimoniAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        fetchDataSebelumnya()
        setAppBarNavbar()
        getVendor()
        getTestimoni()
        getTambahPesanan()
    }

    private fun fetchDataSebelumnya() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@WeddingOrganizerDetailActivity)
        val extras = intent.extras
        if(extras != null) {

            idWeddingOrganizer = intent.getIntExtra("idWeddingOrganizer", 0)
            namaWeddingOrganizer = intent.getStringExtra("nama")!!
            alamat = intent.getStringExtra("alamat")!!
            deskripsi = intent.getStringExtra("deskripsi")!!
//            harga = intent.getIntExtra("harga", 0)
            gambarWeddingOrganizer = intent.getStringExtra("gambarWeddingOrganizer")!!
            vendor = intent.getParcelableArrayListExtra("vendor")!!

            binding.apply {
                appNavbarDrawer.tvTitle.text = namaWeddingOrganizer
//                tvHarga.text = rupiah.rupiah(harga.toLong())
                tvDeskripsi.text = deskripsi

                Glide.with(this@WeddingOrganizerDetailActivity)
                    .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${gambarWeddingOrganizer}") // URL Gambar
                    .error(R.drawable.background_main2)    // image ketika gagal
                    .into(ivGambarWeddingOrganizer) // imageView mana yang akan diterapkan
            }

            fetchTestimoni(idWeddingOrganizer)
        }
    }

    private fun setAppBarNavbar() {
        binding.appNavbarDrawer.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun getVendor() {
        val adapterVendor = VendorWeddingOrganizerDetailAdapter(vendor, false, object: OnClickItem.ClickChooseVendorWeddingOrganizer{
            override fun clickChooseVendorWeddingOrganizer(
                check: Boolean,
                position:Int,
                vendor: VendorModel
            ) {

            }

        })
        binding.apply {
            rvVendor.layoutManager = GridLayoutManager(this@WeddingOrganizerDetailActivity, 2)
            rvVendor.adapter = adapterVendor
        }
    }

    private fun fetchTestimoni(idWeddingOrganizer: Int){
        viewModel.fetchTestimoni(idWeddingOrganizer)
    }

    private fun getTestimoni(){
        viewModel.getTestimoni().observe(this@WeddingOrganizerDetailActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Failure-> setFailureFetchTestimoni(result.message)
                is UIState.Success-> setSuccessFetchTestimoni(result.data)
            }
        }
    }

    private fun setSuccessFetchTestimoni(data: ArrayList<TestimoniModel>) {
        if(data.isNotEmpty()){
            adapter = TestimoniAdapter(data, sharedPreferencesLogin.getIdUser().toString())
            binding.apply {
                rvTestimoni.layoutManager = LinearLayoutManager(this@WeddingOrganizerDetailActivity, LinearLayoutManager.VERTICAL, false)
                rvTestimoni.adapter = adapter
            }
        }
    }

    private fun setFailureFetchTestimoni(message: String) {
        Log.d("DetailTAG", "setFailureFetchTestimoni: $message")
    }

    private fun setButton() {
        binding.apply {
            btnPesan.setOnClickListener {
                showBottomDialog()
//                showBottomDialog2()
            }
            btnChatWO.setOnClickListener {
                val i = Intent(this@WeddingOrganizerDetailActivity, ChatWeddingOrganizerActivity::class.java)
                i.putExtra("id_received", idWeddingOrganizer)
                i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
                startActivity(i)
            }
        }
    }

    private fun showBottomDialog(){
        val view = BottomSheetDialogBinding.inflate(layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(this@WeddingOrganizerDetailActivity)
        bottomSheetDialog.apply {
            setContentView(view.root)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            show()
        }

        val idListChooseVendor = arrayListOf<String>()
        val listChooseVendor = arrayListOf<VendorModel>()
        var totalPriceVendor = 0

        view.apply {
            val adapter = VendorWeddingOrganizerDetailAdapter(vendor, true, object: OnClickItem.ClickChooseVendorWeddingOrganizer{
                override fun clickChooseVendorWeddingOrganizer(
                    check: Boolean,
                    position:Int,
                    vendor: VendorModel
                ) {
                    if(check){
                        Log.d("DetailTAG", "true: ${vendor.nama_vendor}")
                        idListChooseVendor.add(vendor.id_vendor.toString())
                        listChooseVendor.add(vendor)
                        totalPriceVendor+=vendor.harga!!
                    } else{
                        Log.d("DetailTAG", "false: ")
                        idListChooseVendor.remove(vendor.id_vendor.toString())
                        listChooseVendor.remove(vendor)
                        totalPriceVendor-=vendor.harga!!
                    }
                    tvTotal.text = rupiah.rupiah(totalPriceVendor.toLong())
                    Log.d("DetailTAG", "id: ${idListChooseVendor.size}")
                }

            })
            rvVendor.layoutManager = GridLayoutManager(this@WeddingOrganizerDetailActivity, 2)
            rvVendor.adapter = adapter

            btnPesanVendor.setOnClickListener {
                if(listChooseVendor.isNotEmpty()){
                    val i = Intent(this@WeddingOrganizerDetailActivity, PaymentActivity::class.java)
                    i.putParcelableArrayListExtra("vendor", listChooseVendor)
                    i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
                    startActivity(i)
                } else{
                    Toast.makeText(this@WeddingOrganizerDetailActivity, "Kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showBottomDialog2() {
        val view = BottomSheetDialogBinding.inflate(layoutInflater)
        val dialog: Dialog = Dialog(this@WeddingOrganizerDetailActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view.root)

        view.apply {
            val adapter = VendorWeddingOrganizerDetailAdapter(vendor, true, object: OnClickItem.ClickChooseVendorWeddingOrganizer{
                override fun clickChooseVendorWeddingOrganizer(
                    check: Boolean,
                    position:Int,
                    vendor: VendorModel
                ) {
                    if(check){
                        Log.d("DetailTAG", "true: ${vendor.nama_vendor}")
                    } else{
                        Log.d("DetailTAG", "false: ")
                    }
                }

            })
            rvVendor.layoutManager = GridLayoutManager(this@WeddingOrganizerDetailActivity, 2)
            rvVendor.adapter = adapter
            btnPesanVendor.setOnClickListener {
                Toast.makeText(this@WeddingOrganizerDetailActivity, "Pesan", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
        dialog.window!!.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun dialogTambahPesanan(plafonModel: WeddingOrganizerModel) {
//        val view = AlertDialogPesanWeddingOrganizerBinding.inflate(layoutInflater)
//
//        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerDetailActivity)
//        alertDialog.setView(view.root)
//            .setCancelable(false)
//        val dialogInputan = alertDialog.create()
//        dialogInputan.show()
//
//        view.apply {
//            tvNamaWeddingOrganizer.text = namaWeddingOrganizer
//
//            tvJumlah.text =  "0"
//
//            tvStok.text =  "Stok : "+plafonModel.stok
//            tvUkuran.text =  "Ukuran : "+plafonModel.ukuran
//
//            val konversiRupiah = KonversiRupiah()
//            tvHargaWeddingOrganizer.text = "Harga : "+konversiRupiah.rupiah(plafonModel.harga!!.toLong())
////            tvJumlah.text =  plafonModel.jumlah.toString()
//
//            btnTambah.setOnClickListener {
//                var jumlah = tvJumlah.text.toString().toInt()
//                if(jumlah < plafonModel.stok!!.toInt()){
//                    jumlah+=1
//                    tvJumlah.text = jumlah.toString()
//                }
//            }
//            btnKurang.setOnClickListener {
//                var jumlah = tvJumlah.text.toString().toInt()
//                if (jumlah > 0){
//                    jumlah-=1
//                    tvJumlah.text = jumlah.toString()
//                }
//            }
//
//            btnSimpan.setOnClickListener {
//                var cek = false
//
//                if(tvJumlah.text.toString().trim() == "0"){
//                    cek = true
//                }
//
//                if(!cek){
//                    val idWeddingOrganizer = plafonModel.id_plafon!!
//                    val idUser = sharedPreferencesLogin.getIdUser().toString()
//                    val jumlah = tvJumlah.text.toString().trim()
//
//                    postTambahPesanan(idWeddingOrganizer, idUser, jumlah)
//                    dialogInputan.dismiss()
//                }
//            }
//            btnBatal.setOnClickListener {
//                dialogInputan.dismiss()
//            }
//        }

    }

    private fun postTambahPesanan(
        idWeddingOrganizer:String, idUser:String, jumlah: String
    ) {
        viewModel.postTambahPesanan(
            idWeddingOrganizer, idUser, jumlah
        )
    }

    private fun getTambahPesanan() {
        viewModel.getTambahPesanan().observe(this@WeddingOrganizerDetailActivity){ result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerDetailActivity)
                is UIState.Failure-> setFailurePostTambahData(result.message)
                is UIState.Success-> setSuccessPostTambahData(result.data)
            }
        }
    }

    private fun setFailurePostTambahData(message: String) {
        Toast.makeText(this@WeddingOrganizerDetailActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessPostTambahData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                Toast.makeText(this@WeddingOrganizerDetailActivity, "Berhasil", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@WeddingOrganizerDetailActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerDetailActivity, "Ada kesahalan", Toast.LENGTH_SHORT).show()
        }

        loading.alertDialogCancel()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(this@WeddingOrganizerDetailActivity, WeddingOrganizerActivity::class.java))
//        finish()
//    }
}