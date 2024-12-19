package com.abcd.vian_marketplaceweddingorganizer.utils

import android.view.View
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel

interface OnClickItem {
    interface ClickWeddingOrganizer{
        fun clickWeddingOrganizer(
            weddingOrganizer: WeddingOrganizerModel
        )
//        fun clickLogoWeddingOrganizer(
//            namaWO: String, logo: String
//        )
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

    interface ClickAkun{
        fun clickItemSetting(akun: UsersModel, it: View)
        fun clickItemAlamat(alamat: String, it: View)
    }
}