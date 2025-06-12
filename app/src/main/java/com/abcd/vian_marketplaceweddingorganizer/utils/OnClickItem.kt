package com.abcd.vian_marketplaceweddingorganizer.utils

import android.view.View
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel

interface OnClickItem {
    interface ClickPesanan{
        fun clickPesanan(
            idPemesanan: Int
        )
    }
    interface ClickWeddingOrganizer{
        fun clickWeddingOrganizer(
            weddingOrganizer: WeddingOrganizerModel
        )
//        fun clickLogoWeddingOrganizer(
//            namaWO: String, logo: String
//        )
    }
    interface ClickPaketWeddingOrganizer{
        fun clickPaketWeddingOrganizer(
            vendor: ArrayList<VendorModel>
        )
    }

    interface ClickChooseVendorWeddingOrganizer{
        fun clickChooseVendorWeddingOrganizer(
            check:Boolean, position:Int, vendor: VendorModel
        )
    }

    interface ClickChooseeAddress{
        fun clickItemPilih(data: AlamatModel, it: View)
        fun clickItemEdit(data:AlamatModel, it: View)
    }

    interface ClickChatListWeddingOrganizer{
        fun clickDetailChat(message: MessageModel)
        fun clickItemHapus(message: MessageModel)
    }

    interface ClickChatWeddingOrganizer{
        fun clickItemHapus(message: MessageModel)
        fun clickItemGambarHapus(message: MessageModel)
    }
    interface ClickRiwayatPesanan{
        fun clickRiwayatPesanan(
            idPemesanan: Int
        )
    }

    interface ClickAkun{
        fun clickItemSetting(akun: UsersModel, it: View)
        fun clickItemAlamat(alamat: String, it: View)
    }

    // Wedding organizer
    // Vendor

    interface ClickWeddingOrganizerVendor{
        fun clickItemVendor(vendor: VendorModel, it: View)
        fun clickItemSetting(vendor: VendorModel, it: View)
    }

    interface ClickWeddingOrganizerRekening{
        fun clickItemNama(ket:String, nama: String, it: View)
        fun clickItemSetting(rekening: RekeningModel, it: View)
    }

    interface ClickAdminWeddingOrganizer{
        fun clickItemNama(title:String, nama: String)
        fun clickItemAlamat(title:String, alamat: String)
        fun clickItemUsername(title:String, username: String)
        fun clickItemDeskripsi(title:String, deskripsi: String)
        fun clickItemGambar(title:String, gambar: String)
        fun clickItemSetting(wo: UsersModel, it: View)
    }

    interface ClickAdminUser{
        fun clickItemNama(title:String, nama: String)
        fun clickItemAlamat(title:String, alamat: String)
        fun clickItemUsername(title:String, username: String)
        fun clickItemSetting(wo: UsersModel, it: View)
    }

    interface ClickAdminTestimoni{
        fun clickItemNama(title:String, nama: String)
        fun clickItemVendor(title:String, vendor: String)
        fun clickItemTestimoni(title:String, testimoni: String)
        fun clickItemSetting(tempTestimoni: TestimoniModel, it: View)
    }
}